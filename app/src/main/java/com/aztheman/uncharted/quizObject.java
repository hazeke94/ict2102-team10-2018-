package com.aztheman.uncharted;

public class quizObject {

    int questionID;
    String question;
    String optionSelected;
    String optionID;


    public quizObject(int questionNum, String question, String optionSelected, String optionID){
        this.questionID = questionNum;
        this.question = question;
        this.optionSelected = optionSelected;
        this.optionID = optionID;
    }

    public int getQuestionNum() {
        return questionID;
    }

    public void setQuestionNum(int questionNum) {
        this.questionID = questionNum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionSelected() {
        return optionSelected;
    }

    public void setOptionSelected(String optionSelected) {
        this.optionSelected = optionSelected;
    }

    public String getOptionID() {
        return optionID;
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }
}
