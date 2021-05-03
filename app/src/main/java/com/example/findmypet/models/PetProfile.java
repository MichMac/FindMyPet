package com.example.findmypet.models;

public class PetProfile {

    private String name;
    private String specie;
    private String gender;
    private String breed;
    private String age;
    private String description;
    private String image_url;
    private String microchip_number;
    private String nfc_id;
    private String user_id;

    public PetProfile(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMicrochip_number() {
        return microchip_number;
    }

    public void setMicrochip_number(String microchip_number) { this.microchip_number = microchip_number;
    }

    public String getNfc_id() {
        return nfc_id;
    }

    public void setNfc_id(String nfc_id) {
        this.nfc_id = nfc_id;
    }
}
