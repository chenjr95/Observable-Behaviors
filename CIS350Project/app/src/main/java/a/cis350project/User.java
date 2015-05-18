package a.cis350project;

/**
 * Created by champ_000 on 4/1/2015.
 *
 * abstract class for users
 *
 */
public abstract class User implements StoredInParseCloud {
    String program;
    String username;
    String name;
    String password;
    String typeOfUser;
    String parseObjectId;
    String email;

    // get string name of program
    public String getProgram() {
        return program;
    }

    // get usernames
    public String getUsername() {
        return username;
    }

    // get name
    public String getName() {
        return name;
    }

    // get password
    public String getPassword() {
        return password;
    }

    // get type of user string
    public String getTypeOfUser() {
        return typeOfUser;
    }

    // get ParseObjectId
    public String getParseObjectId() {
        return parseObjectId;
    }

    // get email
    public String getEmail() {
        return email;
    }

}
