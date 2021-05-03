package com.example.findmypet.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MissingFoundViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MissingFoundViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is missing found pets fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}