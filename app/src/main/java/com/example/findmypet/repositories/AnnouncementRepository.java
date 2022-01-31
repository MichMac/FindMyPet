package com.example.findmypet.repositories;

import android.net.Uri;
import android.util.Log;

import com.example.findmypet.models.Announcement;
import com.example.findmypet.utils.EventWrapper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AnnouncementRepository {

    private static final String TAG = "AnnouncementRepository";
    private static AnnouncementRepository instance;

    private FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = mFirebaseStorage.getReference();
    private StorageReference petPicRef;

    private ArrayList<Announcement> announcementsDataSet = new ArrayList<>();
    private MutableLiveData<Announcement> mAnnouncement = new MutableLiveData<>();
    private MutableLiveData<List<Announcement>> mAnnouncements = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUserLoggedInAnnouncement = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAnnouncementQueryDone = new MutableLiveData<>();
    private MutableLiveData<EventWrapper<Boolean>> isAdded= new MutableLiveData<com.example.findmypet.utils.EventWrapper<Boolean>>();
    private MutableLiveData<EventWrapper<Boolean>> isAnnouncementFound = new MutableLiveData<com.example.findmypet.utils.EventWrapper<Boolean>>();

    public static AnnouncementRepository getInstance(){
        if(instance == null){
            instance = new AnnouncementRepository();
        }
        return instance;
    }

    public void setAnnouncement(Announcement announcement) {
        mAnnouncement.setValue(announcement);
    }

    public LiveData<Announcement> getAnnouncement() {
        return mAnnouncement;
    }

    public MutableLiveData<EventWrapper<Boolean>> isAnnouncementFound() { return isAnnouncementFound; }

    public MutableLiveData<Boolean> isAnnouncementQueryDone() { return isAnnouncementQueryDone; }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    public MutableLiveData<Boolean> getIsUserLoggedInAnnouncement() {return isUserLoggedInAnnouncement;}

    public MutableLiveData<EventWrapper<Boolean>>  isAnnouncementAdded(){
        return isAdded;
    }

    public MutableLiveData<List<Announcement>> getAnnouncements() {
        loadAnnouncements();
        return mAnnouncements;
    }

    public void addLostPetAnnouncement(Announcement announcement){
        isLoading.setValue(true);
        isAdded.setValue(new EventWrapper<>(false));
        String userID = mFirebaseAuth.getUid();
        announcement.setUserID(userID);
        mFirestoreDB.collection("announcements")
                .add(announcement)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Announcement has been added");
                        isLoading.setValue(false);
                        isAdded.setValue(new EventWrapper<>(true));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Announcement hasn't been added: " + e.getMessage());
            }
        });
    }

    public void addFoundPetAnnouncement(Announcement announcement){
        isLoading.setValue(true);
        isAdded.setValue(new EventWrapper<>(false));
        String userID = mFirebaseAuth.getUid();
        announcement.setUserID(userID);
        mFirestoreDB.collection("announcements")
                .add(announcement)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "Announcement has been added");
                        isLoading.setValue(false);
                        isAdded.setValue(new EventWrapper<>(true));
                        String documentReferenceID = documentReference.getId();
                        addPetPicture(Uri.parse(announcement.getPetImageUrl()),documentReferenceID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Announcement hasn't been added: " + e.getMessage());
            }
        });
    }

    private void addPetPicture(Uri PetProfilePicURL, String AnnouncementID){
        isLoading.setValue(true);
        //isAdded.setValue(false);
        petPicRef = storageRef.child(mFirebaseAuth.getUid() + "/" + AnnouncementID +".jpg");
        petPicRef.putFile(PetProfilePicURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                petPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mFirestoreDB.collection("announcements").document(AnnouncementID)
                                .update("petImageUrl", uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i(TAG,"Pet picture url successfully added to firestore");
                                isLoading.setValue(false);
                                //isAdded.setValue(true);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.i(TAG,"Pet picture url hasn't been added: " + e.getMessage());
                            }
                        });
                    }
                });
            }
        });
    }
    private void loadAnnouncements(){
        isLoading.setValue(true);
//        announcementsDataSet.clear();
//        mAnnouncements.setValue(announcementsDataSet);
        mFirestoreDB.collection("announcements").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    announcementsDataSet.clear();

                    for (DocumentSnapshot documentSnapshot: list){

                        announcementsDataSet.add(documentSnapshot.toObject(Announcement.class));
                        mAnnouncements.setValue(announcementsDataSet);
                    }
                }
                isLoading.setValue(false);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Getting document snapshot error: ",e);
            }
        });
    }

    public void findAnnouncementByPetProfileID(String petProfileID){
        mFirestoreDB.collection("announcements")
                .whereEqualTo("petProfileID", petProfileID)
                .limit(1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots.getDocuments().isEmpty())
                            isAnnouncementFound.setValue(new EventWrapper<>(false));
                        else
                            isAnnouncementFound.setValue(new EventWrapper<>(true));


                        List<DocumentSnapshot> list =  queryDocumentSnapshots.getDocuments();

                        for (DocumentSnapshot documentSnapshot: list){
                            setAnnouncement(documentSnapshot.toObject(Announcement.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error getting documents: ", e);
                isAnnouncementFound.setValue(new EventWrapper<>(false));
            }
        });
//        isAnnouncementFound.setValue(false);
//        isLoading.setValue(false);
    }
}


