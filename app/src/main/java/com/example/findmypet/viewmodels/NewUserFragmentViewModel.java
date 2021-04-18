package com.example.findmypet.viewmodels;

import android.app.Application;

import com.example.findmypet.models.User;
import com.example.findmypet.repositories.AuthRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class NewUserFragmentViewModel extends AndroidViewModel {

    private AuthRepository mAuthAppRepository;
    private MutableLiveData<User> mUserMutableLiveData;

    public NewUserFragmentViewModel(@NonNull Application application) {
        super(application);
        mAuthAppRepository= new AuthRepository(application);
        //mFirebaseUserMutableLiveData = mAuthAppRepository.getFirebaseUserLiveData();
        mUserMutableLiveData = mAuthAppRepository.getUserMutableLiveData();
    }

    public void register(String email, String password, String name, String phoneNumber) {
        mAuthAppRepository.register(email, password, name, phoneNumber);
    }

    public MutableLiveData<User> getUserMutableLiveData(){
        return mUserMutableLiveData;
    }
}
