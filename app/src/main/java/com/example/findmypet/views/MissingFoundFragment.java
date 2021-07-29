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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mMissingFoundViewModel =
                new ViewModelProvider(this).get(MissingFoundViewModel.class);
        View root = inflater.inflate(R.layout.missing_found_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        FloatingActionButton fab = root.findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Floating button test", Toast.LENGTH_SHORT).show();
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