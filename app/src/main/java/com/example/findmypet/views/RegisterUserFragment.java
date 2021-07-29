package com.example.findmypet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.findmypet.R;
import com.example.findmypet.viewmodels.RegisterUserFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

public class RegisterUserFragment extends Fragment {

    private static final String TAG = "NewUserFragment";

    private RegisterUserFragmentViewModel mRegisterUserFragmentViewModel;
    private Button mRegister;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mName;
    private EditText mPhoneNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRegisterUserFragmentViewModel = new ViewModelProvider(this).get(RegisterUserFragmentViewModel.class);
        mRegisterUserFragmentViewModel.getUserMutableLiveData().observe(this, user ->
                Navigation.findNavController(getView()).navigate(R.id.action_newUserFragment_to_mainActivity));
//        MutableLiveData<User> user = mRegisterUserFragmentViewModel.getUserMutableLiveData();
//        Log.d(TAG, "New user fragment value: " + user.getValue());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newuser_fragment,container,false);

        mRegister = view.findViewById(R.id.fragment_newuser_register_button);
        mEmail = view.findViewById(R.id.fragment_newuser_email);
        mPassword = view.findViewById(R.id.fragment_newuser_password);
        mName = view.findViewById(R.id.fragment_newuser_name_edittext);
        mPhoneNumber = view.findViewById(R.id.fragment_newuser_phonenumber_edittext);

        mRegister.setOnClickListener(view1 -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            String fullname = mName.getText().toString();
            String phone_number = mPhoneNumber.getText().toString();

            if(fullname.length() >= 0 && phone_number.length() >= 0 && email.length() >= 0 && password.length() >= 6){
                mRegisterUserFragmentViewModel.register(email,password,fullname,phone_number);
                //Navigation.findNavController(getView()).navigate(R.id.action_newUserFragment_to_mainActivity);
            }
        });
        return view;
    }
}
