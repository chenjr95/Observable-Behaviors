package a.cis350project;

import com.parse.ParseObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by socratesli on 4/2/15.
 *
 * class for evaluations
 *
 */
public class Evaluation implements StoredInParseCloud, Comparable<Evaluation> {
    private String evalUsername;
    private String trainUsername;
    private String text;
    private String hiddenText;
    private int likertScale;
    private GregorianCalendar dateTime;
    private String parseObjectId;
    private String obsBe;

    public static final SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
            Locale.US);

    // constructor for new evaluations
    public Evaluation(String evalUsername, String trainUsername, String obsBe, String text,
                      String hiddenText, int likertScale, GregorianCalendar dateTime) {
        if (likertScale < 1 || likertScale > 5) {
            System.err.println("Invalid likert scale value of " + likertScale);
            return;
        }
        this.evalUsername = evalUsername;
        this.trainUsername = trainUsername;
        this.text = text;
        this.hiddenText = hiddenText;
        this.obsBe = obsBe;
        this.likertScale = likertScale;
        this.dateTime = dateTime;
    }

    // constructor given all the members including the parseObjectId
    public Evaluation(String evalUsername, String trainUsername, String obsBe, String text,
                      String hiddenText, int likertScale, GregorianCalendar dateTime,
                      String parseObjectId) {
        if (likertScale < 1 || likertScale > 5) {
            System.err.println("Invalid likert scale value of " + likertScale + ".");
            return;
        }
        this.evalUsername = evalUsername;
        this.trainUsername = trainUsername;
        this.obsBe = obsBe;
        this.text = text;
        this.hiddenText = hiddenText;
        this.likertScale = likertScale;
        this.parseObjectId = parseObjectId;
        this.dateTime = dateTime;
    }

    // constructor given ParseObject
    public Evaluation(ParseObject evaluation) {
        int likertFromObj = evaluation.getInt(StringDefines.likert_scale_attr);
        if (likertFromObj < 1 || likertFromObj > 5) {
            System.err.println("Invalid likert scale value of " + likertFromObj);
            return;
        }
        evalUsername = evaluation.getString(StringDefines.evaluator_username_attr);
        trainUsername = evaluation.getString(StringDefines.trainee_username_attr);
        obsBe = evaluation.getString(StringDefines.obs_be_attr);
        text = evaluation.getString(StringDefines.text_attr);
        hiddenText = evaluation.getString(StringDefines.hidden_text_attr);
        likertScale = likertFromObj;
        dateTime = new GregorianCalendar();
        try {
            dateTime.setTime(
                    dtFormat.parse(evaluation.getString(StringDefines.date_time_str_attr)));
        } catch (ParseException e) {
            System.err.println(
                    "Error parsing date time in Evaluation constructor taking in ParseObject");
            e.printStackTrace();
        }
        parseObjectId = evaluation.getObjectId();
    }

    // convert this object to a ParseObject
    public ParseObject toParseObject() {
        ParseObject evaluation;
        if (parseObjectId == null) {
            evaluation = new ParseObject(StringDefines.evaluation_obj);
        } else {
            evaluation = ParseObject.createWithoutData(StringDefines.evaluation_obj, parseObjectId);
        }
        evaluation.put(StringDefines.evaluator_username_attr, evalUsername);
        evaluation.put(StringDefines.trainee_username_attr, trainUsername);
        evaluation.put(StringDefines.text_attr, text);
        evaluation.put(StringDefines.hidden_text_attr, hiddenText);
        evaluation.put(StringDefines.likert_scale_attr, likertScale);
        evaluation.put(StringDefines.date_time_str_attr, dtFormat.format(dateTime.getTime()));
        evaluation.put(StringDefines.obs_be_attr, obsBe);
        return evaluation;
    }

    // get parseObjectId of this object in the Parse cloud
    @Override
    public String getParseObjectId() {
        return parseObjectId;
    }

    // get username of evaluator
    public String getEvalUsername() {
        return evalUsername;
    }

    // get username of trainee
    public String getTrainUsername() {
        return trainUsername;
    }

    // get main text of evaluation
    public String getText() {
        return text;
    }

    // get hidden text of evaluation
    public String getHiddenText() {
        return hiddenText;
    }

    // get likert scale score on this evaluation
    public int getLikertScale() {
        return likertScale;
    }

    // get string representation of timestamp
    public String getTimeString() {
        return dtFormat.format(dateTime.getTime());
    }

    // get observable behavior
    public String getObsBe() {
        return obsBe;
    }

    // get GregorianCalendar object
    private GregorianCalendar getDateTime() {
        return dateTime;
    }

    // convert whole object to String without hidden text
    public String toString() {
        return toString(false);
    }

    // convert whole object to String with hidden text
    public String toString(boolean showHidden) {
        String accu = "";
        accu += "Evaluator: " + evalUsername + "\n";
        accu += "Trainee: " + trainUsername + "\n";
        accu += "OB: " + obsBe + "\n";
        accu += "Likert: " + likertScale + "\n";
        accu += text + "\n";
        if (showHidden) {
            accu += "Hidden: " + hiddenText + "\n";
        }
        accu += "Timestamp: " + dtFormat.format(dateTime.getTime());
        return accu;
    }

    // object comparisons compare the timestamp
    @Override
    public int compareTo(Evaluation another) {
        return dateTime.compareTo(another.getDateTime());
    }

    // return whether this evaluation has a hidden text field
    public boolean hasHidden() {
        return !hiddenText.isEmpty();
    }
}
