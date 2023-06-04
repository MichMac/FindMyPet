package com.example.findmypet.models;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announcement implements Serializable {

    private String status;
    private String userID;
    private Date date;
    private String phoneNumber;
    private String city;
    private String location;
    private GeoPoint LatLng;
    private String petProfileID;
    private String petImageUrl;
    private String petName;
    private String petSpecie;
    private String petBreed;
    private String petGender;
    private Long petMicrochipNumber;
    private String petDescription;
    private String announcementID;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public Announcement(){}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID;}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {return date;}

    public String getFormatedDate() {return formatter.format(date);}

    public void setDate(Date date) {
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

    public long getPetMicrochipNumber() {
        return petMicrochipNumber;
    }

    public void setPetMicrochipNumber(long petMicrochipNumber) { this.petMicrochipNumber = petMicrochipNumber; }

    public String getPetProfileID() {return petProfileID; }

    public void setPetProfileID(String petProfileID) { this.petProfileID = petProfileID; }
    
    public String getPetDescription() { return petDescription; }

    public void setPetDescription(String petDescription) {
        this.petDescription = petDescription;
    }

    public String getPetBreed() { return petBreed;}

    public void setPetBreed(String petBreed) { this.petBreed = petBreed;}

    public GeoPoint getLatLng() {
        return LatLng;
    }

    public void setLatLng(GeoPoint latLng) {
        LatLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAnnouncementID() {
        return announcementID;
    }

    public void setAnnouncementID(String announcementID) {
        this.announcementID = announcementID;
    }
}
