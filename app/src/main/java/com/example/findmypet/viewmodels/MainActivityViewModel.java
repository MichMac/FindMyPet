package com.example.findmypet.viewmodels;

import android.app.Application;
import android.content.Intent;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.AuthRepository;
import com.example.findmypet.repositories.PetProfileRepository;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private AuthRepository mAuthAppRepository;
    private AnnouncementRepository mAnnouncementRepository;
    private PetProfileRepository mPetProfileRepository;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
    private MutableLiveData<Boolean> mLoggedOutLiveData;
    private MutableLiveData<Intent> mIntentMutableLiveData;
    private MutableLiveData<Announcement> mAnnouncementMutableLiveData;
    private MutableLiveData<Boolean> mIsAnnouncementFound;
    private MutableLiveData<PetProfile> mPetProfile;


    public MainActivityViewModel (@NonNull Application application) {
        super(application);
        mAnnouncementRepository = AnnouncementRepository.getInstance();
        mAuthAppRepository= new AuthRepository(application);
        mPetProfileRepository = PetProfileRepository.getInstance();
        //mFirebaseUserMutableLiveData = mAuthAppRepository.getFirebaseUserLiveData();
        mIntentMutableLiveData = new MutableLiveData<>();
        mFirebaseUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
        mLoggedOutLiveData = mAuthAppRepository.getLoggedOutLiveData();
        mIsAnnouncementFound = mAnnouncementRepository.isAnnouncementFound();
        mPetProfile = mPetProfileRepository.getPetProfile();
    }

    public void logOut() {
        mAuthAppRepository.logOut();
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }

    public MutableLiveData<PetProfile> getPetProfile(){
        return mPetProfile;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return mLoggedOutLiveData;
    }

    public MutableLiveData<Intent> getIntentMutableLiveData() {
        return mIntentMutableLiveData;
    }

    public LiveData<Boolean> isAnnouncementFound() {
        return mIsAnnouncementFound;
    }

    public void setNewIntent(Intent intent){
        mIntentMutableLiveData.postValue(intent);
    }

    public void findAnnouncementByPetProfileID(String petProfileID) { mAnnouncementRepository.findAnnouncementByPetProfileID(petProfileID); }
}
