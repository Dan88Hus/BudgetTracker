package com.hdogmbh.budgettracker.dataInput_Controllers;

public class ExpenseInput {
    private long expenseAmount;
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
        return expenseAmount;
    }

    public void setExpenseAmount(long expenseAmount) {
        this.expenseAmount = expenseAmount;
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

    public ExpenseInput(long expenseAmount, String type, String date, String uid) {
        this.expenseAmount = expenseAmount;
        this.type = type;
        this.date = date;
        this.uid = uid;


    }

}
