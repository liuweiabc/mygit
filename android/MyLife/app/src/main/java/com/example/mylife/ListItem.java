package com.example.mylife;

public class ListItem {
    private Float Income,Outcome;
    private String time;
    private Float total;

    public ListItem(Float Income,Float Outcome,String time,Float total){
        this.Income = Income;
        this.Outcome = Outcome;
        this.time = time;
        this.total = total;
    }

    public void setIncome(Float income) {
        Income = income;
    }
    public Float getIncome() {
        return Income;
    }

    public void setOutcome(Float outcome) {
        Outcome = outcome;
    }

    public Float getOutcome() {
        return Outcome;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getTotal() {
        return total;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
