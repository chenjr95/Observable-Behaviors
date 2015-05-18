package a.cis350project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/*
 * Activity for profile of Evaluator
 */
public class EvaluatorProfileActivity extends Activity {
    private String evalUsername;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluator_profile);

        String evalName = getIntent().getStringExtra(StringDefines.name_attr);
        evalUsername = getIntent().getStringExtra(StringDefines.username_attr);
        String evalEmail = getIntent().getStringExtra(StringDefines.email_attr);

        TextView evalNameText = (TextView) findViewById(R.id.evaluatorName);
        TextView evalUsernameText = (TextView) findViewById(R.id.evaluatorUsername);
        TextView evalEmailText = (TextView) findViewById(R.id.evaluatorEmail);

        evalNameText.setText("Name: " + evalName);
        evalUsernameText.setText("Username: " + evalUsername);
        evalEmailText.setText("Email: " + evalEmail);
    }

    // create menu at top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_evaluator_profile, menu);
        return true;
    }

    public void evaluationHistory (View view) {
        Intent i = new Intent(this, EvaluatorHistoryActivity.class);
        i.putExtra("EVALUATOR_USERNAME", evalUsername);
        startActivity(i);
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
