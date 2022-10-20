package com.example.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;
    private TextView nQuestionsCorrect;
    private TextView nQuestionsWrong;
    private static final String KEY_INDEX = "index";
    private int correctAnswers = 0;
    private int wrongAnswers = 0;
    //private float questionAccurage = 0;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia,true,false),
            new Question(R.string.question_oceans,true,false),
            new Question(R.string.question_mideast,false,false),
            new Question(R.string.question_africa,false,false),
            new Question(R.string.question_americas,true,false),
            new Question(R.string.question_asia,true,false),
    };
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState !=null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            mQuestionBank[0].setAnswered(savedInstanceState.getBoolean("0"));
            mQuestionBank[1].setAnswered(savedInstanceState.getBoolean("1"));
            mQuestionBank[2].setAnswered(savedInstanceState.getBoolean("2"));
            mQuestionBank[3].setAnswered(savedInstanceState.getBoolean("3"));
            mQuestionBank[4].setAnswered(savedInstanceState.getBoolean("4"));
            mQuestionBank[5].setAnswered(savedInstanceState.getBoolean("5"));
            correctAnswers = savedInstanceState.getInt("correct");
            wrongAnswers = savedInstanceState.getInt("wrong");
        }

        nQuestionsCorrect = (TextView) findViewById(R.id.ncorrectquestions);
        nQuestionsWrong = (TextView) findViewById(R.id.nwrongquestions);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex +1) % mQuestionBank.length;
                Log.i("Index =", String.valueOf(mCurrentIndex));

                updateQuestion();
            }
        });

        mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex -1) % mQuestionBank.length;
                if(mCurrentIndex<0){
                    mCurrentIndex = mQuestionBank.length + (mCurrentIndex-1);
                }
                Log.i("Index =", String.valueOf(mCurrentIndex));
                updateQuestion();
            }
        });

        updateQuestion();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
        savedInstanceState.putBoolean("0",mQuestionBank[0].isAnswered());
        savedInstanceState.putBoolean("1",mQuestionBank[1].isAnswered());
        savedInstanceState.putBoolean("2",mQuestionBank[2].isAnswered());
        savedInstanceState.putBoolean("3",mQuestionBank[3].isAnswered());
        savedInstanceState.putBoolean("4",mQuestionBank[4].isAnswered());
        savedInstanceState.putBoolean("5",mQuestionBank[5].isAnswered());
        savedInstanceState.putInt("correct",correctAnswers);
        savedInstanceState.putInt("wrong",wrongAnswers);

    }
    private void updateQuestion(){
        checkTotalAnswers();
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        nQuestionsCorrect.setText(String.valueOf(correctAnswers));
        nQuestionsWrong.setText(String.valueOf(wrongAnswers));
        //Esto es lo modificado
        if(mQuestionBank[mCurrentIndex].isAnswered() == true){
            mFalseButton.setEnabled(false);
            mTrueButton.setEnabled(false);
            if(mQuestionBank[mCurrentIndex].isAnsweredCorrect()==true){
                if(mQuestionBank[mCurrentIndex].isAnswerTrue()==true){
                    mTrueButton.setBackgroundColor(Color.GREEN);
                    mFalseButton.setBackgroundColor(mFalseButton.getContext().getResources().getColor(R.color.purple_700));
                }
                else if(mQuestionBank[mCurrentIndex].isAnswerTrue()==false){
                    mFalseButton.setBackgroundColor(Color.GREEN);
                    mTrueButton.setBackgroundColor(mFalseButton.getContext().getResources().getColor(R.color.purple_700));
                }


            }
            else if (mQuestionBank[mCurrentIndex].isAnsweredCorrect()==false){
                if(mQuestionBank[mCurrentIndex].isAnswerTrue()==true){
                    mFalseButton.setBackgroundColor(Color.RED);
                    mTrueButton.setBackgroundColor(mTrueButton.getContext().getResources().getColor(R.color.purple_700));
                }else if(mQuestionBank[mCurrentIndex].isAnswerTrue()==true){
                    mTrueButton.setBackgroundColor(Color.RED);
                    mFalseButton.setBackgroundColor(mTrueButton.getContext().getResources().getColor(R.color.purple_700));
                }

            }
        }else if (mQuestionBank[mCurrentIndex].isAnswered() == false){
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
            mFalseButton.setBackgroundColor(mFalseButton.getContext().getResources().getColor(R.color.purple_700));
            mTrueButton.setBackgroundColor(mTrueButton.getContext().getResources().getColor(R.color.purple_700));
        }

    }
    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        mQuestionBank[mCurrentIndex].setAnswered(true);

        int messageResId = 0;
        if (userPressedTrue == answerIsTrue){
            mQuestionBank[mCurrentIndex].setAnsweredCorrect(true);
            correctAnswers = correctAnswers+1;
            messageResId = R.string.correct_toast;
        } else {
            mQuestionBank[mCurrentIndex].setAnsweredCorrect(false);
            wrongAnswers = wrongAnswers+1;
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(getApplicationContext(),messageResId,Toast.LENGTH_SHORT).show();
    }
    private void checkTotalAnswers(){
        if(wrongAnswers+correctAnswers==mQuestionBank.length){
            Intent intent = new Intent(getApplicationContext(),ResultsActivity.class);
            float questionAccurage = (float)correctAnswers/(float)mQuestionBank.length*100;
            Log.i("Score", String.valueOf(correctAnswers));
            Log.i("Score", String.valueOf(mQuestionBank.length));
            Log.i("Score", String.valueOf(questionAccurage));
            intent.putExtra("percent",String.valueOf(questionAccurage));
            startActivity(intent);
        }
    }
}