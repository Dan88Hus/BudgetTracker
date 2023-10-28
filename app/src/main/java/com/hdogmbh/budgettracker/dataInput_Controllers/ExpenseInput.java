package com.hdogmbh.budgettracker.dataInput_Controllers;

public class ExpenseInput {
    private long ExpenseAmount;
    private String type;
    private String date;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid; // user id of Firebase firestore

    public ExpenseInput(){

    }

    public long getExpenseAmount() {
        return ExpenseAmount;
    }

    public void setExpenseAmount(long expenseAmount) {
        this.ExpenseAmount = expenseAmount;
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

    public ExpenseInput(long ExpenseAmount, String type, String date, String uid) {
        this.ExpenseAmount = ExpenseAmount;
        this.type = type;
        this.date = date;
        this.uid = uid;


    }

}
