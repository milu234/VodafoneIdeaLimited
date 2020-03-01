package com.example.otplogin;




import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SurveyActivity extends AppCompatActivity {

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();


    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);


        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);

        updateQuestion();

        //Start of Button Listener for Button1
        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mButtonChoice1.setBackgroundColor(Color.rgb(0, 204, 68));

                //My logic for Button goes in here
                updateQuestion();
                mButtonChoice1.setBackgroundColor(Color.rgb(0, 145, 234));

            }
        });

        //End of Button Listener for Button1

        //Start of Button Listener for Button2
        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mButtonChoice2.setBackgroundColor(Color.rgb(0, 204, 68));

                //My logic for Button goes in here

               updateQuestion();
                mButtonChoice2.setBackgroundColor(Color.rgb(0, 145, 234));
            }
        });

        //End of Button Listener for Button2


        //Start of Button Listener for Button3
        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mButtonChoice3.setBackgroundColor(Color.rgb(0, 204, 68));
                //My logic for Button goes in here
                mButtonChoice3.setBackgroundColor(Color.rgb(0, 145, 234));

               updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
//        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        if(mQuestionNumber++ == 3 ){
            openDialog();
            UpdateCoins.updateVodaCoins("20");
        }
    }

    public void openDialog(){
        ThankDialog thankDialog = new ThankDialog();
        thankDialog.show(getSupportFragmentManager(),"thank Dialog");
    }
}