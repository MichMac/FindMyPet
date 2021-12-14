package com.example.findmypet.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;


public class AnnouncementFragment extends Fragment {

    private Announcement mAnnouncement;

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

        ivPetPic = root.findViewById(R.id.image_pet_item_announcement);
        btnShowOnMap = root.findViewById(R.id.show_on_map_announcement_info);
        tvCountryCity = root.findViewById(R.id.country_city_announcement_info_textview);
        tvProvince = root.findViewById(R.id.province_announcement_info_textview);
        tvStreet = root.findViewById(R.id.street_announcement_info_textview);
        tvDescription = root.findViewById(R.id.pet_description_announcement_info_textview);
        tvSpecies = root.findViewById(R.id.species_announcement_info_textview);
        tvGender = root.findViewById(R.id.gender_announcement_info_textview);
        tvBreed = root.findViewById(R.id.breed_announcement_info_textview);
        tvMicroshipNumber = root.findViewById(R.id.microchip_number_announcement_info_textview);
        tvPhoneNumber = root.findViewById(R.id.phone_number_announcement_info_textview);

        mAnnouncement = (Announcement) getArguments().getSerializable("announcement");
        setAnnouncementInfo(mAnnouncement);


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

            tvCountryCity.setText(announcement.getCountry() + "," + announcement.getCity());
            tvProvince.setText(announcement.getProvince());
            tvStreet.setText(announcement.getStreet());
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