package com.example.findmypet.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.findmypet.R;
import com.example.findmypet.models.User;
import com.example.findmypet.viewmodels.AuthActivityViewModel;
import com.google.firebase.auth.FirebaseUser;

public class AuthFragment extends Fragment {

    private static final String TAG = "AuthFragment";

    private AuthActivityViewModel mAuthActivityViewModel;
    private Button mSignUpButton;
    private Button mSignInButton;
    private EditText mEmailTextView;
    private EditText mPasswordTextView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuthActivityViewModel = new ViewModelProvider(this).get(AuthActivityViewModel.class);
        mAuthActivityViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if(user != null){
                    Log.d(TAG,"Navigating to user log window..." );
                    Log.d(TAG,"User onChange : " + user.getEmail() + " " + user.getDisplayName() + user.getUid());
                    Navigation.findNavController(getView()).navigate(R.id.action_authFragment_to_mainActivity);
                }
                else {
                    Toast.makeText(getContext(),"User is null",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        //mAuthActivityViewModel = new ViewModelProvider(this).get(AuthActivityViewModel.class);

        mSignInButton = view.findViewById(R.id.signIn);
        mSignUpButton = view.findViewById(R.id.signUp);
        mEmailTextView = view.findViewById(R.id.fragment_newuser_email);
        mPasswordTextView = view.findViewById(R.id.fragment_newuser_password);

        mSignUpButton.setOnClickListener(view1 -> {
            Navigation.findNavController(getView()).navigate(R.id.action_authFragment_to_newUserFragment);
        });

        mSignInButton.setOnClickListener(view12 -> {
            String email = mEmailTextView.getText().toString();
            String password = mPasswordTextView.getText().toString();

            if(email.length() >= 0 && password.length() >= 0){
                mAuthActivityViewModel.login(email,password);
            }
        });
        return view;
    }
}