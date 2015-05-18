package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by champ_000 on 4/12/2015.
 *
 * Activity for a coordinator to view OBs for a trainee
 *
 */
public class CoordinatorOBTraineeActivity extends Activity {
    private String traineeUsername;
    private final HashMap<String, Integer> obsBeCount = new HashMap<>();
    private final HashMap<String, Double> obsBeAvgs = new HashMap<>();
    /**
     * Keep track of the eval task to ensure we can cancel it if requested.
     */
    private EvaluationTask evalTask = null;
    private TableLayout mainLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_ob_trainee);

        Program coordinator_program = LoginActivity.globalProgram;
        traineeUsername = getIntent().getStringExtra(StringDefines.username_attr);
        mainLayout = (TableLayout) findViewById(R.id.table);
        mainLayout.setStretchAllColumns(true);
        mainLayout.setClickable(false);
        // Holds all behaviors in the program
        List<String> behaviors = coordinator_program.getObsBehavs();
        for (String behav : behaviors) {
            obsBeCount.put(behav, 0);
            obsBeAvgs.put(behav, 0.0);
        }

        // Holds a sorted list of evaluated behaviors for the trainee
        // the OB with the least evaluations is at the top, and most evaluated is at the bottom
        evalTask = new EvaluationTask(traineeUsername, this);
        evalTask.execute((Void) null);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class EvaluationTask extends AsyncTask<Void, Void, Integer> {
        List<ParseObject> evaluations = null;
        private final String mUsername;
        private final Activity obTraineeActivity;

        EvaluationTask(String username, Activity a) {
            mUsername = username;
            obTraineeActivity = a;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.evaluation_obj);
            query.whereEqualTo(StringDefines.trainee_username_attr, mUsername);
            try{
                evaluations = query.find();
            }
            catch (com.parse.ParseException e){
                Log.d("Error in first query", e.getMessage());
            }
            Evaluation next;
            for (ParseObject p : evaluations) {
                next = new Evaluation(p);

                if(!obsBeCount.containsKey(next.getObsBe())) {
                    obsBeCount.put(next.getObsBe(), 1);
                    obsBeAvgs.put(next.getObsBe(), (double) next.getLikertScale());

                } else {
                    obsBeCount.put(next.getObsBe(), obsBeCount.get(next.getObsBe()) + 1);

                    obsBeAvgs.put(next.getObsBe(),
                            next.getLikertScale() + obsBeAvgs.get(next.getObsBe()));

                }
            }
            for (String ob : obsBeAvgs.keySet()) {
                obsBeAvgs.put(ob, (double) Math.round(obsBeAvgs.get(ob) / obsBeCount.get(ob)));
            }
            return 0;
        }

        public <String, Integer extends Comparable<? super Integer>> SortedSet<Map.Entry<String,Integer>> entriesSortedByValues(Map<String,Integer> map) {
            SortedSet<Map.Entry<String,Integer>> sortedEntries = new TreeSet<Map.Entry<String,Integer>>(
                    new Comparator<Map.Entry<String,Integer>>() {
                        @Override public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2) {
                            int res = e1.getValue().compareTo(e2.getValue());
                            if (e1.getKey().equals(e2.getKey())) {
                                return res; // Code will now handle equality properly
                            } else {
                                return res != 0 ? res : 1; // While still adding all entries
                            }
                        }
                    }
            );
            sortedEntries.addAll(map.entrySet());
            return sortedEntries;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            evalTask = null;
            List<String> sortedBehaviors = new ArrayList<>();
            List<String> sortedNumbers = new ArrayList<>();
            if(success == 0) {
                SortedSet<Map.Entry<String, Integer>> behaviors = entriesSortedByValues(obsBeCount);
                for (Map.Entry<String, Integer> behavior : behaviors) {
                    sortedBehaviors.add(behavior.getKey());
                }
                for(int i = 0; i< sortedBehaviors.size(); i++) {
                    sortedNumbers.add(obsBeCount.get(sortedBehaviors.get(i)).toString());
                }
                // set observable averages
                List<String> sortedAvgs = new ArrayList<>();
                for (String ob : sortedBehaviors) {
                    String toAdd = obsBeAvgs.get(ob).toString();
                    if (toAdd.equals("NaN")) {
                        toAdd = "--";
                    }
                    sortedAvgs.add(toAdd);
                }

                // Creates Header Rows
                TableRow firstRow = new TableRow(obTraineeActivity);
                TextView columnOne = new TextView(obTraineeActivity);
                columnOne.setText("OB");
                TextView columnTwo = new TextView(obTraineeActivity);
                columnTwo.setText("# Evals");
                TextView columnThree = new TextView(obTraineeActivity);
                columnThree.setText("Average");
                firstRow.addView(columnOne);
                firstRow.addView(columnTwo);
                firstRow.addView(columnThree);
                mainLayout.addView(firstRow);
                // Creates and populates remaining rows
                for (int i = 0; i < sortedBehaviors.size(); i++) {
                    TableRow nextRow = new TableRow(obTraineeActivity);
                    nextRow.setClickable(true);
                    nextRow.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            //get the data you need
                            TableRow tablerow = (TableRow)v;
                            TextView sample = (TextView) tablerow.getChildAt(0);
                            String obsBe = sample.getText().toString();
                            Intent i = new Intent(getApplicationContext(),
                                    CoordinatorHistoryActivity.class);
                            i.putExtra(StringDefines.obs_be_attr, obsBe);
                            i.putExtra(StringDefines.username_attr, traineeUsername);
                            startActivity(i);
                        }
                    });
                    TextView first = new TextView(obTraineeActivity);
                    first.setText(sortedBehaviors.get(i));
                    TextView second = new TextView(obTraineeActivity);
                    second.setText(sortedNumbers.get(i));
                    TextView third = new TextView(obTraineeActivity);
                    third.setText(sortedAvgs.get(i));
                    nextRow.addView(first);
                    nextRow.addView(second);
                    nextRow.addView(third);
                    mainLayout.addView(nextRow);
                }
            }
        }

        @Override
        protected void onCancelled() {
            evalTask = null;
        }
    }

    public void sendOneTraineeEmail(View view) {
        for(Trainee t : LoginActivity.globalTrainees) {
            if(t.getUsername().equals(traineeUsername)) {
                t.sendTraineeEmail();
            }
        }
    }
}
