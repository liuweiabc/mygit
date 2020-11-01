package com.example.stsystem3;

public class task {
    private String ta;
    private String time;

    public task(String ta,String time){
        this.ta = ta;
        this.time = time;
    }

    public String getTa() {
        return ta;
    }

    public void setTa(String ta) {
        this.ta = ta;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
