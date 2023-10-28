package com.hdogmbh.budgettracker.dataInput_Controllers;

public class IncomeInput {
    private long incomeAmount;
    private String type;
    private String date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid; // user id of Firebase firestore

    public IncomeInput(){

    }

    public long getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(long incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public IncomeInput(long incomeAmount, String type, String date, String uid) {
        this.incomeAmount = incomeAmount;
        this.type = type;
        this.date = date;
        this.uid = uid;


    }

}
