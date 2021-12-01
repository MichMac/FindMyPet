package com.example.findmypet.models;

import java.io.Serializable;

public class PetProfile implements Serializable {

    private String name;
    private String specie;
    private String gender;
    private String breed;
    private int age;
    private String description;
    private String petImageUrl;
    private Long microchipNumber;
    private String nfcID;
    private String userID;

    public PetProfile(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSpecies() {
        return specie;
    }

    public void setSpecies(String specie) {
        this.specie = specie;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    public long getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(long microchipNumber) { this.microchipNumber = microchipNumber; }

    public String getNfcID() {
        return nfcID;
    }

    public void setNfcID(String nfcID) {
        this.nfcID = nfcID;
    }

    @Override
    public String toString(){
        return name;
    }
}
