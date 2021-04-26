package com.example.findmypet.viewmodels;

import android.app.Application;

import com.example.findmypet.models.User;
import com.example.findmypet.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private AuthRepository mAuthAppRepository;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
    private MutableLiveData<Boolean> mLoggedOutLiveData;
    //private MutableLiveData<User> mUserMutableLiveData;

    public MainActivityViewModel (@NonNull Application application) {
        super(application);
        mAuthAppRepository= new AuthRepository(application);
        //mFirebaseUserMutableLiveData = mAuthAppRepository.getFirebaseUserLiveData();
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
}
