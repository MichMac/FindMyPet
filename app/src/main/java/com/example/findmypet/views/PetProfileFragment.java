package com.example.findmypet.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.findmypet.R;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileViewModel;

public class PetProfileFragment extends Fragment {

    private PetProfileViewModel mPetProfileViewModel;
    private PetProfile mPetProfile;

    private ImageView ivPetPic;
    private TextView tvPetName;
    private TextView etPetAge;
    private Spinner spGender;
    private TextView etMnNumber;
    private TextView etDescription;
    private TextView etSpecies;
    private TextView etBreed;

    ArrayAdapter<CharSequence> genderAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.pet_profile_fragment, container, false);
        mPetProfileViewModel = new ViewModelProvider(this).get(PetProfileViewModel.class);
        mPetProfileViewModel.init();
        setHasOptionsMenu(true);

        ivPetPic = root.findViewById(R.id.image_petprofile);
        tvPetName = root.findViewById(R.id.name_textview_petprofile);
        etPetAge = root.findViewById(R.id.age_edittext_petprofile);
        spGender = root.findViewById(R.id.gender_edittext_petprofile);
        genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);
        etMnNumber = root.findViewById(R.id.microchipnumber_edittext_petprofile);
        etDescription = root.findViewById(R.id.description_edittext_petprofile);
        etSpecies = root.findViewById(R.id.species_edittext_petprofile);
        etBreed = root.findViewById(R.id.breed_edittext_petprofile);

        mPetProfile = (PetProfile) getArguments().getSerializable("petprofile");
        setPetProfileData(mPetProfile);



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
            etPetAge.setText(Integer.toString(petProfile.getAge()));
            spGender.setSelection(genderAdapter.getPosition(petProfile.getGender()));
            etMnNumber.setText(Integer.toString(petProfile.getMicrochip_number()));
            etSpecies.setText(petProfile.getSpecies());
            etBreed.setText(petProfile.getBreed());
            etDescription.setText(petProfile.getDescription());
        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item=menu.findItem(R.id.save_action);
        if(item!=null)
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mPetProfile.setName(tvPetName.getText().toString());
                mPetProfile.setAge(Integer.parseInt(etPetAge.getText().toString()));
                mPetProfile.setGender(spGender.getSelectedItem().toString());
                mPetProfile.setMicrochip_number(Integer.parseInt(etMnNumber.getText().toString()));
                mPetProfile.setSpecies(etSpecies.getText().toString());
                mPetProfile.setBreed(etBreed.getText().toString());
                mPetProfile.setDescription(etDescription.getText().toString());
                mPetProfileViewModel.updatePetProfile(mPetProfile);
                Toast.makeText(getContext(),"Profile successfully updated!",Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

}