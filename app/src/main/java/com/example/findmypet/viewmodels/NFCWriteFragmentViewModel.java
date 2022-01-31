package com.example.findmypet.viewmodels;

import android.content.Intent;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.PetProfileRepository;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NFCWriteFragmentViewModel extends ViewModel{

    PetProfileRepository mPetProfileRepository;
    MutableLiveData<PetProfile> mPetProfile;

    public void init() {
        mPetProfileRepository = PetProfileRepository.getInstance();
        mPetProfile = mPetProfileRepository.getPetProfile();
    }

    public MutableLiveData<PetProfile> getPetProfile(){
        return mPetProfile;
    }

}
