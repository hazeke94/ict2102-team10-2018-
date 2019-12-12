package com.aztheman.uncharted;


public class option {

    int optID;
    String optionDesc;

    public option(int optID,String optionDesc){
        this.optID = optID;
        this.optionDesc = optionDesc;
    }

    public int getOptID() {
        return optID;
    }

    @Override
    public String toString() {
        return "option{" +
                "optID=" + optID +
                ", optionDesc='" + optionDesc + '\'' +
                '}';
    }

    public void setOptID(int optID) {
        this.optID = optID;
    }

    public String getOptionDesc() {
        return optionDesc;
    }

    public void setOptionDesc(String optionDesc) {
        this.optionDesc = optionDesc;
    }
}