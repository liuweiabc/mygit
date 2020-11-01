package com.example.calendarview;

import java.util.Date;

public class AccountModel {


    /*private int id;*/
    private long date;
    private float income;
    private float outcome;
    private String description;
    private String name;
    //constructor
    public AccountModel(String name,long date, float income, float outcome, String description){
        /*this.id = id;*/
        this.date = date;
        this.income = income;
        this.outcome = outcome;
        this.description = description;
        this.name = name;
    }

    public AccountModel(){

    }

    // toString is necessary for printing the content of a class object
    @Override
    public String toString() {
        /*"com.example.calendarview.accountModel{" +
                "id=" +id +*/
        DateUtils du = new DateUtils();
        String printDate = du.timeStamp2Date(date);
                if(income!=0){
                    return
                            printDate +
                            "\nIncome: " + income +
                            /*", Outcome: " + outcome +*/
                            "\nDescription: '" + description + '\'';
                }else {
                    return printDate +
                            /*"\nIncome: " + income +*/
                            "\nOutcome: " + outcome +
                            "\nDescription: '" + description + '\'';
                }
    }

    //Getters & Setters
    /*public int getId() { return id; }

    public void setId(int id) { this.id = id; }*/

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public float getOutcome() {
        return outcome;
    }

    public void setOutcome(float outcome) {
        this.outcome = outcome;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){return name;};

    public void setName(String name) {this.name = name; }
}
