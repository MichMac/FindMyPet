package com.example.findmypet.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.findmypet.R;
import com.example.findmypet.models.User;
import com.example.findmypet.viewmodels.NewUserFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class NewUserFragment extends Fragment {

    private static final String TAG = "NewUserFragment";

    private NewUserFragmentViewModel mNewUserFragmentViewModel;
    private Button mRegister;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mName;
    private EditText mPhoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNewUserFragmentViewModel = new ViewModelProvider(this).get(NewUserFragmentViewModel.class);
        mNewUserFragmentViewModel.getUserMutableLiveData().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d(TAG, "onChanged: " + user.getEmail() + " " + user.getName());
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newuser,container,false);

        mRegister = view.findViewById(R.id.fragment_newuser_register_button);
        mEmail = view.findViewById(R.id.fragment_newuser_email);
        mPassword = view.findViewById(R.id.fragment_newuser_password);
        mName = view.findViewById(R.id.fragment_newuser_name_edittext);
        mPhoneNumber = view.findViewById(R.id.fragment_newuser_phonenumber_edittext);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                String fullname = mName.getText().toString();
                String phone_number = mPhoneNumber.getText().toString();

                if(fullname.length() >= 0 && phone_number.length() >= 0 && email.length() >= 0 && password.length() >= 0){
                    mNewUserFragmentViewModel.register(email,password,fullname,phone_number);
                }
            }
        });
        return view;
    }
}
