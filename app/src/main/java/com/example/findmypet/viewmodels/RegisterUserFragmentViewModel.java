package com.example.findmypet.viewmodels;

import android.app.Application;

import com.example.findmypet.models.User;
import com.example.findmypet.repositories.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class RegisterUserFragmentViewModel extends AndroidViewModel {

    private AuthRepository mAuthAppRepository;
    //private MutableLiveData<User> mUserMutableLiveData;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;

    public RegisterUserFragmentViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository= new AuthRepository(application);
        //mFirebaseUserMutableLiveData = mAuthAppRepository.getFirebaseUserLiveData();
        mFirebaseUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
    }

    public void register(String email, String password, String name, String phoneNumber) {
        mAuthAppRepository.register(email, password, name, phoneNumber);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }
}
