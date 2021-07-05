package com.example.findmypet.viewmodels;

import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.ArrayList;
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
        //mPetProfiles = mPetProfileRepository.getPetProfiles();

        //Dummy data
        mPetProfiles = new MutableLiveData<>();
        List<PetProfile> petProfileList = new ArrayList<>();
        PetProfile petProfile = new PetProfile();
        mPetProfiles = new MutableLiveData<>();
        petProfile.setImage_url("https://cdn.wamiz.pl/media/cache/upload_main-image_414w/uploads/animal/breed/dog/baby/5caf14853910b375817947.jpg");
        petProfile.setName("Azor");
        petProfileList.add(petProfile);
        petProfileList.add(petProfile);
        petProfileList.add(petProfile);

        mPetProfiles.postValue(petProfileList);
    }

    public LiveData<List<PetProfile>> getPetProfiles(){
        return mPetProfiles;
    }

    public void addPetProfile(PetProfile petProfile){
        mPetProfileRepository.addPetProfile(petProfile);
    }
}