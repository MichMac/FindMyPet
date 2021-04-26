package com.example.findmypet.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.findmypet.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private FirebaseUser mFirebaseUser;
    private MutableLiveData<User> mUserMutableLiveData;
    private MutableLiveData<Boolean> mLoggedOutLiveData;

    public AuthRepository(Application application) {
        this.application = application;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUserMutableLiveData = new MutableLiveData<>();
        mFirestore = FirebaseFirestore.getInstance();
        mUserRef = mFirestore.collection("users");
        mLoggedOutLiveData = new MutableLiveData<>();
        mUser = new User();
        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
        //mUserMutableLiveData = new MutableLiveData<>();

        if (mFirebaseAuth.getCurrentUser() != null) {
            //getUserFirestore(mFirebaseAuth.getCurrentUser());
            mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
            //mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
            mLoggedOutLiveData.postValue(false);
        }
    }

    public void login(String email, String password) {
        mFirebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), task -> {
                    if (task.isSuccessful()) {
//                        mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                        //mUser = getUserFirestore(mFirebaseAuth.getCurrentUser());
                        //Log.d(TAG,"getting user from repo " + mFirebaseUser.getEmail());
                        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());
                    } else {
                        Toast.makeText(application.getApplicationContext(), "Login Failure: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void register(String email, String password, String name, String phoneNumber) {
        mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ContextCompat.getMainExecutor(application), task -> {
                    if (task.isSuccessful()) {
                        //mUser = getUserFirestore(mFirebaseAuth.getCurrentUser());
                        UserProfileChangeRequest  mUserProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        //mFirebaseUser = mFirebaseAuth.getCurrentUser();
                        mFirebaseAuth.getCurrentUser().updateProfile(mUserProfileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    mFirebaseUserMutableLiveData.postValue(mFirebaseAuth.getCurrentUser());;
                                }
                            }
                        });
                        mUser.setuID(mFirebaseAuth.getCurrentUser().getUid());
                        mUser.setEmail(email);
                        mUser.setName(name);
                        mUser.setPhone_number(phoneNumber);
                        addUserToFirestore(mUser);
                        Toast.makeText(application.getApplicationContext(), "User has been created!", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseAuthException e = (FirebaseAuthException) task.getException();
                        Toast.makeText(application,"Registration failed" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void addUserToFirestore(User mUser){
        mUserRef.document(mUser.getuID()).set(mUser).addOnSuccessListener(aVoid -> {
                Log.d("FirestoreAddingUser","DocumentSnapshot with user has been added");
                modifyUser(mUser.getName(),mUser.getPhone_number());
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("FirestoreAddingUser","error adding document",e);
                }
            });
    }

    private void modifyUser(String name, String number){
        mUser.setName(name);
        mUser.setPhone_number(number);
        mUserRef.document(mUser.getuID())
                .update("name",name,
                        "phone_number",number).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"DocumentSnapshot with user name and phone number successfully updated!");
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
        mLoggedOutLiveData.postValue(true);
    }

/*    private User getUser(FirebaseUser firebaseUser){
        String uid = firebaseUser.getUid();
        String name = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        return new User(uid,name,email);
    }*/

//    private User getUserFirestore(FirebaseUser firebaseUser){
//        mUserRef.document(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot document = task.getResult();
//                    if(document.exists()){
//                        Log.d(TAG,"DocumentSnapshot Data: " + document.getData());
//                        mUser.setuID(document.getString("uID"));
//                        mUser.setName(document.getString("name"));
//                        mUser.setEmail(document.getString("email"));
//                        mUser.setPhone_number(document.getString("phone_number"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                }
//                else{
//                    Log.d(TAG, "get failed with", task.getException());
//                }
//            }
//        });
//        return mUser;
//    }

//    public MutableLiveData<User> getUserMutableLiveData() { return mUserMutableLiveData; }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return mFirebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return mLoggedOutLiveData;
    }
}
