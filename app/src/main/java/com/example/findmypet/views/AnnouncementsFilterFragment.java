package com.example.findmypet.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.findmypet.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

public class AnnouncementsFilterFragment extends DialogFragment {

    public static final String TAG = AnnouncementsFilterFragment.class.getSimpleName();

    TextInputLayout mCity;
    TextInputLayout mType;
    TextInputLayout mDate;
    TextInputLayout mSpecies;
    TextInputLayout mGender;
    TextInputLayout mMicrochipNr;
    MaterialTextView mCityLabel;
    MaterialTextView mTypeLabel;
    MaterialTextView mDateLabel;
    MaterialTextView mSpeciesLabel;
    MaterialTextView mGenderLabel;
    MaterialTextView mMicrochipNrLabel;
    MaterialButton mSearchBtn,mCancelBtn;

    ArrayAdapter<CharSequence> mGenderAdapter;
    ArrayAdapter<CharSequence> mTypeAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.announcements_filter_fragment,container,false);

        //TextInputLayouts
        mCity = view.findViewById(R.id.city_edittext_layout);
        mType = view.findViewById(R.id.type_menu);
        mDate = view.findViewById(R.id.date_menu);
        mSpecies = view.findViewById(R.id.species_edittext_layout);
        mGender = view.findViewById(R.id.gender_menu);
        mMicrochipNr = view.findViewById(R.id.microchip_number_edittext_layout);

        //Set adapters for spinners
        mGenderAdapter = ArrayAdapter.createFromResource(getContext(), R.array.gender_filter_array, android.R.layout.simple_spinner_item);
        mGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((MaterialAutoCompleteTextView) mGender.getEditText()).setAdapter(mGenderAdapter);
        ((MaterialAutoCompleteTextView) mGender.getEditText()).setText(mGenderAdapter.getItem(0),false);

        mTypeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.announcement_filter_type, android.R.layout.simple_spinner_item);
        mTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((MaterialAutoCompleteTextView) mType.getEditText()).setAdapter(mTypeAdapter);
        ((MaterialAutoCompleteTextView) mType.getEditText()).setText(mTypeAdapter.getItem(0),false);

        //TextViews
        mCityLabel = view.findViewById(R.id.city_label);
        mTypeLabel = view.findViewById(R.id.type_label);
        mDateLabel = view.findViewById(R.id.date_label);
        mSpeciesLabel = view.findViewById(R.id.species_label);
        mGenderLabel = view.findViewById(R.id.gender_label);
        mMicrochipNrLabel = view.findViewById(R.id.microchip_number_label);

        //Buttons
        mSearchBtn = view.findViewById(R.id.search_button_filter_ann);
        mCancelBtn = view.findViewById(R.id.cancel_button_filter_ann);

        mCity.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mCityLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mCityLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mType.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mTypeLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mTypeLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mDate.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mDateLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mDateLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mSpecies.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mSpeciesLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mSpeciesLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mGender.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mGenderLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mGenderLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mMicrochipNr.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    mMicrochipNrLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.design_default_color_primary));
                }
                else
                    mMicrochipNrLabel.setTextColor(ContextCompat.getColor(getContext(),R.color.secondary_text_default_material_light));
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
