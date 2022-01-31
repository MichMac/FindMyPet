package com.example.findmypet.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.findmypet.R;
import com.example.findmypet.adapters.NfcDialogListener;
import com.example.findmypet.utils.NFC;
import com.example.findmypet.utils.EventWrapper;
import com.example.findmypet.viewmodels.NFCReadFragmentViewModel;


public class NFCReadFragment extends DialogFragment {

    public static final String TAG = NFCReadFragment.class.getSimpleName();

    public static NFCReadFragment newInstance() {

        return new NFCReadFragment();
    }

    private TextView mTvMessage;
    private NfcDialogListener mNfcDialogListener;
    private NFCReadFragmentViewModel mNFCReadFragmentViewModel;
    private Boolean isAnnouncementFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nfc_read,container,false);
        initViews(view);
        mNFCReadFragmentViewModel = new ViewModelProvider(getActivity()).get(NFCReadFragmentViewModel.class);
        mNFCReadFragmentViewModel.init();

        return view;
    }

    private void initViews(View view) {

        mTvMessage = (TextView) view.findViewById(R.id.tv_message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNfcDialogListener = (MainActivity)context;
        mNfcDialogListener.onDialogDisplayed(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNfcDialogListener.onDialogDismissed();
    }

    public void onNfcDetected(Intent intent, NFC nfc){
        //Bundle bundle = new Bundle();
        String petProfileID = nfc.getNFCMessage(intent);
        NavController navcontroller = NavHostFragment.findNavController(this);
        mNFCReadFragmentViewModel.findAnnouncementByPetProfileID(petProfileID);
        //LiveData<Boolean> isAnnouncementFound = mMainActivityViewModel.isAnnouncementsFound();

//        isAnnouncementFound.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if (aBoolean){
//                    mMainActivityViewModel.isAnnouncementsFound().removeObserver(this);
//                    mTvMessage.setText(getString(R.string.message_read_success));
//                    navcontroller.navigate(R.id.action_NFCReadFragment_to_announcementFragment);
//                    dismiss();
//            }
//                else {
//                    mTvMessage.setText(getString(R.string.message_read_error));
//                }
//            }
//        });

        mNFCReadFragmentViewModel.isAnnouncementFound().observe(getViewLifecycleOwner(), new Observer<EventWrapper<Boolean>>() {
            @Override
            public void onChanged(EventWrapper<Boolean> booleanEventWrapper) {
                if (booleanEventWrapper != null){
                    final Boolean shouldGetNewValue = booleanEventWrapper.getContentIfNotHandled();

                    if (shouldGetNewValue != null && shouldGetNewValue) {
                        mTvMessage.setText(getString(R.string.message_read_success));
                        navcontroller.navigate(R.id.action_NFCReadFragment_to_announcementFragment);
                        dismiss();
                    }
                    else if (shouldGetNewValue != null) {
                        mTvMessage.setText(getString(R.string.message_read_error));
                    }
                }
            }
        });



        //navController.popBackStack();

//        mMainActivityViewModel.isAnnouncementFound().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                //mMainActivityViewModel.isAnnouncementFound().removeObserver(this);
//                if (aBoolean){
//                    navcontroller.navigate(R.id.action_NFCReadFragment_to_announcementFragment);
//                    //mTvMessage.setText(getString(R.string.message_read_success));
//
//                    dismiss();
//            }
//                else {
//                    mTvMessage.setText(getString(R.string.message_read_error));
//                }
//
//        }
//        });


//        if(isAnnouncementFound){
//            navcontroller.navigate(R.id.action_NFCReadFragment_to_announcementFragment);
//        }else
//            mTvMessage.setText(getString(R.string.message_read_error));
    }


        //bundle.putSerializable("announcement", announcementInfo);
        //Navigation.findNavController(getView()).navigate(R.id.action_NFCReadFragment_to_announcementFragment);

}