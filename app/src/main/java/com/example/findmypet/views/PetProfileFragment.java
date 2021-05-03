package com.example.findmypet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findmypet.R;
import com.example.findmypet.adapters.PetProfileListAdapter;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PetProfileFragment extends Fragment {

    private PetProfileViewModel mPetProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPetProfileViewModel = new ViewModelProvider(this).get(PetProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pet_profile, container, false);

        FloatingActionButton fabAddPetProfile = root.findViewById(R.id.fab_add_pet_profile);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_petprofile);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        PetProfileListAdapter adapter = new PetProfileListAdapter();
        recyclerView.setAdapter(adapter);

        fabAddPetProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_nav_pet_profile_to_addPetProfileFragment);
            }
        });
        // dummy data
/*        List<PetProfile> petProfileList = new ArrayList<>();
        PetProfile petProfile = new PetProfile();
        petProfile.setImage_url("https://cdn.wamiz.pl/media/cache/upload_main-image_414w/uploads/animal/breed/dog/baby/5caf14853910b375817947.jpg");
        petProfile.setName("Azor");
        petProfileList.add(petProfile);*/


//        mPetProfileViewModel.getPetProfiles().observe(getViewLifecycleOwner(), new Observer<List<PetProfile>>() {
//            @Override
//            public void onChanged(List<PetProfile> petProfiles) {
//
//            }
//        });
        return root;
    }
}