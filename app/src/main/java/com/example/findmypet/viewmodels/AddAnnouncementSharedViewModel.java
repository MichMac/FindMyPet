package com.example.findmypet.viewmodels;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.PetProfileRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddAnnouncementSharedViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;
    private PetProfileRepository mPetProfileRepository;

    private LiveData<List<PetProfile>> mPetProfiles;

    public void init(){
        if (mAnnouncementRepository != null && mPetProfileRepository !=null ) {
            return;
        }
        mAnnouncementRepository=AnnouncementRepository.getInstance();
        mPetProfileRepository= PetProfileRepository.getInstance();
    }

    public LiveData<List<PetProfile>> getPetProfiles(){
        mPetProfiles = mPetProfileRepository.getPetProfiles();
        return mPetProfiles;
    }

    public void setAnnouncementInfo(Announcement announcement) {
        mAnnouncementRepository.setAnnouncement(announcement);
    }

    public void addLostAnnouncementToFirestore(Announcement announcement){
        mAnnouncementRepository.addLostPetAnnouncement(announcement);
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
