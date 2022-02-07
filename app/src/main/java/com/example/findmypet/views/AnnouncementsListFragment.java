package com.example.findmypet.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AnnouncementsListSharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AnnouncementsListFragment extends Fragment implements OnAnnouncementListener {

    private AnnouncementsListSharedViewModel mAnnouncementsListSharedViewModel;
    private static final String TAG = "AnnouncementsListFrag";

    FloatingActionButton mAddAnnFab, mAddLostPetAnnFab, mAddFoundPetAnnFab;
    TextView addLostPetAnnText, addFoundPetAnnText;
    Boolean isAllFabsVisible;
    Boolean isFoundAnn;
    AnnouncementsListAdapter adapter;
    Boolean isSorting;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.announcements_list_fragment, container, false);

        mAnnouncementsListSharedViewModel = new ViewModelProvider(this).get(AnnouncementsListSharedViewModel.class);
        mAnnouncementsListSharedViewModel.init();
        setHasOptionsMenu(true);

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

        mAnnouncementsListSharedViewModel.getAnnouncements().observe(getViewLifecycleOwner(), new Observer<List<Announcement>>() {
            @Override
            public void onChanged(List<Announcement> announcements) {
                if(announcements.size() != 0){
//                    Collections.sort(announcements, new Comparator<Announcement>() {
//                        public int compare(Announcement o1, Announcement o2) {
//                            return o1.getDate().compareTo(o2.getDate());
//                        }
//                    });
                    adapter.setAnnouncements(announcements);
                    //if(isSorting)

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
        mAnnouncementsListSharedViewModel.setAnnouncement(announcementInfo);
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

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem itemSorting = menu.findItem(R.id.sorting_date);
        MenuItem itemSortAsc= menu.findItem(R.id.sorting_ascending_date);
        MenuItem itemSortDesc= menu.findItem(R.id.sorting_descending_date);

        itemSorting.setVisible(true);
        itemSorting.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        itemSortDesc.setVisible(true);
        itemSortAsc.setVisible(true);

        itemSortAsc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getContext(),"Sorting ascending",Toast.LENGTH_LONG).show();
                //isSorting = true;
                mAnnouncementsListSharedViewModel.sortAscending();
                return true;
            }
        });

        itemSortDesc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                mAnnouncementsListSharedViewModel.sortDescending();
                return true;
            }
        });

    }
}
