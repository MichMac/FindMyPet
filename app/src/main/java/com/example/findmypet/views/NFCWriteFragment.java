package com.example.findmypet.views;

import android.app.Dialog;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.findmypet.R;
import com.example.findmypet.adapters.NfcDialogListener;
import com.example.findmypet.models.PetProfile;
import com.example.findmypet.repositories.NFC;
import com.example.findmypet.viewmodels.MainActivityViewModel;
import com.example.findmypet.viewmodels.PetProfileListViewModel;

import android.content.Context;
import android.nfc.tech.Ndef;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;


public class NFCWriteFragment extends DialogFragment{

    public static final String TAG = NFCWriteFragment.class.getSimpleName();

    public static NFCWriteFragment newInstance() {

        return new NFCWriteFragment();
    }

    private MainActivityViewModel mMainActivityViewModel;
    private TextView mTvMessage;
    private ProgressBar mProgress;
    private NFC mNFC;
    private NfcDialogListener mNfcDialogListener;
    private String mPetProfileID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc_write,container,false);
        initViews(view);

        mMainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

        mNFC = new NFC(getContext());
        //Bundle args = getArguments();
        //mPetProfileID = args.getString("petProfileID");     // args.getString("petprofileid");
        //mPetProfileID =  "awdawd";

        mMainActivityViewModel.getIntentMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Intent>() {
            @Override
            public void onChanged(Intent intent) {
                onNfcDetected(intent,mNFC);
                //Log.d(TAG,mPetProfileID);
            }
        });

        mMainActivityViewModel.getPetProfile().observe(getViewLifecycleOwner(), new Observer<PetProfile>() {
            @Override
            public void onChanged(PetProfile petProfile) {
                mPetProfileID = petProfile.getPetProfileID();
            }
        });

        return view;
    }

    private void initViews(View view) {

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
        mProgress = (ProgressBar) view.findViewById(R.id.progress);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNfcDialogListener = (MainActivity)context;
        mNfcDialogListener.onDialogDisplayed(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNfcDialogListener.onDialogDismissed();
    }

    public void onNfcDetected(Intent intent, NFC nfc) {

        mProgress.setVisibility(View.VISIBLE);
        Tag tag = nfc.getTag(intent);
        nfc.write(mPetProfileID,tag);
        if (nfc.isWriteSuccess())
            mTvMessage.setText(getString(R.string.message_write_success));
        else
            mTvMessage.setText(getString(R.string.message_write_error));

        mProgress.setVisibility(View.GONE);

    }
}