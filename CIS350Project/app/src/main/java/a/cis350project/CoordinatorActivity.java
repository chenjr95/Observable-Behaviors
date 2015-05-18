package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jhavaldar on 4/2/15.
 *
 * Activity for login screen seen by coordinator
 *
 */
public class CoordinatorActivity extends Activity {

    private final ArrayList<String> traineeIndices = new ArrayList<>();
    private final ArrayList<String> traineeProgramIndices = new ArrayList<>();
    private final ArrayList<Evaluator> evaluatorIndices = new ArrayList<>();
    private final ArrayList<HashMap<String, String>> itemList = new ArrayList<>();

    // on create, populate trainee and evaluators
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator);

        Coordinator coordinator = (Coordinator) Program.loggedInUser;
        Program program = LoginActivity.globalProgram;

        TextView programDisplay = (TextView) findViewById(R.id.coordinatorProgram);
        programDisplay.setText(coordinator.getProgram());

        ArrayList<String> evaluatorNames = new ArrayList<>();

        // Show trainees with trainees in the same program as coordinator coming first
        HashMap<String, String> itemMap;
        for(Trainee t: program.getTrainees()) {
                itemMap = new HashMap<>();
                itemMap.put("name", t.getName());
                itemMap.put("program", t.getProgram());
                itemList.add(itemMap);

                traineeIndices.add(t.getUsername());
                traineeProgramIndices.add(t.getProgram());

        }

        for(Trainee t: LoginActivity.globalTrainees) {
            if(!t.getProgram().equals(Program.loggedInUser.getProgram())) {
                itemMap = new HashMap<>();
                itemMap.put("name", t.getName());
                itemMap.put("program", t.getProgram());
                itemList.add(itemMap);

                traineeIndices.add(t.getUsername());
                traineeProgramIndices.add(t.getProgram());
            }
        }

        for(Evaluator e: program.getEvaluators()) {
            evaluatorNames.add(e.getName());
            evaluatorIndices.add(e);
        }

        ListView traineesList = (ListView) findViewById(R.id.traineesList);
        ListView evaluatorsList = (ListView) findViewById(R.id.evaluatorsList);

        SimpleAdapter adapter = new SimpleAdapter(this, itemList,
                android.R.layout.simple_list_item_2, new String[] {"name",
                "program"}, new int[] { android.R.id.text1,
                android.R.id.text2 });
        traineesList.setAdapter(adapter);

        ArrayAdapter<String> evaluatorsAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, evaluatorNames);
        evaluatorsList.setAdapter(evaluatorsAdapter);

        traineesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(Program.loggedInUser.getProgram().equals(traineeProgramIndices.get(position))) {
                    Intent i = new Intent(getApplicationContext(), CoordinatorOBTraineeActivity.class);
                    i.putExtra(StringDefines.username_attr, traineeIndices.get(position));
                    startActivity(i);
                }
            }
        });

        evaluatorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                    Intent i = new Intent(getApplicationContext(), EvaluatorProfileActivity.class);
                    Evaluator chosen = evaluatorIndices.get(position);
                    i.putExtra(StringDefines.username_attr, chosen.getUsername());
                    i.putExtra(StringDefines.name_attr, chosen.getName());
                    i.putExtra(StringDefines.email_attr, chosen.getEmail());
                    startActivity(i);
                }
        });

    }

    // move to a different activity to modify OBs of the program
    public void changeOB (View view){
        Intent i = new Intent(this, ChangeOBActivity.class);
        startActivity(i);
    }

    public void addTrainees (View view){
        Intent i = new Intent(this, AssignTraineesActivity.class);
        startActivity(i);
    }

    // create menu at top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluator, menu);
        return true;
    }

    // handling menu at top
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

    public void emailAllTrainees(View view) {
        for(Trainee t : LoginActivity.globalTrainees) {
            if(t.getProgram().equals(LoginActivity.globalProgram.getProgName())) {
                t.sendTraineeEmail();
            }
        }
    }

}
