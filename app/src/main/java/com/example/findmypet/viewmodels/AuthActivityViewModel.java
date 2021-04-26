package com.example.findmypet.viewmodels;

import android.app.Application;

import com.example.findmypet.models.User;
import com.example.findmypet.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AuthActivityViewModel extends AndroidViewModel {

    private AuthRepository mAuthAppRepository;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
   // private MutableLiveData<User> mUserMutableLiveData;

    public AuthActivityViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository= new AuthRepository(application);
        //mFirebaseUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
        mFirebaseUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
    }

    public void login(String email, String password) {
        mAuthAppRepository.login(email, password);
    }

//    public MutableLiveData<FirebaseUser> getFirebaseUserLiveData() {
//        return mFirebaseUserMutableLiveData;
//    }
//    public MutableLiveData<User> getUserMutableLiveData(){
//        return mUserMutableLiveData;
//    }
    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }
}
