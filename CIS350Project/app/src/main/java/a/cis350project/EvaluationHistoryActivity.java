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
 * Created by champ_000 on 4/2/2015.
 *
 * Activity to view history of evaluations between an evaluator and trainee
 *
 */
public class EvaluationHistoryActivity extends Activity {

    // on create, query for evaluations
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_history);
        String evaluatorUsername = getIntent().getStringExtra("EVALUATOR_USERNAME");
        String traineeUsername = getIntent().getStringExtra("TRAINEE_USERNAME");
        final ListView historyList = (ListView) findViewById(R.id.histories);
        final ArrayList<String> historyTexts = new ArrayList<>();
        final android.content.Context selfReference = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.evaluation_obj);
        query.whereEqualTo(StringDefines.evaluator_username_attr, evaluatorUsername);
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
                    for (Evaluation curr : evals) {
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
