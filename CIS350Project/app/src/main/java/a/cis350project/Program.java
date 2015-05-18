package a.cis350project;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by socratesli on 4/4/15.
 *
 * Class for a program
 *
 */
public class Program implements StoredInParseCloud {

    private final Set<Trainee> trainees;
    private final Set<Evaluator> evaluators;
    private final Set<Coordinator> coordinators;
    private final ArrayList<String> obsBehavs;
    private String parseObjectId;
    private final String progName;
    static User loggedInUser;

    // constructor for creating new program
    public Program(String progName, Set<Trainee> trainees, Set<Evaluator> evaluators,
                   Set<Coordinator> coordinators, List<String> obsBehavs) {
        this.trainees = trainees;
        this.evaluators = evaluators;
        this.coordinators = coordinators;
        this.obsBehavs = new ArrayList<>(obsBehavs);
        this.progName = progName;
    }

    // constructor given ParseObject and all Program users
    private Program(ParseObject prog, Set<Trainee> trainees, Set<Evaluator> evaluators,
                    Set<Coordinator> coordinators) {
        this.trainees = trainees;
        this.evaluators = evaluators;
        this.coordinators = coordinators;
        obsBehavs = new ArrayList<>(
                StringDefines.
                        fromCommaDelimString((prog.getString(StringDefines.prog_obs_bes_attr))));
        parseObjectId = prog.getObjectId();
        progName = prog.getString(StringDefines.prog_name_attr);
    }

    // convert this object to a ParseObject
    public ParseObject toParseObject() {
        ParseObject program;
        if (parseObjectId == null) {
            program = new ParseObject(StringDefines.prog_obj);
        } else {
            program = ParseObject.createWithoutData(StringDefines.prog_obj, parseObjectId);
        }
        LinkedList<String> evalUsernames = new LinkedList<>();
        for (Evaluator curr : evaluators) {
            evalUsernames.add(curr.getUsername());
        }
        LinkedList<String> trainUsernames = new LinkedList<>();
        for (Trainee curr : trainees) {
            trainUsernames.add(curr.getUsername());
        }
        LinkedList<String> coordUsernames = new LinkedList<>();
        for (Coordinator curr : coordinators) {
            coordUsernames.add(curr.getUsername());
        }
        program.put(StringDefines.prog_evaluators_attr, StringDefines.toCommaDelimString(evalUsernames));
        program.put(StringDefines.prog_trainees_attr, StringDefines.toCommaDelimString(trainUsernames));
        program.put(StringDefines.prog_coordinators_attr, StringDefines.toCommaDelimString(coordUsernames));
        program.put(StringDefines.prog_obs_bes_attr, StringDefines.toCommaDelimString(obsBehavs));
        program.put(StringDefines.prog_name_attr, progName);
        return program;
    }

    // get parseObjectId of this object in Parse cloud
    @Override
    public String getParseObjectId() {
        return parseObjectId;
    }

    // fetch everything related to program to create a fully populated instance of Program
    // should be called in a background thread
    public static Program fetchWholeProgram(ParseObject userObj) {

        // sets for each type of user
        HashSet<Trainee> trainees = new HashSet<>();
        HashSet<Evaluator> evaluators = new HashSet<>();
        HashSet<Coordinator> coordinators = new HashSet<>();

        // create user current and put it into the correct data structures
        User current = null;
        String type = userObj.getString(StringDefines.typeofuser_attr);
        switch (type) {
            case StringDefines.coordinator_type:
                current = new Coordinator(userObj);
                coordinators.add((Coordinator) current);
                break;
            case StringDefines.evaluator_type:
                current = new Evaluator(userObj);
                Evaluator eval = (Evaluator) current;
                evaluators.add(eval);
                break;
            case StringDefines.trainee_type:
                current = new Trainee(userObj);
                Trainee train = (Trainee) current;
                trainees.add(train);
                break;
        }

        // set the static field for user who logged in
        loggedInUser = current;

        String programName = current.getProgram();
        // query for program
        ParseQuery<ParseObject> programQuery = ParseQuery.getQuery(StringDefines.prog_obj);
        programQuery.whereEqualTo(StringDefines.prog_name_attr, programName);
        List<ParseObject> progFound = null;
        try {
            progFound = programQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (progFound.size() == 0) {
            System.err.println("Program not found while trying to fetch whole program");
            return null;
        }
        ParseObject prog = progFound.get(0);
        // query for users in program
        ParseQuery<ParseObject> everyoneQuery = ParseQuery.getQuery(StringDefines.user_obj);
        everyoneQuery.whereEqualTo(StringDefines.program_attr, programName);
        // ignore the user we already queried, which was passed in as a parameter
        everyoneQuery.whereNotEqualTo(StringDefines.username_attr, current.getUsername());
        List<ParseObject> everyone = null;
        try {
            everyone = everyoneQuery.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (ParseObject curr : everyone) {
            String userType = curr.getString(StringDefines.typeofuser_attr);
            switch (userType) {
                case StringDefines.coordinator_type:
                    coordinators.add(new Coordinator(curr));
                    break;
                case StringDefines.evaluator_type:
                    Evaluator eval = new Evaluator(curr);
                    evaluators.add(eval);
                    break;
                case StringDefines.trainee_type:
                    Trainee train = new Trainee(curr);
                    trainees.add(train);
                    break;
            }
        }

        return new Program(prog, trainees, evaluators, coordinators);
    }

    // get set of trainees
    public Set<Trainee> getTrainees() {
        return trainees;
    }

    // get set of evaluators
    public Set<Evaluator> getEvaluators() {
        return evaluators;
    }

    // get set of coordinators
    public Set<Coordinator> getCoordinators() {
        return coordinators;
    }

    // get list of OBs
    public ArrayList<String> getObsBehavs() {
        return obsBehavs;
    }

    // get program name
    public String getProgName() {
        return progName;
    }

    // convert object to String
    public String toString() {
        String accu = "";
        accu += "Coordinator Usernames:\n";
        for (Coordinator curr : coordinators) {
            accu += curr.getUsername() + "\n";
        }
        accu += "Evaluator Usernames:\n";
        for (Evaluator curr : evaluators) {
            accu += curr.getUsername() + "\n";
        }
        accu += "Trainee Usernames:\n";
        for (Trainee curr : trainees) {
            accu += curr.getUsername() + "\n";
        }
        if (loggedInUser != null) {
            accu += "fetchWholeProgram was used to create the program; " +
                    "the user that logged in was " + loggedInUser.getUsername() + ".\n";
        }
        return accu;
    }

    // add OB; return true if added and false otherwise
    public boolean addObsBehav(String toAdd) {
        if (obsBehavs.contains(toAdd)) {
            return false;
        }
        obsBehavs.add(toAdd);
        return true;
    }

    // remove OB; return true if removed and false otherwise
    public boolean removeObsBehav(String toRemove) {
        return obsBehavs.remove(toRemove);
    }

    public static void addUserToProgram(Program prog, User toAdd) {
        String userType = toAdd.getTypeOfUser();
        switch (userType) {
            case StringDefines.coordinator_type:
                prog.coordinators.add((Coordinator) toAdd);
                break;
            case StringDefines.evaluator_type:
                prog.evaluators.add((Evaluator) toAdd);
                break;
            case StringDefines.trainee_type:
                prog.trainees.add((Trainee) toAdd);
                break;
        }
        toAdd.program = prog.progName;
    }

    // remove a user from the program
    public static void removeUserFromProgram(Program prog, User toRemove) {
        prog.coordinators.remove(toRemove);
        prog.evaluators.remove(toRemove);
        prog.trainees.remove(toRemove);
        toRemove.program = StringDefines.default_program;
    }
}
