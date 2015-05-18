package a.cis350project;

import com.parse.ParseObject;

/**
 * Created by champ_000 on 4/1/2015.
 *
 * Class for evaluator
 *
 */
public class Evaluator extends User {

    // constructor for new evaluator
    public Evaluator(String program, String username, String name,
                     String password, String email) {
        this.program = program;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        typeOfUser = StringDefines.evaluator_type;
    }

    // constructor for evaluator given attributes and parseObjectId
    public Evaluator(String program, String username, String name,
                     String password, String email, String parseObjectId) {
        this.program = program;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        typeOfUser = StringDefines.evaluator_type;
        this.parseObjectId = parseObjectId;
    }

    // constructor given ParseObject; does not establish eval-trainee relationships
    public Evaluator(ParseObject evaluator) {
        program = evaluator.getString(StringDefines.program_attr);
        username = evaluator.getString(StringDefines.username_attr);
        name = evaluator.getString(StringDefines.name_attr);
        password = evaluator.getString(StringDefines.password_attr);
        email = evaluator.getString(StringDefines.email_attr);
        typeOfUser = StringDefines.evaluator_type;
        parseObjectId = evaluator.getObjectId();
    }

    // turn this object into ParseObject
    public ParseObject toParseObject() {
        ParseObject eval;
        if (parseObjectId == null) {
            eval = new ParseObject(StringDefines.user_obj);
        } else {
            eval = ParseObject.createWithoutData(StringDefines.user_obj, parseObjectId);
        }
        eval.put(StringDefines.program_attr, program);
        eval.put(StringDefines.username_attr, username);
        eval.put(StringDefines.name_attr, name);
        eval.put(StringDefines.password_attr, password);
        eval.put(StringDefines.typeofuser_attr, typeOfUser);
        eval.put(StringDefines.email_attr, email);
        return eval;
    }
}
