package com.example.findmypet.views;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.findmypet.BuildConfig;
import com.example.findmypet.R;
import com.example.findmypet.models.Announcement;
import com.example.findmypet.viewmodels.AddAnnouncementSharedViewModel;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class AddAnnouncementLocationFragment extends Fragment {

    private static final String TAG = "AddAnnouncementLocation";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;

    private Button btnNext;
    private EditText etCountry;
    private EditText etProvince;
    private EditText etCity;
    private EditText etStreet;
    private Boolean isFoundAnn;
   // private DateEditText etDate;
    private EditText etDate;
    private EditText etLocation;
    private TextInputLayout layDate;
    private TextInputLayout layLocation;
    private String city;
    private LatLng latLng;
    private AddAnnouncementSharedViewModel sharedViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                etLocation.setText(place.getAddress());
                latLng = place.getLatLng();
                //Geocoder potrzebny do wyciągnięcia informacji na temat miasta
                Geocoder geocoder = new Geocoder(getContext(),Locale.getDefault());
                try {
                    List<Address> adresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    city = adresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.add_announcement_location_fragment, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(AddAnnouncementSharedViewModel.class);
        sharedViewModel.init();

        btnNext = root.findViewById(R.id.next_button_location_ann);
        etDate = root.findViewById(R.id.date_edittext_loc_ann);
        etLocation = root.findViewById(R.id.location_edittext_loc_ann);
        //etCountry =  root.findViewById(R.id.country_edittext_loc_ann);
        etProvince = root.findViewById(R.id.province_edittext_loc_ann);
        etCity = root.findViewById(R.id.city_edittext_add_ann_pet);
        etStreet = root.findViewById(R.id.street_edittext_add_ann_pet);

        layLocation = root.findViewById(R.id.locationLayout_location_ann);
        layDate = root.findViewById(R.id.dateLayout_location_ann);

        long currentTime = Calendar.getInstance().getTimeInMillis();
//        etDate.listen();
//        etDate.setMaxDate(currentTime);

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();

        CalendarConstraints.DateValidator dateValidatorMax = DateValidatorPointBackward.before(currentTime);

        calendarConstraints.setValidator(dateValidatorMax);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(calendarConstraints.build())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
                Log.d(TAG,"PositiveBtnClick");
                String dateString = DateFormat.format("dd/MM/yyyy", new Date(selection)).toString();
                etDate.setText(dateString);
        });

        datePicker.addOnNegativeButtonClickListener(selection -> {
                Log.d(TAG,"NegativeBtnClick");
        });

//        datePicker.addOnCancelListener(selection ->
//                Log.d(TAG,"Cancel"));
//
//        datePicker.addOnDismissListener(dialogInterface -> {
//                Log.d(TAG,"Dismiss");
//                etDate.setText(datePicker.getSelection().);
//        });

        Places.initialize(getContext(), BuildConfig.MAPS_API_KEY);

        List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS,Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getContext());

        etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getParentFragmentManager(),TAG);
            }
        });

        etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layLocation.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layDate.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments() != null && dataValidation() ){
                    isFoundAnn = getArguments().getBoolean("isFoundAnn");
                    Announcement announcement = new Announcement();
//                    announcement.setCountry(etCountry.getText().toString());
//                    announcement.setProvince(etProvince.getText().toString());
//                    announcement.setStreet(etStreet.getText().toString());
                    announcement.setDate(etDate.getText().toString());
                    announcement.setCity(city);
                    announcement.setLocation(etLocation.getText().toString());
                    announcement.setLatLng(new GeoPoint(latLng.latitude,latLng.longitude));
                    sharedViewModel.setAnnouncementInfo(announcement);
                    if(isFoundAnn)
                        Navigation.findNavController(getView()).navigate(R.id.action_addAnnouncementLocation_to_addFoundAnnouncementPet);
                    else
                        Navigation.findNavController(getView()).navigate(R.id.action_addAnnouncementLocation_to_addLostAnnouncementPet);
                }
            }
        });

        return root;
    }

    private boolean dataValidation(){

        if (etDate.length() == 0) {
            layDate.setError("This field is required");
            return false;
        }
        if (etLocation.length() == 0) {
            layLocation.setError("This field is required");
            return false;
        }

//        try {
//            latLng = sharedViewModel.getGeopoint(location,getContext(), Locale.getDefault());
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(getContext(),"Getting location error",Toast.LENGTH_LONG).show();
//            return false;
//        } catch (IndexOutOfBoundsException e) {
//            e.printStackTrace();
//            Toast.makeText(getContext(),"Given location is not valid!",Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }

}