package a.cis350project;

import com.parse.FindCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.HashMap;
import java.util.List;

/**
 * Created by champ_000 on 4/1/2015.
 *
 * Class for trainee
 *
 */
public class Trainee extends User {

    // constructor for creating a new Trainee
    public Trainee(String program, String username, String name, String password, String email) {
        this.program = program;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        typeOfUser = StringDefines.trainee_type;
    }

    // constructor for creating a Trainee given all attributes and parseObjectId
    public Trainee(String program, String username, String name, String password, String email,
                   String parseObjectId) {
        this.program = program;
        this.username = username;
        this.name = name;
        this.password = password;
        typeOfUser = StringDefines.trainee_type;
        this.email = email;
        this.parseObjectId = parseObjectId;
    }

    // constructor given ParseObject; does not establish eval-trainee relationships
    public Trainee(ParseObject trainee) {
        program = trainee.getString(StringDefines.program_attr);
        username = trainee.getString(StringDefines.username_attr);
        name = trainee.getString(StringDefines.name_attr);
        password = trainee.getString(StringDefines.password_attr);
        email = trainee.getString(StringDefines.email_attr);
        typeOfUser = StringDefines.trainee_type;
        parseObjectId = trainee.getObjectId();
    }

    // turn this into a ParseObject
    public ParseObject toParseObject() {
        ParseObject trainee;
        if (parseObjectId == null) {
            trainee = new ParseObject(StringDefines.user_obj);
        } else {
            trainee = ParseObject.createWithoutData(StringDefines.user_obj, parseObjectId);
        }
        trainee.put(StringDefines.program_attr, program);
        trainee.put(StringDefines.username_attr, username);
        trainee.put(StringDefines.name_attr, name);
        trainee.put(StringDefines.password_attr, password);
        trainee.put(StringDefines.typeofuser_attr, typeOfUser);
        trainee.put(StringDefines.email_attr, email);
        return trainee;
    }

    public void sendTraineeEmail() {
        final Trainee instanceOfTrainee = this;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.evaluation_obj);
        query.whereEqualTo(StringDefines.trainee_username_attr, this.username);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> evalList, ParseException e) {
                if (e == null) {
                    Evaluation curr;
                    HashMap<String, Object> params;
                    StringBuilder emailBuilder = new StringBuilder();
                    emailBuilder.append(instanceOfTrainee.name).append(":\n");
                    for (ParseObject p : evalList) {
                        curr = new Evaluation(p);
                        emailBuilder.append(StringDefines.traineeOBLine).append(curr.getObsBe()).append("\n");
                        emailBuilder.append(StringDefines.traineeScoreLine).append(curr.getLikertScale()).append("\n");
                        emailBuilder.append(StringDefines.traineeCommentLine);
                        emailBuilder.append("\t").append(curr.getText()).append("\n\n");
                    }

                    params = new HashMap<>();
                    String email = instanceOfTrainee.email;
                    params.put(StringDefines.recipEmail, email);
                    params.put(StringDefines.recipName, instanceOfTrainee.name);
                    params.put(StringDefines.subj, StringDefines.traineeSubject);
                    params.put(StringDefines.message, emailBuilder.toString());
                    ParseCloud.callFunctionInBackground(StringDefines.email_func, params);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
