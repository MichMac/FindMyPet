package com.example.findmypet.models;

public class User {

    private String uID;
    private String name;
    private String email;
    private String phoneNumber;

//    private boolean isAuthenticated;
//    private boolean isNew, isCreated;

    public User() {}

//    public User(String uID,String name,String email) {
//        this.uID = uID;
//        this.name = name;
//        this.email = email;
//    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public boolean isAuthenticated() {
//        return isAuthenticated;
//    }
//
//    public void setAuthenticated(boolean authenticated) {
//        isAuthenticated = authenticated;
//    }
//
//    public boolean isNew() {
//        return isNew;
//    }
//
//    public void setNew(boolean aNew) {
//        isNew = aNew;
//    }
}
