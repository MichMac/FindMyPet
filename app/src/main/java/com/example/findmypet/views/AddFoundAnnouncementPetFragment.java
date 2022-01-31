package com.example.findmypet.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

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
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AddAnnouncementSharedViewModel;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;


public class AddFoundAnnouncementPetFragment extends Fragment {

    private Button btnNext;
    private Button btnAddPhoto;
    private Spinner spGender;
    private ImageView ivPhoto;
    private EditText etMnNumber;
    private EditText etDescription;
    private EditText etSpecie;
    private EditText etBreed;

    private AddAnnouncementSharedViewModel sharedViewModel;

    private Uri selectedImageUri;
    int SELECT_PICTURE = 200;

    ArrayAdapter<CharSequence> genderAdapter;

    public AddFoundAnnouncementPetFragment() {
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
        View root = inflater.inflate(R.layout.add_found_announcement_pet_fragment, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(AddAnnouncementSharedViewModel.class);
        //sharedViewModel.init();

        btnNext = root.findViewById(R.id.next_button_add_ann_found_pet);
        btnAddPhoto = root.findViewById(R.id.add_ann_found_pet_photo_button);

        spGender= root.findViewById(R.id.gender_spinner_add_ann_found_pet);
        genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        ivPhoto = root.findViewById(R.id.add_ann_found_pet_imageview);
        etMnNumber = root.findViewById(R.id.street_edittext_add_ann_pet);
        etDescription =root.findViewById(R.id.description_edittext_add_ann_found_pet);
        etSpecie = root.findViewById(R.id.specie_edittext_add_ann_found_pet);
        etBreed = root.findViewById(R.id.city_edittext_add_ann_pet);


        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataValidation()){
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isFoundAnn", true);
                    Announcement announcement = sharedViewModel.getAnnouncementInfo().getValue();
                    announcement.setStatus("Zaginiony");
                    announcement.setPetImageUrl(selectedImageUri.toString());
                    announcement.setPetGender(spGender.getSelectedItem().toString());
                    announcement.setPetSpecie(etSpecie.getText().toString());
                    announcement.setPetBreed(etBreed.getText().toString());
                    if(!etMnNumber.getText().toString().equals("")){
                        announcement.setPetMicrochipNumber(Long.parseLong(etMnNumber.getText().toString()));
                    }
                    else{
                        announcement.setPetMicrochipNumber(0);
                    }
                    announcement.setPetDescription(etDescription.getText().toString());
                    sharedViewModel.setAnnouncementInfo(announcement);
                    Navigation.findNavController(getView()).navigate(R.id.action_addFoundAnnouncementPet_to_addAnnouncementContact,bundle);
                }
            }
        });

        return root;
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
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