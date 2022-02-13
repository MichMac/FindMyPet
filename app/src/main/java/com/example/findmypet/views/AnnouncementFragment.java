package com.example.findmypet.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AnnouncementViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class AnnouncementFragment extends Fragment {

    private AnnouncementViewModel mAnnouncementViewModel;

    private ImageView ivPetPic;
    private MaterialButton btnShowOnMap;
    private MaterialTextView tvCountryCity;
    private MaterialTextView tvProvince;
    private MaterialTextView tvStreet;
    private MaterialTextView tvDescription;
    private MaterialTextView tvSpecies;
    private MaterialTextView tvGender;
    private MaterialTextView tvBreed;
    private MaterialTextView tvMicroshipNumber;
    private MaterialTextView tvPhoneNumber;

    public AnnouncementFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.announcement_fragment, container, false);

        mAnnouncementViewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class);
        mAnnouncementViewModel.init();

        ivPetPic = root.findViewById(R.id.image_pet_item_announcement);
        btnShowOnMap = root.findViewById(R.id.show_on_map_announcement_info);
        tvCountryCity = root.findViewById(R.id.location_announcement_info_textview);
//        tvProvince = root.findViewById(R.id.province_announcement_info_textview);
//        tvStreet = root.findViewById(R.id.street_announcement_info_textview);
        tvDescription = root.findViewById(R.id.pet_description_announcement_info_textview);
        tvSpecies = root.findViewById(R.id.species_announcement_info_textview);
        tvGender = root.findViewById(R.id.gender_announcement_info_textview);
        tvBreed = root.findViewById(R.id.breed_announcement_info_textview);
        tvMicroshipNumber = root.findViewById(R.id.microchip_number_announcement_info_textview);
        tvPhoneNumber = root.findViewById(R.id.phone_number_announcement_info_textview);

        mAnnouncementViewModel.getAnnouncement().observe(getViewLifecycleOwner(), new Observer<Announcement>() {
            @Override
            public void onChanged(Announcement announcement) {
                setAnnouncementInfo(announcement);
            }
        });

        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_announcementFragment_to_mapFragment);
            }
        });

        return root;
    }

    public void setAnnouncementInfo(Announcement announcement){
        if(announcement != null){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(announcement.getPetImageUrl())
                    .into(ivPetPic);

            //tvCountryCity.setText(announcement.getCountry() + "," + announcement.getCity());
            tvCountryCity.setText(announcement.getLocation());
//            tvProvince.setText(announcement.getProvince());
//            tvStreet.setText(announcement.getStreet());
            tvDescription.setText(announcement.getPetDescription());
            tvSpecies.setText(announcement.getPetSpecie());
            tvGender.setText(announcement.getPetGender());
            tvBreed.setText(announcement.getPetBreed());
            tvMicroshipNumber.setText(Long.toString(announcement.getPetMicrochipNumber()));
            tvDescription.setText(announcement.getPetDescription());
            tvPhoneNumber.setText(announcement.getPhoneNumber());
        }
    }
}