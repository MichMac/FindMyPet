package com.example.findmypet.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.findmypet.R;
import com.example.findmypet.viewmodels.MissingFoundViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MissingFoundFragment extends Fragment {

    private MissingFoundViewModel mMissingFoundViewModel;

    FloatingActionButton mAddAnnFab, mAddLostPetAnnFab, mAddFoundPetAnnFab;
    TextView addLostPetAnnText, addFoundPetAnnText;
    Boolean isAllFabsVisible;
    Boolean isFoundAnn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mMissingFoundViewModel =
                new ViewModelProvider(this).get(MissingFoundViewModel.class);
        View root = inflater.inflate(R.layout.missing_found_fragment, container, false);

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

        mAddFoundPetAnnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFoundAnn = true;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isFoundAnn",isFoundAnn);
                Navigation.findNavController(getView()).navigate(R.id.action_nav_missing_lost_to_addAnnouncementLocation,bundle);
            }
        });

        mAddLostPetAnnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFoundAnn = false;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isFoundAnn",isFoundAnn);
                Navigation.findNavController(getView()).navigate(R.id.action_nav_missing_lost_to_addAnnouncementLocation,bundle);
            }
        });


//        mMissingFoundViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        
        return root;
    }
}