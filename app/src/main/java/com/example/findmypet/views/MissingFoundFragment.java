package com.example.findmypet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.findmypet.R;
import com.example.findmypet.viewmodels.MissingFoundViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MissingFoundFragment extends Fragment {

    private MissingFoundViewModel mMissingFoundViewModel;

    FloatingActionButton mAddAnnFab, mAddLostPetAnnFab, mAddFoundPetAnnFab;

    TextView addLostPetAnnText, addFoundPetAnnText;

    Boolean isAllFabsVisible;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mMissingFoundViewModel =
                new ViewModelProvider(this).get(MissingFoundViewModel.class);
        View root = inflater.inflate(R.layout.missing_found_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        mAddAnnFab = root.findViewById(R.id.add_ann_fab);
        mAddFoundPetAnnFab = root.findViewById(R.id.add_found_pet_ann_fab);
        mAddLostPetAnnFab = root.findViewById(R.id.add_lost_pet_ann_fab);

        addFoundPetAnnText = root.findViewById(R.id.add_found_pet_ann_text);
        addLostPetAnnText = root.findViewById(R.id.add_lost_pet_ann_text);

        mAddFoundPetAnnFab.setVisibility(View.GONE);
        mAddLostPetAnnFab.setVisibility(View.GONE);
        addFoundPetAnnText.setVisibility(View.GONE);
        addLostPetAnnText.setVisibility(View.GONE);

        isAllFabsVisible = false;

        mAddAnnFab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs VISIBLE.
                            mAddFoundPetAnnFab.show();
                            mAddLostPetAnnFab.show();
                            addFoundPetAnnText.setVisibility(View.VISIBLE);
                            addLostPetAnnText.setVisibility(View.VISIBLE);

                            // make the boolean variable true as
                            // we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = true;
                        } else {

                            // when isAllFabsVisible becomes
                            // true make all the action name
                            // texts and FABs GONE.
                            mAddFoundPetAnnFab.hide();
                            mAddLostPetAnnFab.hide();
                            addFoundPetAnnText.setVisibility(View.GONE);
                            addLostPetAnnText.setVisibility(View.GONE);

                            // make the boolean variable false
                            // as we have set the sub FABs
                            // visibility to GONE
                            isAllFabsVisible = false;
                        }
                    }
                });

        mMissingFoundViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        
        return root;
    }
}