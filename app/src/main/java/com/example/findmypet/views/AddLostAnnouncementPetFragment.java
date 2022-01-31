package com.example.findmypet.views;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.AddAnnouncementSharedViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddLostAnnouncementPetFragment extends Fragment {

    private static final String TAG = "AddLostAnnouncementPet";

    private Button btnNext;
    private Spinner spChoosePetProfile;
    ArrayAdapter<PetProfile> choosePetProfileAdapter;
    ArrayAdapter<CharSequence> genderAdapter;
    private AddAnnouncementSharedViewModel sharedViewModel;
    //private Button btnAddPhoto;
    private Spinner spGender;
    private ImageView ivPetPhoto;
    private EditText etMnNumber;
    private EditText etDescription;
    private EditText etSpecie;
    private EditText etBreed;

    private String selectedImageUri;
    private String petProfileID;

    public AddLostAnnouncementPetFragment() {
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
       View root =  inflater.inflate(R.layout.add_lost_announcement_pet_fragment, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(AddAnnouncementSharedViewModel.class);
        sharedViewModel.init();

        btnNext = root.findViewById(R.id.next_button_add_ann_lost_pet);

        etSpecie = root.findViewById(R.id.specie_edittext_add_ann_lost_pet);
        etBreed = root.findViewById(R.id.breed_edittext_add_ann_lost_pet);
        etMnNumber = root.findViewById(R.id.microchipnumber_edittext_add_ann_lost_pet);
        etDescription = root.findViewById(R.id.description_edittext_add_ann_lost_pet);

        ivPetPhoto = root.findViewById(R.id.add_ann_lost_pet_imageview);

        spGender = root.findViewById(R.id.gender_spinner_add_ann_lost_pet);
        genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        spChoosePetProfile = root.findViewById(R.id.choose_pet_profile_spinner_add_ann_lost_pet);
        List<PetProfile> petProfiles = new ArrayList<>();
        choosePetProfileAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, petProfiles);
        choosePetProfileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChoosePetProfile.setAdapter(choosePetProfileAdapter);


        sharedViewModel.getPetProfiles().observe(getViewLifecycleOwner(), new Observer<List<PetProfile>>() {
            @Override
            public void onChanged(List<PetProfile> PetProfiles) {
                petProfiles.clear();
                if(PetProfiles.size() != 0){
                    petProfiles.addAll(PetProfiles);
                    choosePetProfileAdapter.notifyDataSetChanged();
                }
                else{
                    Log.d(TAG,"Live data of petprofiles is null!");
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFoundAnn", false);
                    Announcement announcement = sharedViewModel.getAnnouncementInfo().getValue();
                    announcement.setStatus("Zaginiony");
                    announcement.setPetImageUrl(selectedImageUri);
                    announcement.setPetGender(spGender.getSelectedItem().toString());
                    announcement.setPetSpecie(etSpecie.getText().toString());
                    announcement.setPetBreed(etBreed.getText().toString());
                    if(etMnNumber != null)
                        announcement.setPetMicrochipNumber( Long.parseLong(etMnNumber.getText().toString()));
                    announcement.setPetDescription(etDescription.getText().toString());
                    announcement.setPetProfileID(petProfileID);
                    sharedViewModel.setAnnouncementInfo(announcement);
                    Navigation.findNavController(getView()).navigate(R.id.action_addLostAnnouncementPet_to_addAnnouncementContact,bundle);
                }
            }
        });

        spChoosePetProfile.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                PetProfile petProfile = (PetProfile) adapterView.getSelectedItem();
                selectedImageUri = ((PetProfile) adapterView.getSelectedItem()).getPetImageUrl();
                petProfileID = ((PetProfile) adapterView.getSelectedItem()).getPetProfileID();
                displayPetProfileInfo(petProfile);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

       return root;
    }


    private void displayPetProfileInfo(PetProfile petProfile) {

        Glide.with(getContext())
                .load(petProfile.getPetImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(ivPetPhoto);
        etSpecie.setText(petProfile.getSpecies());
        etBreed.setText(petProfile.getBreed());
        spGender.setSelection(genderAdapter.getPosition(petProfile.getGender()));
        if(etMnNumber != null)
            etMnNumber.setText(String.valueOf(petProfile.getMicrochipNumber()));
        etDescription.setText(petProfile.getDescription());
    }

    private boolean dataValidation(){

        if(selectedImageUri == null){
            Toast.makeText(getContext(),"Please select picture of the pet",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(etMnNumber.length() > 2){
            if(etMnNumber.length() != 15) {
                etMnNumber.setError("Microchip number must have 15 digits");
                return false;
            }
        }

        if (etSpecie.length() == 0) {
            etSpecie.setError("This field is required");
            return false;
        }
        if (etBreed.length() == 0) {
            etBreed.setError("This field is required");
            return false;
        }
        if (etDescription.length() == 0) {
            etDescription.setError("This field is required");
            return false;
        }
        return true;
    }
}