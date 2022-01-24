package com.example.findmypet.viewmodels;

import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PetProfileViewModel extends ViewModel {

    private PetProfileRepository mPetProfileRepository;
    private MutableLiveData<PetProfile> mPetProfile;

    public void init(){
        if (mPetProfile != null){
            return;
        }
        mPetProfileRepository = PetProfileRepository.getInstance();
        mPetProfile =  mPetProfileRepository.getPetProfile();
    }

    public void updatePetProfile(PetProfile petProfile){ mPetProfileRepository.updatePetProfile(petProfile); }

    public MutableLiveData<PetProfile> getPetProfile(){
        return mPetProfile;
    }

}