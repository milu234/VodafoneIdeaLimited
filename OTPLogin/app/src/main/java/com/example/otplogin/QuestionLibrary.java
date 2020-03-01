package com.example.otplogin;

public class QuestionLibrary {
    private String mQuestions [] = {
            "How does the app run after the update?",
            "How do you like the app design?",
            "How would you rate our app?",
            "Would you recommend this app to your friends?"

    };

    private String mChoices [][] = {
            {"Fast", "Slow", "Crash"},
            {"Good", "Bad", "Medium"},
            {"Great", "Ok", "Cool"},
            {"Yes", "No", "Not Much"}
    };


//    private String mCorrectAnswers[] = {"Roots", "Leaves", "Flower", "Stem"};




    public String getQuestion(int a) {
        String question = mQuestions[a];
        return question;
    }


    public String getChoice1(int a) {
        String choice0 = mChoices[a][0];
        return choice0;
    }


    public String getChoice2(int a) {
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a) {
        String choice2 = mChoices[a][2];
        return choice2;
    }

//    public String getCorrectAnswer(int a) {
//        String answer = m[a];
//        return answer;
//    }

    }
