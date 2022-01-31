package com.example.findmypet.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.utils.EventWrapper;
import com.example.findmypet.viewmodels.AddAnnouncementSharedViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AddAnnouncementContactFragment extends Fragment {

    private static final String TAG = "AddAnnouncementContact";
    
    private Button btnDone;
    private EditText etPhoneNumber;
    private ProgressBar mProgressBar;

    private AddAnnouncementSharedViewModel sharedViewModel;

    public AddAnnouncementContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.add_announcement_contact_fragment, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AddAnnouncementSharedViewModel.class);
        sharedViewModel.init();

        btnDone = root.findViewById(R.id.next_button_location_ann);
        etPhoneNumber = root.findViewById(R.id.phone_number_edittext_add_ann);
        mProgressBar = root.findViewById(R.id.progress_bar_adding_announcement);


        sharedViewModel.isAnnouncementAdded().observe(getViewLifecycleOwner(), new Observer<EventWrapper<Boolean>>() {
                    @Override
                    public void onChanged(EventWrapper<Boolean> booleanEventWrapper) {
                        if (booleanEventWrapper != null){
                            final Boolean shouldGetNewValue = booleanEventWrapper.getContentIfNotHandled();
                            if (shouldGetNewValue != null && shouldGetNewValue) {
                                Navigation.findNavController(getView()).navigate(R.id.action_addFoundAnnouncementContact_to_nav_missing_found);
                            }
                        }
                    }
                });

                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (etPhoneNumber.length() < 15) {
                            showNumberError();
                        } else {
                            Boolean isFoundAnn = getArguments().getBoolean("isFoundAnn");
                            Announcement announcement = sharedViewModel.getAnnouncementInfo().getValue();
                            announcement.setPhoneNumber(etPhoneNumber.getText().toString());
                            if (isFoundAnn)
                                sharedViewModel.addFoundAnnouncementToFirestore(announcement);
                            else
                                sharedViewModel.addLostAnnouncementToFirestore(announcement);
                        }
                    }
                });

        sharedViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    mProgressBar.setVisibility(View.VISIBLE);
//                    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }else{
                    mProgressBar.setVisibility(View.INVISIBLE);
//                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            }
        });
        return root;
    }

    private void showNumberError() {
        etPhoneNumber.setError("Your phone number is incorrect");
    }
}