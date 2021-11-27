package com.example.findmypet.viewmodels;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.PetProfileRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddAnnouncementSharedViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;

    public void init(){
        if (mAnnouncementRepository != null){
            return;
        }
        mAnnouncementRepository=AnnouncementRepository.getInstance();
    }

    public void setAnnouncementInfo(Announcement announcement) {
        mAnnouncementRepository.setAnnouncement(announcement);
    }

    public void addAnnouncementToFirestore(Announcement announcement) {
        mAnnouncementRepository.addFoundPetAnnouncement(announcement);
    }

    public LiveData<Announcement> getAnnouncementInfo() {
       return  mAnnouncementRepository.getAnnouncement();
    }

    public LiveData<Boolean> getIsLoading(){
        return mAnnouncementRepository.getIsLoading();
    }

    public LiveData<Boolean> isAnnouncementAdded() {
        return mAnnouncementRepository.isAnnouncementAdded();
    }
}
