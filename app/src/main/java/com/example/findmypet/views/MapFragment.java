package com.example.findmypet.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AnnouncementViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment {

    private static final String TAG = "MapFragment";

    private AnnouncementViewModel mAnnouncementViewModel;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

            mAnnouncementViewModel = new ViewModelProvider(getActivity()).get(AnnouncementViewModel.class);
            mAnnouncementViewModel.init();
            mAnnouncementViewModel.getAnnouncement().observe(getViewLifecycleOwner(), new Observer<Announcement>() {
                @Override
                public void onChanged(Announcement announcement) {
                    LatLng location = new LatLng(announcement.getLatLng().getLatitude(),announcement.getLatLng().getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(location).title("Last known location"));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15));

                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.map_fragment, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}