package com.example.stsystem3;

public class person {
    private String address;
    private String name;
    private String peonumber;
    private String phone;


    public person(String address, String name, String peonumber, String phone) {
        super();
        this.address = address;
        this.name = name;
        this.peonumber = peonumber;
        this.phone = phone;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeonumber() {
        return peonumber;
    }

    public void setPeonumber(String peonumber) {
        this.peonumber = peonumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return  address + "," + name + ", " + peonumber+ ", " + phone ;
    }

}