package com.example.findmypet.repositories;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;

public class NFC {

    private Context context;
    private Tag mTag;
    private NfcAdapter mNfcAdapter;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists = new String[][] { new String[] { NfcA.class.getName() } };
    private String nfcMsg;
    private boolean isWriteSuccess = false;
    private boolean isReadSuccess = false;

    public NFC(Context context){
        this.context = context;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(context);
    }

    public void isNfcAdapterNotNull(){
        if (mNfcAdapter != null) {
            //Toast.makeText(context, "Read an NFC tag", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(context, "NFC is not enabled.", Toast.LENGTH_LONG).show();
        }
    }

    public String getNFCMessage(Intent intent) {
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if (data != null) {
            try {
                NdefRecord[] recs = ((NdefMessage) data[0]).getRecords();
                if (recs[0].getTnf() == NdefRecord.TNF_WELL_KNOWN &&
                        Arrays.equals(recs[0].getType(), NdefRecord.RTD_TEXT)) {
                    byte[] payload = recs[0].getPayload();
                    String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
                    int langCodeLen = payload[0] & 0077;

                    nfcMsg = new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1,
                            textEncoding);
                    isReadSuccess = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nfcMsg;
    }

    private NdefRecord createTextRecord(String payload, Locale locale, boolean encodeInUtf8) {
        byte[] langBytes = locale.getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }

    public void write(String text, Tag tag) {
        // Record that defines app package to launch it after tapping the tag
        NdefRecord aarRecord = NdefRecord.createApplicationRecord("com.example.findmypet");
        NdefRecord[] records = { createTextRecord(text, new Locale("pl_PL"),true),aarRecord };
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        try {
            // Enable I/O
            ndef.connect();
            // Write the message
            ndef.writeNdefMessage(message);
            // Close the connection
            ndef.close();
            setWriteSuccess(true);
        } catch (IOException | FormatException e) {
            setWriteSuccess(false);
            e.printStackTrace();
        }
    }

    public IntentFilter[] getIntentFilters() {
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try{
            ndefIntent.addDataType("*/*");
            mIntentFilters = new IntentFilter[] { ndefIntent };
        } catch (IntentFilter.MalformedMimeTypeException e) {
            e.printStackTrace();
        } catch(Exception e){
            Log.e("Intentfilter", e.toString());
        }
        return mIntentFilters;
    }

    public String[][] getNFCTechLists() {
        return mNFCTechLists;
    }

    public Tag getTag(Intent intent) {
        mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        return mTag;
    }

    public NfcAdapter getNfcAdapter() {
        return mNfcAdapter;
    }

    public boolean isWriteSuccess() {
        return isWriteSuccess;
    }

    public boolean isReadSuccess() { return isReadSuccess; }

    public void setWriteSuccess(boolean writeSuccess) {
        isWriteSuccess = writeSuccess;
    }

   //public void isTagConnected() {return isTagConnected;  }

}
