package com.example.findmypet.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.findmypet.R;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileListViewModel;

import static android.app.Activity.RESULT_OK;


public class AddPetProfileFragment extends Fragment {

    private static final String TAG = "AddPetProfileFragment";
    //private static int picCounter;

    private PetProfileListViewModel mPetProfileListViewModel;
    private PetProfile mPetProfile;
    private ProgressBar mProgressBar;
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
        View view = inflater.inflate(R.layout.add_pet_profile_fragment, container, false);

        mPetProfileListViewModel = new ViewModelProvider(this).get(PetProfileListViewModel.class);
        mPetProfile = new PetProfile();
        spGender = view.findViewById(R.id.gender_spinner_add_ann_found_pet);
        ivPhoto = view.findViewById(R.id.add_ann_found_pet_imageview);
        btnAddPhoto = view.findViewById(R.id.add_ann_found_pet_photo_button);
        etPetName = view.findViewById(R.id.petname_editname_add_ann_found_pet);
        etPetAge = view.findViewById(R.id.phone_number_edittext_add_ann);
        etMnNumber = view.findViewById(R.id.street_edittext_add_ann_pet);
        etDescription = view.findViewById(R.id.description_edittext_add_ann_found_pet);
        etSpecie = view.findViewById(R.id.specie_edittext_add_ann_found_pet);
        etBreed = view.findViewById(R.id.city_edittext_add_ann_pet);
        mProgressBar = view.findViewById(R.id.progress_bar_adding_petprofile);

        btnConfirm = view.findViewById(R.id.confirm_button_addpetprofile);
        btnNfc = view.findViewById(R.id.nfc_button_addpetprofile);
        
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        mPetProfileListViewModel.init();

        //deleting left arrrow from action bar
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mPetProfileListViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    //mPetProfile.setImage_url(selectedImageUri.toString());
                    mPetProfile.setName(etPetName.getText().toString());
                    mPetProfile.setGender(spGender.getSelectedItem().toString());
                    mPetProfile.setSpecies(etSpecie.getText().toString());
                    mPetProfile.setBreed(etBreed.getText().toString());
                    mPetProfile.setAge(Integer.parseInt(etPetAge.getText().toString()));
                    if(etMnNumber.length() != 0){
                        mPetProfile.setMicrochipNumber(Integer.parseInt(etMnNumber.getText().toString()));
                    }
                    mPetProfile.setDescription(etDescription.getText().toString());

                    mPetProfileListViewModel.addPetProfile(mPetProfile);
                    mPetProfileListViewModel.addPetProfilePicture(selectedImageUri,etPetName.getText().toString());
                }
                Log.i(TAG,"URL: " + selectedImageUri);
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
                return false;
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