package com.example.findmypet.viewmodels;

import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetProfileViewModel extends ViewModel {

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
}