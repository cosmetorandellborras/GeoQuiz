package com.example.geoquiz;

public class Question {
    //Atributos
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;
    private boolean mAnsweredCorrect;
    //Constructor
    public Question(int textResId,boolean answerTrue, boolean answered){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
        mAnswered = answered;
    }
    //Getters y setters
    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int mTextResId) {
        this.mTextResId = mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean mAnswerTrue) {
        this.mAnswerTrue = mAnswerTrue;
    }

    public boolean isAnswered(){
        return mAnswered;
    }
    public void setAnswered(boolean answered){
        this.mAnswered = answered;
    }

    public boolean isAnsweredCorrect(){
        return mAnsweredCorrect;
    }
    public void setAnsweredCorrect(boolean answeredCorrect){
        mAnsweredCorrect = answeredCorrect;
    }
}
