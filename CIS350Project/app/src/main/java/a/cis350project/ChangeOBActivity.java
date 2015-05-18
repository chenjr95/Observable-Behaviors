package a.cis350project;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andy on 4/15/2015.
 *
 * Activity to add or remove OBs
 *
 */
public class ChangeOBActivity extends Activity {

    // on create, set content view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeob);
    }

    // show the OBs we can use
    public void showOB(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Observable Behaviors");
        ArrayList<String> listOB = LoginActivity.globalProgram.getObsBehavs();
        String Ob = "";
        for(String s : listOB){
            Ob = Ob + "-" + s + "\n";
        }
        alertDialog.setMessage(Ob);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    // add an OB
    public void addOB(View view){
        EditText OBview = (EditText) findViewById(R.id.Obtoadd);
        String newOB = OBview.getText().toString();
        if (newOB.isEmpty()) {
            Toast.makeText(this, "Please specify an Observable Behavior.", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        boolean addObSuccess = LoginActivity.globalProgram.addObsBehav(newOB);
        if(addObSuccess){
            OBview.setText("");

            // add an OB with no rubric items
            new ObsBe(newOB, LoginActivity.globalProgram.getProgName(), new ArrayList<String>())
                    .toParseObject().saveInBackground();

            LoginActivity.globalProgram.toParseObject().saveInBackground();

            Toast.makeText(this, "Observable Behavior added successfully.",
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Observable Behavior already exists.",
                    Toast.LENGTH_LONG).show();
        }
    }

    // remove an OB
    public void removeOB(View view){
        EditText OBview = (EditText) findViewById(R.id.OBtoremove);
        String removeOB = OBview.getText().toString();
        boolean removeObSuccess = LoginActivity.globalProgram.removeObsBehav(removeOB);
        if(removeObSuccess){
            OBview.setText("");
            LoginActivity.globalProgram.toParseObject().saveInBackground();

            ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.obs_be_obj);
            query.whereEqualTo(StringDefines.program_attr,
                    LoginActivity.globalProgram.getProgName());
            query.whereEqualTo(StringDefines.ob_name_attr, removeOB);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> evalList, ParseException e) {
                    if (e == null) {
                        if (evalList.size() == 1) {
                            evalList.get(0).deleteInBackground();
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });

            Toast.makeText(this, "Observable Behavior removed successfully.",
                    Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Observable Behavior doesn't exist.",
                    Toast.LENGTH_LONG).show();
        }
    }
}