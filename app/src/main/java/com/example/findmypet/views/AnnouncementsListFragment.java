package com.example.findmypet.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findmypet.R;
import com.example.findmypet.adapters.AnnouncementsListAdapter;
import com.example.findmypet.adapters.OnAnnouncementListener;
import com.example.findmypet.adapters.PetProfileListAdapter;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.viewmodels.AnnouncementsListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class AnnouncementsListFragment extends Fragment implements OnAnnouncementListener {

    private AnnouncementsListViewModel mAnnouncementsListViewModel;
    private static final String TAG = "AnnouncementsListFragme";

    FloatingActionButton mAddAnnFab, mAddLostPetAnnFab, mAddFoundPetAnnFab;
    TextView addLostPetAnnText, addFoundPetAnnText;
    Boolean isAllFabsVisible;
    Boolean isFoundAnn;
    AnnouncementsListAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.announcements_list_fragment, container, false);

        mAnnouncementsListViewModel = new ViewModelProvider(this).get(AnnouncementsListViewModel.class);
        mAnnouncementsListViewModel.init();


        RecyclerView recyclerView = root.findViewById(R.id.recyclerview_announcements);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new AnnouncementsListAdapter(this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

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
                            root.findViewById(R.id.recyclerview_announcements).setAlpha((float)0.20);
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
                            root.findViewById(R.id.recyclerview_announcements).setAlpha(1);
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
                bundle.putBoolean("isFoundAnn", isFoundAnn);
                Navigation.findNavController(getView()).navigate(R.id.action_nav_missing_lost_to_addAnnouncementLocation, bundle);
            }
        });

        mAddLostPetAnnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFoundAnn = false;
                Bundle bundle = new Bundle();
                bundle.putBoolean("isFoundAnn", isFoundAnn);
                Navigation.findNavController(getView()).navigate(R.id.action_nav_missing_lost_to_addAnnouncementLocation, bundle);
            }
        });

        mAnnouncementsListViewModel.getAnnouncements().observe(getViewLifecycleOwner(), new Observer<List<Announcement>>() {
            @Override
            public void onChanged(List<Announcement> announcements) {
                if(announcements.size() != 0){
                    adapter.setAnnouncements(announcements);
                }
                else{
                    Log.d(TAG,"Live data of announcements is null!");
                }
            }
        });

        return root;
    }

    @Override
    public void OnAnnouncementClick(int position) {

        Announcement announcementInfo = adapter.getSelectedAnnouncement(position);
        mAnnouncementsListViewModel.setAnnouncement(announcementInfo);
        Navigation.findNavController(getView()).navigate(R.id.action_nav_missing_found_to_announcementFragment);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };
}
