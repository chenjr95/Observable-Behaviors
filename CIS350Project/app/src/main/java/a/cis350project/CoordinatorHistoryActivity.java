package a.cis350project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by champ_000 on 4/12/2015.
 *
 * view history of a trainee for a certain OB
 *
 */
public class CoordinatorHistoryActivity extends Activity {
    /*
     * ListView with each Observable Behavior and number of Evals for it
     * If number of Evals > 0 then open new activity, otherwise toast "No evals for this OB"
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_history);
        String traineeUsername = getIntent().getStringExtra(StringDefines.username_attr);
        String obsBehaviorName = getIntent().getStringExtra(StringDefines.obs_be_attr);
        final ListView historyList = (ListView) findViewById(R.id.histories);
        final ArrayList<String> historyTexts = new ArrayList<>();
        final android.content.Context selfReference = this;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.evaluation_obj);
        query.whereEqualTo(StringDefines.obs_be_attr, obsBehaviorName);
        query.whereEqualTo(StringDefines.trainee_username_attr, traineeUsername);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> evalList, ParseException e) {
                if (e == null) {
                    Evaluation next;
                    ArrayList<Evaluation> evals = new ArrayList<>();
                    for (ParseObject p : evalList) {
                        next = new Evaluation(p);
                        evals.add(next);
                    }
                    // sort the evaluations by timestamp
                    // Collections.sort(evals, Collections.reverseOrder());
                    for (Evaluation curr : evals) {
                        // show the history with the hidden strings
                        historyTexts.add(curr.toString(true));
                    }
                    ArrayAdapter<String> arrayAdapter =
                            new ArrayAdapter<>(selfReference, android.R.layout.simple_list_item_1, historyTexts);
                    historyList.setAdapter(arrayAdapter);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
