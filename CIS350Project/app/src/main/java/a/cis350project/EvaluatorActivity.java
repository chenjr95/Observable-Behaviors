package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListView;

import java.util.ArrayList;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;

/*
 * Class for login activity of Evaluators
 */
public class EvaluatorActivity extends Activity {
    private final ArrayList<String> traineeIndices = new ArrayList<>();
    private static final int EVALUATION = 1;

    // on create, make the mapping of trainees
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator);

        Evaluator evaluator = (Evaluator) Program.loggedInUser;
        TextView programDisplay = (TextView) findViewById(R.id.yourProgramBottom);
        programDisplay.setText(evaluator.getProgram());

        ListView traineesList = (ListView) findViewById(R.id.listView);
        ArrayList<String> traineeNames = new ArrayList<>();
        for(Trainee t: LoginActivity.globalProgram.getTrainees()) {
            traineeNames.add(t.getName());
            traineeIndices.add(t.getUsername());
        }

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, traineeNames);
        traineesList.setAdapter(arrayAdapter);

        traineesList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(getApplicationContext(), EvaluationActivity.class);
                i.putExtra(StringDefines.username_attr, traineeIndices.get(position));
                startActivityForResult(i, EVALUATION);
            }
        });

    }

    // help with starting the activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check which request we're responding to
        switch(requestCode) {
            case EVALUATION:
            // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Evaluation submitted successfully.",
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
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
