package com.example.findmypet.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.findmypet.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

public class AuthRepository {

    private static final String TAG = "AuthRepository";
    private static AuthRepository instance;

    private Application application;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirestore;
    private CollectionReference mUserRef;
    private MutableLiveData<FirebaseUser> mFirebaseUserMutableLiveData;
    private User mUser;
    private MutableLiveData<User> mUserMutableLiveData;
    private MutableLiveData<Boolean> mLoggedOutLiveData;

    public AuthRepository(Application application) {
        this.application = application;
        this.mFirebaseAuth = FirebaseAuth.getInstance();
        this.mFirebaseUserMutableLiveData = new MutableLiveData<>();
        this.mFirestore = FirebaseFirestore.getInstance();
        this.mUserRef = mFirestore.collection("users");
        this.mLoggedOutLiveData = new MutableLiveData<>();
        this.mUser = new User();
        this.mUserMutableLiveData = new MutableLiveData<>();

        if (mFirebaseAuth.getCurrentUser() != null) {
            mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
            mLoggedOutLiveData.postValue(false);
        }
    }

    public void login(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), task -> {
                    if (task.isSuccessful()) {
                        //mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                        mUser = getUser(mFirebaseAuth.getCurrentUser());
                        mUserMutableLiveData.postValue(mUser);
                    } else {
                        Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void register(String email, String password, String name, String phoneNumber) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), task -> {
                    if (task.isSuccessful()) {
                        //mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                        mUser = getUser(mFirebaseAuth.getCurrentUser());
                        //mUser.setNew(true);
                        mUserMutableLiveData.postValue(mUser);
                        mUserRef.document(mUser.getuID()).set(mUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("FirestoreAddingUser","DocumentSnapshot with user has been added");
                                modifyUser(name,phoneNumber);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("FirestoreAddingUser","error adding document",e);
                            }
                        });
                    }

                });
    }

    private void modifyUser(String name, String number){
        mUser.setName(name);
        mUser.setPhone_number(number);
        mUserMutableLiveData.postValue(mUser);
        mUserRef.document(mUser.getuID())
                .update("name",name,
                        "phone_number",number).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"DocumentSnapshot succesfully updated!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG,"Error updating document",e);
            }
        });
    }

    public void logOut() {
        mFirebaseAuth.signOut();
        //mLoggedOutLiveData.postValue(true);
    }

    private User getUser(FirebaseUser firebaseUser){
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        return new User(uid,name,email);
    }

//    public MutableLiveData<FirebaseUser> getFirebaseUserLiveData() {
//        return mFirebaseUserMutableLiveData;
//    }

    public MutableLiveData<User> getUserMutableLiveData() {return mUserMutableLiveData;}

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return mLoggedOutLiveData;
    }
}

//    @Override
//    public void onSuccess(DocumentReference documentReference) {
//        Log.d("FirestoreAddingUser","DocumentSnapshot with user has been added");
//        modifyUser(name,phoneNumber);
//    }
//}).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Log.w("FirestoreAddingUser","error adding document",e);