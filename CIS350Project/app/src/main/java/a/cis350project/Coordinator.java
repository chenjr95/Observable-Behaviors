package a.cis350project;

import com.parse.ParseObject;

/**
 * Created by socratesli on 4/5/15.
 *
 * Class for coordinator
 *
 */
public class Coordinator extends User {

    // constructor for new coordinators
    public Coordinator(String program, String username, String name,
                     String password, String email) {
        this.program = program;
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        typeOfUser = StringDefines.coordinator_type;
    }

    // constructor given ParseObject
    public Coordinator(ParseObject coordinator) {
        program = coordinator.getString(StringDefines.program_attr);
        username = coordinator.getString(StringDefines.username_attr);
        name = coordinator.getString(StringDefines.name_attr);
        password = coordinator.getString(StringDefines.password_attr);
        email = coordinator.getString(StringDefines.email_attr);
        typeOfUser = StringDefines.coordinator_type;
        parseObjectId = coordinator.getObjectId();
    }

    // turn this object into ParseObject
    public ParseObject toParseObject() {
        ParseObject coord;
        if (parseObjectId == null) {
            coord = new ParseObject(StringDefines.user_obj);
        } else {
            coord = ParseObject.createWithoutData(StringDefines.user_obj, parseObjectId);
        }
        coord.put(StringDefines.program_attr, program);
        coord.put(StringDefines.username_attr, username);
        coord.put(StringDefines.name_attr, name);
        coord.put(StringDefines.password_attr, password);
        coord.put(StringDefines.typeofuser_attr, typeOfUser);
        coord.put(StringDefines.email_attr, email);
        return coord;
    }
}
