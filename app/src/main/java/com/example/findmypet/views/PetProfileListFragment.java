package com.example.findmypet.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.findmypet.R;
import com.example.findmypet.adapters.PetProfileListAdapter;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.PetProfileListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PetProfileListFragment extends Fragment {

    private static final String TAG = "PetProfileFragment";
    private PetProfileListViewModel mPetProfileListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mPetProfileListViewModel = new ViewModelProvider(this).get(PetProfileListViewModel.class);
        View root = inflater.inflate(R.layout.pet_profile_list_fragment, container, false);

        mPetProfileListViewModel.init();
        FloatingActionButton fabAddPetProfile = root.findViewById(R.id.fab_add_pet_profile);
        ProgressBar mProgressBar = root.findViewById(R.id.progress_bar_petprofiles);

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

        mPetProfileListViewModel.getIsLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    mProgressBar.setVisibility(View.VISIBLE);
                }else{
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        });

        mPetProfileListViewModel.getPetProfiles().observe(getViewLifecycleOwner(), new Observer<List<PetProfile>>() {
            @Override
            public void onChanged(List<PetProfile> petProfiles) {
                if(petProfiles.size() != 0){
                    adapter.setPetProfiles(petProfiles);
                }
                else{
                    Log.d(TAG,"Live data of petprofiles is null!");
                }
            }
        });
        getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.action_nav_pet_profile_to_nav_missing_found);
            }
        });

        return root;
    }
}