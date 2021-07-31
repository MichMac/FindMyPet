package com.example.findmypet.viewmodels;

import android.app.Application;
import android.net.Uri;

import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetProfileListViewModel extends ViewModel {

    private PetProfileRepository mPetProfileRepository;
    private MutableLiveData<List<PetProfile>> mPetProfiles;

    public void init(){
        if(mPetProfiles != null){
            return;
        }
        mPetProfileRepository = PetProfileRepository.getInstance();
        mPetProfiles = mPetProfileRepository.getPetProfiles();
    }

    public LiveData<List<PetProfile>> getPetProfiles(){
        return mPetProfiles;
    }

    public void addPetProfile(PetProfile petProfile){
        mPetProfileRepository.addPetProfile(petProfile);
    }

    public void addPetProfilePicture(Uri PetProfilePicURL, String PetName){
        mPetProfileRepository.addPetProfilePicture(PetProfilePicURL,PetName);
    }

    public void deletePetProfile(PetProfile petProfile){
        mPetProfileRepository.deletePetProfile(petProfile);
    }

    public LiveData<Boolean> getIsLoading(){
        LiveData<Boolean> isLoading=mPetProfileRepository.getIsLoading();
        return isLoading;
    }

}