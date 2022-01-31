package com.example.findmypet.viewmodels;

import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.utils.EventWrapper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NFCReadFragmentViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;
    public MutableLiveData<EventWrapper<Boolean>> mIsAnnouncementFound;

    public void init() {

        if(mAnnouncementRepository != null )
            return;
        else {
            mAnnouncementRepository = AnnouncementRepository.getInstance();
            mIsAnnouncementFound = mAnnouncementRepository.isAnnouncementFound();
        }
    }

    public LiveData<EventWrapper<Boolean>> isAnnouncementFound() {
        return mIsAnnouncementFound;
    }

    public void findAnnouncementByPetProfileID(String petProfileID) { mAnnouncementRepository.findAnnouncementByPetProfileID(petProfileID); }
}
