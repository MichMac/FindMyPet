package com.example.findmypet.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AddAnnouncementSharedViewModel;

public class AddAnnouncementLocation extends Fragment {

    private Button btnNext;
    private EditText etDate;
    private EditText etCountry;
    private EditText etProvince;
    private EditText etCity;
    private EditText etStreet;
    private Boolean isFoundAnn;

    private AddAnnouncementSharedViewModel sharedViewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_announcement_location_fragment, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(AddAnnouncementSharedViewModel.class);
        sharedViewModel.init();

        btnNext = root.findViewById(R.id.done_button_ann);
        etDate = root.findViewById(R.id.phone_number_edittext_add_ann);
        etCountry =  root.findViewById(R.id.country_edittext_loc_ann);
        etProvince = root.findViewById(R.id.province_edittext_loc_ann);
        etCity = root.findViewById(R.id.city_edittext_add_ann_pet);
        etStreet = root.findViewById(R.id.street_edittext_add_ann_pet);

        //&& dataValidation()
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments() != null ){
                    isFoundAnn = getArguments().getBoolean("isFoundAnn");
                    Announcement announcement = new Announcement();
                    announcement.setDate(etDate.getText().toString());
                    announcement.setCountry(etCountry.getText().toString());
                    announcement.setProvince(etProvince.getText().toString());
                    announcement.setCity(etCity.getText().toString());
                    announcement.setStreet(etStreet.getText().toString());
                    sharedViewModel.setAnnouncementInfo(announcement);
                    if(isFoundAnn)
                        Navigation.findNavController(getView()).navigate(R.id.action_addAnnouncementLocation_to_addFoundAnnouncementPet);
                    else
                        Navigation.findNavController(getView()).navigate(R.id.action_addFoundAnnouncementLocation_to_addLostAnnouncementPet);
                }
            }
        });

        return root;
    }

    private boolean dataValidation(){

        if (etDate.length() == 0) {
            etDate.setError("This field is required");
            return false;
        }
        if (etCountry.length() == 0) {
            etCountry.setError("This field is required");
            return false;
        }
        if (etProvince.length() == 0) {
            etProvince.setError("This field is required");
            return false;
        }
        if (etCity.length() == 0) {
            etProvince.setError("This field is required");
            return false;
        }
        if (etStreet.length() == 0) {
            etProvince.setError("This field is required");
            return false;
        }
        return true;
    }

}