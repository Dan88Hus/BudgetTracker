package com.hdogmbh.budgettracker.dataInput_Controllers;

import java.util.Date;

public class DataInput {
    private long amount;
    private String type;
    private int id;
    private Date date;

    public DataInput(){

    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public DataInput(long amount, String type, int id, Date date) {
        this.amount = amount;
        this.type = type;
        this.id = id;
        this.date = date;


    }

}
