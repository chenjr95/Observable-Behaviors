package a.cis350project;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by socratesli on 4/1/15.
 *
 * File to standardize String names of attributes and object types in Parse cloud
 *
 */
class StringDefines {
    public static final String trainee_type = "Trainee";
    public static final String evaluator_type = "Evaluator";
    public static final String coordinator_type = "Coordinator";
    public static final String program_attr = "Program";
    public static final String username_attr = "Username";
    public static final String name_attr = "Name";
    public static final String password_attr = "Password";
    public static final String typeofuser_attr = "Type";
    public static final String email_attr = "Email";
    public static final String user_obj = "User";
    public static final String evaluation_obj = "Evaluation";
    public static final String text_attr = "Eval_Text";
    public static final String likert_scale_attr = "Eval_Likert_Scale";
    public static final String date_time_str_attr = "Timestamp";
    public static final String hidden_text_attr = "Eval_Hidden_Text";
    public static final String obs_be_attr = "Observable_Behavior";
    public static final String prog_obj = "Program";
    public static final String prog_trainees_attr = "Trainees";
    public static final String prog_evaluators_attr = "Evaluators";
    public static final String prog_coordinators_attr = "Coordinators";
    public static final String prog_obs_bes_attr = "Observable_Behaviors";
    public static final String evaluator_username_attr = "Evaluator";
    public static final String trainee_username_attr = "Trainee";
    public static final String prog_name_attr = "Program_Name";
    public static final String default_program = "No Program";
    public static final String obs_be_obj = "Obs_Be";
    public static final String obs_prog_attr = "Program";
    public static final String ob_name_attr = "Name";
    public static final String ob_rubric_attr = "Rubric_Items";
    public static final String recipEmail = "recipientEmail";
    public static final String recipName = "recipientName";
    public static final String subj = "subject";
    public static final String message = "message";
    public static final String oneScore = " received a score of 1!";
    public static final String email_func = "sendEmail";
    public static final String likert_desc1 = "Critical deficiency";
    public static final String likert_desc2 = "Inconsistent; direct supervision required";
    public static final String likert_desc3 = "Some direct supervision required";
    public static final String likert_desc4 = "Ready for unsupervised practice";
    public static final String likert_desc5 = "Exceptional";
    public static final String traineeSubject = "Observable Behavior Digest Email";
    public static final String traineeOBLine = "You completed: ";
    public static final String traineeScoreLine = "You received a score of: ";
    public static final String traineeCommentLine = "Your comments were:\n";

    // convert a list of strings to a comma delimited string
    public static String toCommaDelimString(Collection<String> strs) {
        StringBuilder outputBuilder = new StringBuilder();
        Iterator<String> iter = strs.iterator();
        while (iter.hasNext()) {
            outputBuilder.append(iter.next());
            if (iter.hasNext()) {
                outputBuilder.append(',');
            }
        }
        return outputBuilder.toString();
    }

    // convert a comma delimited string to a list of strings
    public static List<String> fromCommaDelimString(String str) {
        return Arrays.asList(str.split(","));
    }
}
