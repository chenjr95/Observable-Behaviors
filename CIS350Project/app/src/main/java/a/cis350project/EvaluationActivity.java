package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.GregorianCalendar;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import android.text.Html;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

import android.widget.SeekBar.OnSeekBarChangeListener;

import java.util.TimeZone;

import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import android.widget.Toast;

/**
 * Created by jhavaldar on 4/2/15.
 *
 * Activity for doing the evaluation
 *
 */
public class EvaluationActivity extends Activity {

    private String traineeUsername;
    private Map<String, String[]> rubricItems;
    private android.content.Context selfReference;

    // on create, set trainee and rubricItems
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        selfReference = this;

        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        final ArrayList<String> observableBehaviors =
                new ArrayList<>(LoginActivity.globalProgram.getObsBehavs());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, observableBehaviors);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        final TextView rubricNumDescription = (TextView) findViewById(R.id.rubricNumDescription);
        final Map <Integer, String> likertDescriptions = new HashMap<>();

        likertDescriptions.put(1, StringDefines.likert_desc1);
        likertDescriptions.put(2, StringDefines.likert_desc2);
        likertDescriptions.put(3, StringDefines.likert_desc3);
        likertDescriptions.put(4, StringDefines.likert_desc4);
        likertDescriptions.put(5, StringDefines.likert_desc5);

        SeekBar seekBar = (SeekBar) findViewById(R.id.intensitySlider);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rubricNumDescription.setText(likertDescriptions.get(progress + 1));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /**
         * The rubric TextView goes right above the comment and gives the criteria for evaluation.
         * The evaluations matched with each OB in the rubricItems HashMap.
         */
        final TextView rubric = (TextView) findViewById(R.id.rubric);
        rubricItems =  new HashMap<>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.obs_be_obj);
        query.whereEqualTo(StringDefines.obs_prog_attr, LoginActivity.globalProgram.getProgName());
        Log.v("program name", LoginActivity.globalProgram.getProgName());
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> obsBeList, ParseException e) {
                if (e == null) {
                    for (ParseObject p : obsBeList) {
                        String [] rubricArray = p.getString(StringDefines.ob_rubric_attr).split(",");
                        rubricItems.put(p.getString(StringDefines.ob_name_attr),rubricArray);
                        Log.v("rubric Item", p.getString(StringDefines.ob_rubric_attr));
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });

        String [] rubricArray = rubricItems.get(spinner.getSelectedItem());
        String rubricItemsList = "";
        if(rubricArray != null) {
            for (int i = 0; i < rubricArray.length - 1; i++) {
                rubricItemsList += "&#8226; " + rubricArray[i] + "<br/>\n";
            }
            rubricItemsList += "&#8226; " + rubricArray[rubricArray.length - 1] + "<br/>";
        }
        rubric.setText((Html.fromHtml(rubricItemsList)));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String [] rubricArray = rubricItems.get(spinner.getSelectedItem());
                String rubricItemsList = "";
                if(rubricArray != null) {
                    for (int i = 0; i < rubricArray.length - 1; i++) {
                        rubricItemsList += "&#8226; " + rubricArray[i] + "<br/>\n";
                    }
                    rubricItemsList += "&#8226; " + rubricArray[rubricArray.length - 1] + "<br/>";
                }
                rubric.setText((Html.fromHtml(rubricItemsList)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        traineeUsername = getIntent().getStringExtra(StringDefines.username_attr);
    }

    // handles form submission
    public void submitEval(View view) {
        EditText evalTextBox = (EditText) findViewById(R.id.editText);
        EditText hiddenTextBox = (EditText) findViewById(R.id.editHiddenText);
        String evalText = evalTextBox.getText().toString();
        String evalHiddenText = hiddenTextBox.getText().toString();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String obsBehav = spinner.getSelectedItem().toString();
        SeekBar likertScore = (SeekBar) findViewById(R.id.intensitySlider);
        int score = likertScore.getProgress() + 1;

        GregorianCalendar time = new GregorianCalendar();
        time.setTimeZone(TimeZone.getTimeZone("GMT"));
        Evaluation curr = new Evaluation(Program.loggedInUser.getUsername(),
                traineeUsername, obsBehav, evalText, evalHiddenText, score,
                new GregorianCalendar());
        curr.toParseObject().saveInBackground();

        if(score == 1) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
            query.whereEqualTo(StringDefines.typeofuser_attr, StringDefines.coordinator_type);
            query.whereEqualTo(StringDefines.program_attr, LoginActivity.globalProgram.getProgName());
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> coordList, ParseException e) {
                    if (e == null) {
                        HashMap<String, Object> params;
                        String emailMessage = traineeUsername + " scored a one on: " + obsBehav;
                        for (ParseObject p : coordList) {
                            params = new HashMap<>();
                            String email = p.getString(StringDefines.email_attr);
                            params.put(StringDefines.recipEmail, email);
                            params.put(StringDefines.recipName, p.getString(StringDefines.name_attr));
                            params.put(StringDefines.subj, traineeUsername + StringDefines.oneScore);
                            params.put(StringDefines.message, emailMessage);
                            ParseCloud.callFunctionInBackground(StringDefines.email_func, params, new FunctionCallback<String>() {
                                public void done(String confirmation, ParseException e) {
                                    if (e == null) {
                                        Toast.makeText(selfReference, "Email successfully sent.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
        // finish activity
        setResult(RESULT_OK);
        finish();
    }

    // go to a different activity to view history of evaluations between this evaluator and trainee
    // pair
    public void viewHistory (View view) {
        Intent i = new Intent(this, EvaluationHistoryActivity.class);
        i.putExtra("EVALUATOR_USERNAME", Program.loggedInUser.getUsername());
        i.putExtra("TRAINEE_USERNAME", traineeUsername);
        startActivity(i);
    }

    // create menu at top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluator, menu);
        return true;
    }

    // handle menu at top
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
