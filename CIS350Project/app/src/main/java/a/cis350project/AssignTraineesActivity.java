package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andy on 4/15/2015.
 *
 * Activity for assigning trainees to an evaluator
 *
 */
public class AssignTraineesActivity extends Activity {
    private final HashMap<Trainee, Integer> traineeMap = new HashMap<>();

    // on creation, we initialize the evaluator and the trainees we could assign to this evaluator
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_trainees);
        String programName = LoginActivity.globalProgram.getProgName();

        ListView traineeList = (ListView) findViewById(R.id.traineesList);
        ArrayList<String> traineeNames = new ArrayList<>();
        int index = 0;
        for(Trainee t: LoginActivity.globalTrainees) {
            if(t.getProgram().equals(programName) || t.getProgram().equals(StringDefines.default_program)) {
                traineeNames.add(t.getName());
                traineeMap.put(t, index);
                index++;
            }
        }

        ArrayAdapter<String> traineesAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice,
                        traineeNames);
        traineeList.setAdapter(traineesAdapter);
        for(Trainee t : traineeMap.keySet()){
            if(t.getProgram().equals(programName)){
                traineeList.setItemChecked(traineeMap.get(t), true);
            }
        }
    }

    public void done (View view){
        ListView traineeList = (ListView) findViewById(R.id.traineesList);
        for(Trainee t : traineeMap.keySet()) {
            if (traineeList.isItemChecked(traineeMap.get(t))
                    && !(LoginActivity.globalProgram.getProgName().equals(t.getProgram()))) {
                addTrainee(t);
            } else if(!traineeList.isItemChecked(traineeMap.get(t))
                    && LoginActivity.globalProgram.getProgName().equals(t.getProgram())){
                removeTrainee(t);
            }
        }
        Intent i = new Intent(this, CoordinatorActivity.class);
        startActivity(i);
    }

    //add trainee to program
    private void addTrainee(Trainee t){
        Program.addUserToProgram(LoginActivity.globalProgram, t);
        ParseObject trainee = t.toParseObject();
        ParseObject program = LoginActivity.globalProgram.toParseObject();
        trainee.saveInBackground();
        program.saveInBackground();
    }

    //remove trainee from program
    private void removeTrainee(Trainee t){
        Program.removeUserFromProgram(LoginActivity.globalProgram, t);
        ParseObject trainee = t.toParseObject();
        ParseObject program = LoginActivity.globalProgram.toParseObject();
        trainee.saveInBackground();
        program.saveInBackground();
    }
}
