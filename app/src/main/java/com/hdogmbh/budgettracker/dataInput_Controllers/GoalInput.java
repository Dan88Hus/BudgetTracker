package com.hdogmbh.budgettracker.dataInput_Controllers;

public class GoalInput {
    private long goalAmount;
    private String type;
    private String date;
    private String uid; // user id of Firebase firestore


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public GoalInput(){

    }

    public long getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(long incomeAmount) {
        this.goalAmount = goalAmount;
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

    public GoalInput(long goalAmount, String type, String date, String uid) {
        this.goalAmount = goalAmount;
        this.type = type;
        this.date = date;
        this.uid = uid;


    }

}
