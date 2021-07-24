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
import androidx.lifecycle.MutableLiveData;

public class PetProfileRepository {

    private static final String TAG = "PetProfileRepository";
    private static PetProfileRepository instance;

    private ArrayList<PetProfile> mPetProfiles = new ArrayList<>();
    private MutableLiveData<List<PetProfile>> petProfiles;

    private FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = mFirebaseStorage.getReference();
    private StorageReference petPicRef;

    public static PetProfileRepository getInstance(){

        if(instance == null){
            instance = new PetProfileRepository();
        }
        return instance;
    }

    public MutableLiveData<List<PetProfile>> getPetProfiles(){
        loadPetProfiles();
        petProfiles.setValue(mPetProfiles);
        return petProfiles;
    };

    private void loadPetProfiles(){

        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "PetProfiles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot documentSnapshot: list){

                        mPetProfiles.add(documentSnapshot.toObject(PetProfile.class));
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Getting document snapshot error: ",e);
            }
        });
    }

    public void addPetProfilePicture(Uri PetProfilePicURL, String PetName){
        petPicRef = storageRef.child(mFirebaseAuth.getUid() + "/" + PetName +".jpg");
        petPicRef.putFile(PetProfilePicURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.i(TAG,"Pet picture successfully added");
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
        
        mFirestoreDB.collection("users").document(mFirebaseAuth.getUid()).collection("PetProfiles")
                .add(petProfile)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference){
                        Log.d(TAG , "Pet profile has been added");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"Document hasn't been added: ",e);
            }
        });
    }
}
