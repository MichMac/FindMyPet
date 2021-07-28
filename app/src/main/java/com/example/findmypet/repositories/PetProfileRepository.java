package com.example.findmypet.repositories;

import android.app.Application;
import android.app.ProgressDialog;
import android.net.Uri;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.findmypet.models.PetProfile;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

public class PetProfileRepository {

    private static final String TAG = "PetProfileRepository";
    private static PetProfileRepository instance;

    private FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = mFirebaseStorage.getReference();
    private StorageReference petPicRef;

    private ArrayList<PetProfile> PetProfilesDataSet = new ArrayList<>();
    MutableLiveData<List<PetProfile>> petProfiles = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public static PetProfileRepository getInstance(){

        if(instance == null){
            instance = new PetProfileRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PetProfile>> getPetProfiles(){
        loadPetProfiles();
        //petProfiles.setValue(PetProfilesDataSet);
        return petProfiles;
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    //na ten moment nie potrzebne, ponieważ livedata obsługuje dodawanie nowych profili
    private void notifyUpdatePetProfile() {

        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    System.err.println("Listen failed:" + error);
                    return;
                }

                PetProfilesDataSet.clear();

                for (DocumentSnapshot doc : snapshot){
                    Log.d(TAG, "Current data: " + doc.getData());
                    PetProfilesDataSet.add(doc.toObject(PetProfile.class));
                    petProfiles.setValue(PetProfilesDataSet);
                }

//                for (DocumentChange doc : snapshot.getDocumentChanges()){
//                    PetProfilesDataSet.add(doc.getDocument().toObject(PetProfile.class));
//                    petProfiles.setValue(PetProfilesDataSet);
//                }
            }
        });
    }

    private void loadPetProfiles(){
        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    PetProfilesDataSet.clear();

                    for (DocumentSnapshot documentSnapshot: list){

                        PetProfilesDataSet.add(documentSnapshot.toObject(PetProfile.class));
                        petProfiles.setValue(PetProfilesDataSet);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Getting document snapshot error: ",e);
            }
        });
//        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        })
    }

    public void addPetProfilePicture(Uri PetProfilePicURL, String PetName){
        isLoading.setValue(true);
        petPicRef = storageRef.child(mFirebaseAuth.getUid() + "/" + PetName +".jpg");
        petPicRef.putFile(PetProfilePicURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                petPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        mFirestoreDB.collection("users").document(mFirebaseAuth.getUid()).collection("PetProfiles").document(PetName)
                                .update("image_url", uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i(TAG,"Pet picture url successfully added to firestore");
                                isLoading.setValue(false);
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

//    public Uri getPetProfilePicture(String PetName){
//        petPicRef = storageRef.child(mFirebaseAuth.getUid() + "/" + PetName +".jpg");
//        petPicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Uri petPic = uri;
//            }
//        });
//    }

    public void addPetProfile(PetProfile petProfile){
        mFirestoreDB.collection("users").document(mFirebaseAuth.getUid()).collection("PetProfiles").document(petProfile.getName())
                .set(petProfile)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //notifyUpdatePetProfile();
                        //PetProfilesDataSet.add(petProfile);
                        //petProfiles.setValue(PetProfilesDataSet);
                        Log.d(TAG , "Pet profile has been added");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG , "Pet profile hasn't been added: " + e.getMessage());
            }
        });
    }
}
