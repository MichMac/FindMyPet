package com.example.findmypet.repositories;

import android.net.Uri;
import android.util.Log;

import com.example.findmypet.models.PetProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
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
    //private AuthRepository mAuthRepository;

    private FirebaseFirestore mFirestoreDB = FirebaseFirestore.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageRef = mFirebaseStorage.getReference();
    private StorageReference petPicRef;

    private ArrayList<PetProfile> PetProfilesDataSet = new ArrayList<>();
    private PetProfile petProfile = new PetProfile();
    MutableLiveData<PetProfile> mPetProfile = new MutableLiveData<>();
    MutableLiveData<List<PetProfile>> mPetProfiles = new MutableLiveData<>();
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
        return mPetProfiles;
    }

    public MutableLiveData<PetProfile> getPetProfile(){
        return mPetProfile;
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return isLoading;
    }

    //na ten moment nie potrzebne, ponieważ livedata obsługuje dodawanie nowych profili
//    private void notifyUpdatePetProfile() {
//        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null) {
//                    System.err.println("Listen failed:" + error);
//                    return;
//                }
//
//                PetProfilesDataSet.clear();
//
//                for (DocumentSnapshot doc : snapshot){
//                    Log.d(TAG, "Current data: " + doc.getData());
//                    PetProfilesDataSet.add(doc.toObject(PetProfile.class));
//                    mPetProfiles.setValue(PetProfilesDataSet);
//                }
//
//            }
//        });
//    }

    private void loadPetProfiles(){
        isLoading.setValue(true);
        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()){

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    PetProfilesDataSet.clear();

                    for (DocumentSnapshot documentSnapshot: list){

                        PetProfilesDataSet.add(documentSnapshot.toObject(PetProfile.class));
                        mPetProfiles.setValue(PetProfilesDataSet);
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
//        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        })
    }

    public void loadPetProfile(String petName){
        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles/" + petName).document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                petProfile = documentSnapshot.toObject(PetProfile.class);
                mPetProfile.setValue(petProfile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Getting document snapshot error: ",e);
            }
        });
    }

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
                                .update("petImageUrl", uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public void deletePetProfile(PetProfile petProfile){
        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles/")
                .document(petProfile.getName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        //mPetProfiles.setValue();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void updatePetProfile(PetProfile petProfile){
        mFirestoreDB.collection("users/" + mFirebaseAuth.getCurrentUser().getUid() + "/PetProfiles/")
                .document(petProfile.getName())
                .update("age",petProfile.getAge(),
                        "breed",petProfile.getBreed(),
                        "description",petProfile.getDescription(),
                        "gender",petProfile.getGender(),
                        "microchip_number",petProfile.getMicrochipNumber(),
                        "species",petProfile.getSpecies()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error updating document", e);
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
}
