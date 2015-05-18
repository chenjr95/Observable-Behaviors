package a.cis350project;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity{
    private static final Integer WRONG_USERNAME = 0;
    private static final Integer WRONG_PASSWORD = 1;
    private static final Integer LOGIN_SUCCESS = 2;
    static Program globalProgram = null;
    static ArrayList<Trainee> globalTrainees = null;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "NzGhhEKGcdmj2fe7saut3aAiCnUTglqrLvPvcEBe",
                "nKRKEwwa7RohXi4McnQQZfwwUEvi8Nfvn1mbsH14");

        // set the timezone to America/New_York
        Evaluation.dtFormat.setTimeZone(TimeZone.getTimeZone("America/New_York"));


        // Set up the login form
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mUsernameSignInButton = (Button) findViewById(R.id.username_sign_in_button);
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(username, password, this);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {
        List<ParseObject> users = null;
        private final String mUsername;
        private final String mPassword;
        private final Activity loginActivity;

        UserLoginTask(String username, String password, Activity a) {
            mUsername = username;
            mPassword = password;
            loginActivity = a;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
            query.whereEqualTo(StringDefines.username_attr, mUsername);
            try{
                users = query.find();
            }
            catch (com.parse.ParseException e){
                Log.d("Error in first query", e.getMessage());
            }
            if(users.size() == 0){
                return WRONG_USERNAME;
            }
            else if(!(users.get(0).getString("Password").equals(mPassword))){
                return WRONG_PASSWORD;
            }
            else{
                return LOGIN_SUCCESS;
            }
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            List<ParseObject> queriedTrainees = null;
            if (success.equals(LOGIN_SUCCESS)) {
                EditText usernameField = (EditText) findViewById(R.id.username);
                EditText passwordField = (EditText) findViewById(R.id.password);
                usernameField.setText("");
                passwordField.setText("");

                // we always fetch whole program; global user is program's loggedInUser
                globalProgram = Program.fetchWholeProgram(users.get(0));

                if(users.get(0).getString(StringDefines.typeofuser_attr).equals(StringDefines.evaluator_type)) {
                    Intent i = new Intent(loginActivity, EvaluatorActivity.class);
                    startActivity(i);
                }
                else if(users.get(0).getString(StringDefines.typeofuser_attr).equals(StringDefines.coordinator_type)){
                    globalTrainees = new ArrayList<>();

                    // Add the trainees to globalTrainees
                    ParseQuery<ParseObject> traineesQuery = ParseQuery.getQuery(StringDefines.user_obj);
                    traineesQuery.whereEqualTo(StringDefines.typeofuser_attr, StringDefines.trainee_username_attr);
                    try{
                        queriedTrainees = traineesQuery.find();
                    }
                    catch (com.parse.ParseException e){
                        Log.d("Error in first query", e.getMessage());
                    }
                    for(ParseObject p : queriedTrainees){
                        globalTrainees.add(new Trainee(p));
                    }

                    // Add the evaluators
                    ParseQuery<ParseObject> evaluatorQuery = ParseQuery.getQuery(StringDefines.user_obj);
                    evaluatorQuery.whereEqualTo(StringDefines.typeofuser_attr, StringDefines.evaluator_username_attr);
                    try {
                        traineesQuery.find();
                    }
                    catch (com.parse.ParseException e) {
                        Log.d("Error in first query", e.getMessage());
                    }

                    Intent i = new Intent(loginActivity, CoordinatorActivity.class);
                    startActivity(i);
                }
            } else if(success.equals(WRONG_PASSWORD)){
                mPasswordView.setText("");
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
            else{
                mUsernameView.setError(getString(R.string.error_incorrect_username));
                mUsernameView.requestFocus();
            }
            showProgress(false);
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}



