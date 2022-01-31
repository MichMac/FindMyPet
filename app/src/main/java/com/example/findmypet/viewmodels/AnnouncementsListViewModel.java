package com.example.findmypet.viewmodels;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnouncementsListViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;
    private MutableLiveData<List<Announcement>> mAnnouncements;

    public void init(){
        if(mAnnouncements != null){
            return;
        }
        mAnnouncementRepository = AnnouncementRepository.getInstance();
        mAnnouncements = mAnnouncementRepository.getAnnouncements();
    }

    public LiveData<List<Announcement>> getAnnouncements(){
        return mAnnouncements;
    }

    public void setAnnouncement(Announcement announcement) {
        mAnnouncementRepository.setAnnouncement(announcement);
    }

}