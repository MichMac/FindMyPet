package com.example.findmypet.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileViewModel;

import static android.app.Activity.RESULT_OK;


public class AddPetProfileFragment extends Fragment {

    private static final String TAG = "AddPetProfileFragment";

    private PetProfileViewModel mPetProfileViewModel;
    private PetProfile mPetProfile;
    private Spinner spGender;
    private ImageView ivPhoto;
    private Button btnAddPhoto;
    private Button btnNfc;
    private Button btnConfirm;
    private EditText etPetName;
    private EditText etPetAge;
    private EditText etMnNumber;
    private EditText etDescription;
    private EditText etSpecie;
    private EditText etBreed;

    private Uri selectedImageUri;

    int SELECT_PICTURE = 200;

//    public AddPetProfileFragment() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_pet_profile, container, false);

        mPetProfileViewModel = new ViewModelProvider(this).get(PetProfileViewModel.class);
        mPetProfile = new PetProfile();
        spGender = view.findViewById(R.id.gender_spinner_petprofile);
        ivPhoto = view.findViewById(R.id.imageview_petprofile);
        btnAddPhoto = view.findViewById(R.id.add_pet_photo_button);
        etPetName = view.findViewById(R.id.petname_editname_petprofile);
        etPetAge = view.findViewById(R.id.age_edittext_petprofile);
        etMnNumber = view.findViewById(R.id.microchipnumber_edittext_petprofile);
        etDescription = view.findViewById(R.id.description_edittext_petprofile);
        etSpecie = view.findViewById(R.id.specie_edittext_petprofile);
        etBreed = view.findViewById(R.id.breed_edittext_petprofile);

        btnConfirm = view.findViewById(R.id.confirm_button_petprofile);
        btnNfc = view.findViewById(R.id.nfc_button_petprofile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);

        mPetProfileViewModel.init();

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    //Log.i(TAG,"Nazwa psa: " + etPetName.getText().toString());
                    mPetProfile.setName(etPetName.getText().toString());
                    mPetProfile.setGender(spGender.getSelectedItem().toString());
                    mPetProfile.setSpecies(etSpecie.getText().toString());
                    mPetProfile.setBreed(etBreed.getText().toString());
                    mPetProfile.setAge(Integer.parseInt(etPetAge.getText().toString()));
                    if(etMnNumber.length() != 0){
                        mPetProfile.setMicrochip_number(Integer.parseInt(etMnNumber.getText().toString()));
                    }
                    mPetProfile.setDescription(etDescription.getText().toString());

                    mPetProfileViewModel.addPetProfile(mPetProfile);
                }
            }
        });

        //handling back button with alert
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setTitle("Exit");
                alertDialog.setMessage("Your data will be not saved. Are you sure you want to go back?");

                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Navigation.findNavController(getView()).navigate(R.id.action_addPetProfileFragment_to_nav_pet_profile);
                    }
                });
                alertDialog.setNegativeButton("No",null);
                alertDialog.show();
            }
        });

        return view;
    }

    private void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    private boolean dataValidation(){

            if(etPetName.length() == 0) {
                etPetName.setError("This field is required");
                return false;
            }
            if(etPetAge.length() == 0) {
                etPetAge.setError("This field is required");
                return false;
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
            }
            return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data.getData();

                if (null != selectedImageUri) {
                    //ivPhoto.setImageURI(selectedImageUri);
                    Glide.with(getContext())
                            .load(selectedImageUri)
                            .centerCrop()
                            .into(ivPhoto);
                }
            }
        }
    }
}