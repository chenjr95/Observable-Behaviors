package a.cis350project;

/**
 * Created by socratesli on 4/1/15.
 *
 * Tests used at various stages of development to see if new features work.
 *
 */
class PutUsersTest {
    public static void putTraineesAndEvaluatorsTest() {

        /*
        // put some OB's and rubric items
        String ob1 = "Giving bad news to a patient";
        String ob2 = "Providing daily medicine";
        String ob3 = "Doing liver surgery";
        String ob4 = "Heart Surgery";
        String progA = "Internal Medicine";

        String ob5 = "Creating long-term care plan for patient";
        String ob6 = "Performing bi-annual checkup on patient";
        String ob7 = "Diagnosing patient with illness";
        String progB = "Family Medicine";

        ArrayList<String> rubric1 = new ArrayList<>();
        rubric1.add("Talks in a language the patient can understand");
        rubric1.add("Answers all questions respectfully and professionally");
        rubric1.add( "Provides an adequate summary of next steps");

        ArrayList<String> rubric2 = new ArrayList<>();
        rubric2.add("Accurately reviews medications allergy");
        rubric2.add("Provides appropriate doses of medications");
        rubric2.add( "Answers any questions about medications");

        ArrayList<String> rubric3 = new ArrayList<>();
        rubric3.add("Obtains appropriate informed consent");
        rubric3.add("Provides technical aspects of surgery appropriately");
        rubric3.add( "Provides appropriate post operative care");

        ArrayList<String> rubric4 = new ArrayList<>();
        rubric4.add("placeholder_heart_surgery");

        ArrayList<String> rubric5 = new ArrayList<>();
        rubric5.add("placeholder_care_plan");

        ArrayList<String> rubric6 = new ArrayList<>();
        rubric6.add("placeholder_checkup");

        ArrayList<String> rubric7 = new ArrayList<>();
        rubric7.add("placeholder_diagnose");

        ObsBe item1 = new ObsBe(ob1, progA, rubric1);
        ObsBe item2 = new ObsBe(ob2, progA, rubric2);
        ObsBe item3 = new ObsBe(ob3, progA, rubric3);
        ObsBe item4 = new ObsBe(ob4, progA, rubric4);
        ObsBe item5 = new ObsBe(ob5, progB, rubric5);
        ObsBe item6 = new ObsBe(ob6, progB, rubric6);
        ObsBe item7 = new ObsBe(ob7, progB, rubric7);

        item1.toParseObject().saveInBackground();
        item2.toParseObject().saveInBackground();
        item3.toParseObject().saveInBackground();
        item4.toParseObject().saveInBackground();
        item5.toParseObject().saveInBackground();
        item6.toParseObject().saveInBackground();
        item7.toParseObject().saveInBackground();
        */

        /*
        String progName = "Family Medicine";
        // some Evaluators
        Evaluator evalA = new Evaluator(progName, "new_world", "Antonín Dvořák", "2Symphony2New",
                "much_wow@abc.com");
        Evaluator evalB = new Evaluator(progName, "yippee", "Hans Gruber", "nakatomi",
                "theo_pls@gruber_family.com");
        Evaluator evalC = new Evaluator(progName, "monkey_king", "Wu Cheng'en", "longwaywest",
                "pig@classics.com");
        Evaluator evalD = new Evaluator(progName, "thestrings", "Ravi Shankar", "pluckpluck",
                "plucked@string.com");

        // some Trainees
        Trainee trainA = new Trainee(progName, "homeland", "Li Bai", "frostyHome",
                "tangPoet@ancients.com");
        Trainee trainB = new Trainee(progName, "nodnod", "Sorcerer Nick", "cantdestroynick1337",
                "magic@uwelcome.com");
        Trainee trainC = new Trainee(progName, "whereartthou", "Bill Shakes", "macbethwithcheese",
                "dad@2be.com");
        Trainee trainD = new Trainee(progName, "strongcc", "Sambasiva Rao Kosaraju",
                "needAdjMatrix", "dag@graphs.com");
        Trainee trainE = new Trainee(progName, "oim8", "Owl Flight", "nighthunt",
                "barnowl@birds.com");
        Trainee trainF = new Trainee(progName, "notPizza", "John McClane", "diehardly",
                "cop@ny.com");
        Trainee trainG = new Trainee(progName, "simonsays", "Simon Gruber", "morethanknox",
                "kreig@gruber_family.com");

        // a Coordinator
        Coordinator coord = new Coordinator(progName, "johnny", "Stanley Kubrick", "molokoPlus",
                "sorry@dave.com");

        Set<Evaluator> evals = new HashSet<Evaluator>();
        evals.add(evalA);
        evals.add(evalB);
        evals.add(evalC);
        evals.add(evalD);

        Set<Trainee> trains = new HashSet<Trainee>();
        trains.add(trainA);
        trains.add(trainB);
        trains.add(trainC);
        trains.add(trainD);
        trains.add(trainE);
        trains.add(trainF);
        trains.add(trainG);

        Set<Coordinator> coords = new HashSet<Coordinator>();
        coords.add(coord);

        List<String> obsBehavs = new ArrayList<String>();
        obsBehavs.add("Creating long-term care plan for patient");
        obsBehavs.add("Performing bi-annual checkup on patient");
        obsBehavs.add("Diagnosing patient with illness");


        // create a new Program
        Program gene = new Program(progName, trains, evals, coords, new HashSet<Director>(), obsBehavs);

        // save all to cloud
        gene.toParseObject().saveInBackground();
        for (Trainee curr : trains) {
            curr.toParseObject().saveInBackground();
        }
        for (Evaluator curr : evals) {
            curr.toParseObject().saveInBackground();
        }
        for (Coordinator curr : coords) {
            curr.toParseObject().saveInBackground();
        }
        */

        /*
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
        query.whereEqualTo(StringDefines.username_attr, "berns123");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> people, ParseException e) {
                if (e == null) {
                    if (people.size() != 1) {
                        System.err.println("FETCHING ORIGINAL PERSON WENT WRONG");
                        return;
                    }
                    ParseObject loggedInObj = people.get(0);
                    Program myProgram = Program.fetchWholeProgram(loggedInObj);
                    System.out.println(myProgram);
                } else {
                    e.printStackTrace();
                }
            }
        });
        */

        /*
        // test to see if we can save a program
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
        query.whereEqualTo(StringDefines.program_attr, "Internal Medicine");
        List<ParseObject> people = null;
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> people, ParseException e) {
                if (e == null) {
                    LinkedList<Evaluator> evals = new LinkedList<Evaluator>();
                    LinkedList<Trainee> trains = new LinkedList<Trainee>();
                    for (ParseObject curr : people) {
                        String type = curr.getString(StringDefines.typeofuser_attr);
                        if (type.equals(StringDefines.trainee_type)) {
                            trains.add(new Trainee(curr));
                        } else if (type.equals(StringDefines.evaluator_type)) {
                            evals.add(new Evaluator(curr));
                        } else if (type.equals(StringDefines.coordinator_type)) {
                            // no coordinators yet
                        } else if (type.equals(StringDefines.director_type)) {
                            // no directors yet
                        }
                    }
                    ArrayList<String> observableBehaviors = new ArrayList<String>();
                    observableBehaviors.add("Giving bad news to a patient");
                    observableBehaviors.add("Providing daily medicine");
                    observableBehaviors.add("Doing liver surgery");
                    //Program prog = new Program(trains, evals, new LinkedList<Coordinator>(), new LinkedList<Director>(), observableBehaviors);
                    //prog.toParseObject().saveInBackground();
                } else {
                    e.printStackTrace();
                }
            }
        });
        */
        /*
        // test to see if we can save evaluations
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
        ArrayList<String> usernames = new ArrayList<String>();
        usernames.add("get it git");
        usernames.add("clout_atlas");
        query.whereContainedIn(StringDefines.username_attr, usernames);
        List<ParseObject> people = null;
        try {
            people = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        if (people.size() != 2) {
            System.err.println("wrong num ppl " + people.size());
            System.exit(-1);
        }

        Evaluator eval = null;
        Trainee train = null;
        if (people.get(0).getString(StringDefines.typeofuser_attr).equals(
                StringDefines.evaluator_type)) {
            eval = new Evaluator(people.get(0));
            train = new Trainee(people.get(1));
        } else {
            eval = new Evaluator(people.get(1));
            train = new Trainee(people.get(0));
        }

        Evaluation eval1 = new Evaluation(eval.getUsername(), train.getUsername(), "Talking to families",
                "Great job, showed calmness.", 5, new GregorianCalendar());

        eval1.toParseObject().saveInBackground();
        */
        /*
        // test to see if we can submit evaluation
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
        ArrayList<String> usernames = new ArrayList<String>();
        usernames.add("get it git");
        usernames.add("clout_atlas");
        query.whereContainedIn(StringDefines.username_attr, usernames);
        List<ParseObject> people = null;
        try {
            people = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        if (people.size() != 2) {
            System.err.println("wrong num ppl " + people.size());
            System.exit(-1);
        }

        Evaluator eval = null;
        Trainee train = null;
        if (people.get(0).getString(StringDefines.typeofuser_attr).equals(
                StringDefines.evaluator_type)) {
            eval = new Evaluator(people.get(0));
            train = new Trainee(people.get(1));
        } else {
            eval = new Evaluator(people.get(1));
            train = new Trainee(people.get(0));
        }

        Evaluation eval1 = new Evaluation(eval, train,
                "OB: Talking to families. Great job, showed calmness.", 5, new GregorianCalendar());

        eval1.toParseObject().saveInBackground();
        */
        /*
        // test to see if we can update and not re-submit a user
        ParseQuery<ParseObject> query = ParseQuery.getQuery(StringDefines.user_obj);
        query.whereEqualTo(StringDefines.username_attr, "flash");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> scoreList, ParseException e) {
                if (e == null) {
                    if (scoreList.size() != 1) {
                        System.err.println("many objects are flash???");
                        System.exit(-1);
                    }
                    ParseObject trainee1 = scoreList.get(0);
                    Trainee flash = new Trainee(trainee1);
                    flash.name = "Albert Albert True Ruse";
                    flash.toParseObject().saveInBackground();
                } else {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        });
        */

        /*
        // test  to see if we can store string lists in Parse
        String program1 = "TestProgram2";

        Trainee trainee1 = new Trainee(program1, "clout_atlas", "Sorcerer Nick", "bubble_tea_LOL");
        Trainee trainee2 = new Trainee(program1, "?riddler?", "Misterio Spider", "alkaline");


        Evaluator eval1 = new Evaluator(program1, "get it git", "Jar Jelly", "Hamlette");


        ArrayList<Trainee> trainees = new ArrayList<Trainee>();
        trainees.add(trainee1);
        trainees.add(trainee2);

        User.makeTrainEvalRelations(eval1, trainees);

        ParseObject pTrain1 = trainee1.toParseObject();
        ParseObject pTrain2 = trainee2.toParseObject();
        ParseObject pEval1 = eval1.toParseObject();

        pEval1.saveInBackground();
        pTrain1.saveInBackground();
        pTrain2.saveInBackground();
        */

        /*

        Trainee trainee1 = new Trainee(program1, "andy_lol2fast", "Alexandria Chen", "fufufu");
        Trainee trainee2 = new Trainee(program1, "damn_good_job_sir", "Chrissy Champoigne", "jubejube");
        User eval1 = new Evaluator(program1, "twice the jeez", "Janet Tuple",
                "mega_boss3333");



        ParseObject postTrain1 = new ParseObject(StringDefines.user_obj);
        postTrain1.put(StringDefines.program_attr, trainee1.getProgram());
        postTrain1.put(StringDefines.username_attr, trainee1.getUsername());
        postTrain1.put(StringDefines.name_attr, trainee1.getName());
        postTrain1.put(StringDefines.password_attr, trainee1.getPassword());
        postTrain1.put(StringDefines.typeofuser_attr, trainee1.getTypeOfUser());
        ParseObject postTrain2 = new ParseObject(StringDefines.user_obj);
        postTrain2.put(StringDefines.program_attr, trainee2.getProgram());
        postTrain2.put(StringDefines.username_attr, trainee2.getUsername());
        postTrain2.put(StringDefines.name_attr, trainee2.getName());
        postTrain2.put(StringDefines.password_attr, trainee2.getPassword());
        postTrain2.put(StringDefines.typeofuser_attr, trainee2.getTypeOfUser());
        ParseObject postEval1 = new ParseObject(StringDefines.user_obj);
        postEval1.put(StringDefines.program_attr, eval1.getProgram());
        postEval1.put(StringDefines.username_attr, eval1.getUsername());
        postEval1.put(StringDefines.name_attr, eval1.getName());
        postEval1.put(StringDefines.password_attr, eval1.getPassword());
        postEval1.put(StringDefines.typeofuser_attr, eval1.getTypeOfUser());


        ParseRelation<ParseObject> relation = postEval1.getRelation(StringDefines.training_relation);
        relation.add(postTrain1);
        relation.add(postTrain2);
        */

        //postTrain1.saveInBackground();
        //postTrain2.saveInBackground();

        //postEval1.saveInBackground();

        /*
        Coordinator c = new Coordinator("Internal Medicine", "batman", "Bruce Wayne", "callofduty");
        ParseObject p = c.toParseObject();
        p.saveInBackground();
        */
    }


}