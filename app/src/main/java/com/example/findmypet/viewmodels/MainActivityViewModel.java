package com.example.findmypet.viewmodels;

import android.app.Application;
import android.content.Intent;

import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AuthRepository;
import com.example.findmypet.repositories.PetProfileRepository;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private AuthRepository mAuthAppRepository;
    private PetProfileRepository mPetProfileRepository;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
    private MutableLiveData<Boolean> mLoggedOutLiveData;
    private MutableLiveData<PetProfile> mPetProfile;
    private MutableLiveData<Intent> mIntent = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository= new AuthRepository(application);
        mPetProfileRepository = PetProfileRepository.getInstance();
        mPetProfile = mPetProfileRepository.getPetProfile();
        mFirebaseUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
        mLoggedOutLiveData = mAuthAppRepository.getLoggedOutLiveData();

    }

    public void logOut() {
        mAuthAppRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return mLoggedOutLiveData;
    }

    public void setIntent(Intent intent) { mIntent.setValue(intent); }

    public LiveData<Intent> getIntent() { return mIntent; }

    public LiveData<PetProfile> getPetProfile() { return mPetProfile; }

//    public void setNewIntent(Intent intent){
//        mIntentMutableLiveData.setValue(intent);
//    }

}
