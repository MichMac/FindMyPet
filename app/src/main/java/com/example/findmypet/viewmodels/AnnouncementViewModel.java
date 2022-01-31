package com.example.findmypet.viewmodels;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.repositories.AnnouncementRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnouncementViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;
    private LiveData<Announcement> mAnnouncement;

    public void init(){
        mAnnouncementRepository = AnnouncementRepository.getInstance();
        if(mAnnouncement != null)
            return;

        mAnnouncement = mAnnouncementRepository.getAnnouncement();
    }

    public LiveData<Announcement> getAnnouncement() {
        return mAnnouncement;
    }


}