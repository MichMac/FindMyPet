package com.example.findmypet.models;

public class Announcement {

    private String status;
    private String userID;
    private String date;
    private String phoneNumber;
    private String country;
    private String province;
    private String city;
    private String street;
    private String petImageUrl;
    private String petName;
    private String petSpecie;
    private String petBreed;
    private String petGender;
    private int petMicrochipNumber;
    private String nfcID;
    private String petDescription;


    public Announcement(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID;}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSpecie() {
        return petSpecie;
    }

    public void setPetSpecie(String petSpecie) {
        this.petSpecie = petSpecie;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public int getPetMicrochipNumber() {
        return petMicrochipNumber;
    }

    public void setPetMicrochipNumber(int petMicrochipNumber) {
        this.petMicrochipNumber = petMicrochipNumber;
    }

    public String getNfcID() {
        return nfcID;
    }

    public void setNfcID(String nfcID) {
        this.nfcID = nfcID;
    }

    public String getPetDescription() {
        return petDescription;
    }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetBreed() { return petBreed;}

    public void setPetBreed(String petBreed) { this.petBreed = petBreed;}


}
