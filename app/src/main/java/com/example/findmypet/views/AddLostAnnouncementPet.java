package com.example.findmypet.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.findmypet.R;

public class AddLostAnnouncementPet extends Fragment {

    private Button btnNext;
    private Spinner spGender;
    ArrayAdapter<CharSequence> genderAdapter;

    public AddLostAnnouncementPet() {
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

        btnNext = root.findViewById(R.id.next_button_add_ann_lost_pet);

        spGender = root.findViewById(R.id.gender_spinner_add_ann_lost_pet);
        genderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_array, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).navigate(R.id.action_addLostAnnouncementPet_to_addFoundAnnouncementContact);
            }
        });

       return root;
    }
}