package a.cis350project;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by socratesli on 4/21/15.
 *
 * Class for an observable behavior
 *
 */
public class ObsBe implements StoredInParseCloud {
    private final String name;
    private final String program;
    private final ArrayList<String> rubric;
    private String parseObjectId;

    // constructor for new OB
    public ObsBe(String name, String program, List<String> rubric) {
        this.name = name;
        this.program = program;
        this.rubric = new ArrayList<>(rubric);
    }

    // constructor for an existing OB with ParseObject
    public ObsBe(ParseObject ob) {
        name = ob.getString(StringDefines.ob_name_attr);
        program = ob.getString(StringDefines.program_attr);
        rubric = new ArrayList<>(StringDefines.fromCommaDelimString(
                ob.getString(StringDefines.ob_rubric_attr)));
        parseObjectId = ob.getObjectId();
    }

    // get a ParseObject of this OB
    @Override
    public ParseObject toParseObject() {
        ParseObject ob;
        if (parseObjectId == null) {
            ob = new ParseObject(StringDefines.obs_be_obj);
        } else {
            ob = ParseObject.createWithoutData(StringDefines.obs_be_obj, parseObjectId);
        }
        ob.put(StringDefines.ob_name_attr, name);
        ob.put(StringDefines.program_attr, program);
        ob.put(StringDefines.ob_rubric_attr, StringDefines.toCommaDelimString(rubric));
        return ob;
    }

    // get this OB's ParseObjectId
    @Override
    public String getParseObjectId() {
        return parseObjectId;
    }

    // get name of OB
    public String getName() {
        return name;
    }

    // get rubric items of OB
    public ArrayList<String> getRubric() {
        return rubric;
    }

    // get program of OB
    public String getProgram() {
        return program;
    }

    // add item to rubric, returns true if added and false if the item is already in rubric
    public boolean rubricAdd(String item) {
        if (rubric.contains(item)) {
            return false;
        }
        rubric.add(item);
        return true;
    }

    // remove item from rubric, returns true if removed and false if the item was not in rubric
    public boolean rubricRemove(String item) {
        return rubric.remove(item);
    }

}
