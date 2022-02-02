package com.example.findmypet.viewmodels;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.PetProfileRepository;
import com.example.findmypet.utils.EventWrapper;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddAnnouncementSharedViewModel extends ViewModel {

    private AnnouncementRepository mAnnouncementRepository;
    private PetProfileRepository mPetProfileRepository;
    private MutableLiveData<Boolean> mIsLoading;
    private MutableLiveData<EventWrapper<Boolean>> mIsAnnouncementAdded;
    private MutableLiveData<Announcement> mAnnouncement = new MutableLiveData<>();
    private Boolean isLocationValid;
    private GeoPoint mLatLng;

    private LiveData<List<PetProfile>> mPetProfiles;

    public void init(){
        if (mAnnouncementRepository != null && mPetProfileRepository !=null ) {
            return;
        }
        mAnnouncementRepository=AnnouncementRepository.getInstance();
        mPetProfileRepository= PetProfileRepository.getInstance();
        mPetProfiles = mPetProfileRepository.getPetProfiles();
        mIsLoading = mAnnouncementRepository.getIsLoading();
        mIsAnnouncementAdded = mAnnouncementRepository.isAnnouncementAdded();
    }

    public LiveData<List<PetProfile>> getPetProfiles(){
        return mPetProfiles;
    }

    public void setAnnouncementInfo(Announcement announcement) {
        mAnnouncement.setValue(announcement);
    }

    public void addLostAnnouncementToFirestore(Announcement announcement){
        mAnnouncementRepository.addLostPetAnnouncement(announcement);
    }

    public void addFoundAnnouncementToFirestore(Announcement announcement) {
        mAnnouncementRepository.addFoundPetAnnouncement(announcement);
    }

    public LiveData<Announcement> getAnnouncementInfo() {
       return  mAnnouncement;
    }

    public LiveData<Boolean> getIsLoading(){
        return mIsLoading;
    }

    public MutableLiveData<EventWrapper<Boolean>> isAnnouncementAdded() {
        return mIsAnnouncementAdded;
    }

    public GeoPoint getGeopoint(String location, Context context, Locale locale) throws IOException, IndexOutOfBoundsException {
        Geocoder geocoder = new Geocoder(context,locale);
        List<Address> result;

        result = geocoder.getFromLocationName(location,1);
        mLatLng= new GeoPoint(result.get(0).getLatitude(), result.get(0).getLongitude());

        return mLatLng;
    }
}
