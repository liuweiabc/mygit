package com.example.xuh.shopping;

public class message {
    private String nicheng;
    private String username;

    public message(String nicheng,String username){
        this.nicheng = nicheng;
        this.username = username;
    }

    public void setNicheng(String nicheng) {
        this.nicheng = nicheng;
    }

    public String getNicheng() {
        return nicheng;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
}
