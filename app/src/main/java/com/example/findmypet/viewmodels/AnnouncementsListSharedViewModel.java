package com.example.findmypet.viewmodels;

import android.util.Log;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.AnnouncementRepository;
import com.example.findmypet.repositories.PetProfileRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AnnouncementsListSharedViewModel extends ViewModel {

    private static final String TAG = "AnnouncementsListShared";
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

    public void sortAscending(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Collections.sort(mAnnouncements.getValue(), new Comparator<Announcement>() {
            public int compare(Announcement o1, Announcement o2) {
                try {
                    return dateFormat.parse(o1.getDate()).compareTo(dateFormat.parse(o2.getDate()));
                } catch (ParseException e) {
                    Log.d(TAG,"Error parsing the date: " + e.getMessage());
                }
                return 0;
            }
        });
        mAnnouncements.postValue(mAnnouncements.getValue());
    }

    public void sortDescending(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Collections.sort(mAnnouncements.getValue(), new Comparator<Announcement>() {
            public int compare(Announcement o1, Announcement o2) {
                try {
                    return dateFormat.parse(o2.getDate()).compareTo(dateFormat.parse(o1.getDate()));
                } catch (ParseException e) {
                    Log.d(TAG,"Error parsing the date: " + e.getMessage());
                }
                return 0;
            }
        });
        mAnnouncements.postValue(mAnnouncements.getValue());
    }



}