package com.example.findmypet.views;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileViewModel;

public class PetProfileFragment extends Fragment {

    private PetProfileViewModel mPetProfileViewModel;

    private ImageView ivPetPic;
    private TextView tvPetName;
    private TextView tvPetAge;
    private TextView tvGender;
    private TextView tvMnNumber;
    private TextView tvDescription;
    private TextView tvSpecies;
    private TextView tvBreed;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pet_profile_fragment, container, false);

        ivPetPic = root.findViewById(R.id.image_petprofile);
        tvPetName = root.findViewById(R.id.name_textview_petprofile);
        tvPetAge = root.findViewById(R.id.age_textview_petprofile);
        tvGender = root.findViewById(R.id.gender_textview_petprofile);
        tvMnNumber = root.findViewById(R.id.microchipnumber_textview_petprofile);
        tvDescription = root.findViewById(R.id.description_textview_petprofile);
        tvSpecies = root.findViewById(R.id.species_textview_petprofile);
        tvBreed = root.findViewById(R.id.breed_textview_petprofile);

        mPetProfileViewModel = new ViewModelProvider(this).get(PetProfileViewModel.class);

        PetProfile petProfile = (PetProfile) getArguments().getSerializable("petprofile");
        setPetProfileData(petProfile);

        // null object referenceeeee
//        mPetProfileViewModel.getPetProfile().observe(getViewLifecycleOwner(), new Observer<PetProfile>() {
//            @Override
//            public void onChanged(PetProfile petProfile) {
//                setPetProfileData(petProfile);
//            }
//        });

        return root;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mPetProfileViewModel = new ViewModelProvider(this).get(PetProfileViewModel.class);
//        // TODO: Use the ViewModel
//    }
    public void setPetProfileData(PetProfile petProfile){

        if(petProfile != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(petProfile.getImage_url())
                    .into(ivPetPic);

            tvPetName.setText(petProfile.getName());
            tvPetAge.setText(Integer.toString(petProfile.getAge()));
            tvGender.setText(petProfile.getGender());
            tvMnNumber.setText(Integer.toString(petProfile.getMicrochip_number()));
            tvSpecies.setText(petProfile.getSpecies());
            tvBreed.setText(petProfile.getBreed());
            tvDescription.setText(petProfile.getDescription());
        }

    }
}