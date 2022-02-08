package com.example.findmypet.views;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findmypet.R;
import com.example.findmypet.adapters.NfcDialogListener;
import com.example.findmypet.utils.NFC;
import com.example.findmypet.viewmodels.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NfcDialogListener {

    private AppBarConfiguration mAppBarConfiguration;
    private MainActivityViewModel mMainActivityViewModel;
    private static final String TAG = "MainActivity";
    private DrawerLayout drawer;
    private View headerView;
    private NFC mNFC;
    private PendingIntent mPendingIntent;
    private NavController navController;
    private boolean isDialogDisplayed = false;
    private boolean isWrite = true;
    private NFCWriteFragment mNFCWriteFragment;
    private NFCReadFragment mNFCReadFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        //initialization of header
        headerView = navigationView.getHeaderView(0);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_missing_found, R.id.nav_pet_profile)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_main);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });;
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.getMenu().findItem(R.id.logout_button_main).setOnMenuItemClickListener(menuItem -> {
            mMainActivityViewModel.logOut();
            finish();
            return false;
        });

        navigationView.getMenu().findItem(R.id.nfc_read_button_main).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                drawer.close();
                showReadFragment();
                return false;
            }
        });

        mMainActivityViewModel.getUserMutableLiveData().observe(this, user -> {
            if (user != null) {
                Log.d(TAG, "Navigating to main activity log window...");
                Log.d(TAG, "User onChange : " + user.getEmail() + user.getDisplayName());

                TextView emailBar = headerView.findViewById(R.id.email_main_bar);
                TextView nameBar = headerView.findViewById(R.id.name_main_bar);

                emailBar.setText(user.getEmail());
                nameBar.setText(user.getDisplayName());
            } else {
                Toast.makeText(getApplicationContext(), "User is null", Toast.LENGTH_LONG).show();
            }
        });

        mMainActivityViewModel.getLoggedOutLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean logout) {
                if (logout) {
                    navController.navigate(R.id.navigate_to_auth);
                    finish();
                }
            }
        });

        mNFC = new NFC(getApplicationContext());
        mNFC.isNfcAdapterNotNull();

        // create an intent with tag data and deliver to this activity
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

        String msg = mNFC.getNFCMessage(intent);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

              if (isDialogDisplayed) {

                  if(isWrite) {
                      mMainActivityViewModel.setIntent(intent);
                      Toast.makeText(this, mNFC.getNFCMessage(intent), Toast.LENGTH_LONG).show();
                  }
                  else {
                      mNFCReadFragment = (NFCReadFragment) getSupportFragmentManager().findFragmentByTag(NFCReadFragment.TAG);
                      mNFCReadFragment.onNfcDetected(intent, mNFC);
                  }

            }
              else
              if(msg != null || !msg.equals("")){
                  showReadFragment();
              }

    }

    private void showReadFragment() {

        mNFCReadFragment = (NFCReadFragment) getSupportFragmentManager().findFragmentByTag(NFCReadFragment.TAG);

        if (mNFCReadFragment == null) {

            mNFCReadFragment = NFCReadFragment.newInstance();
        }
        mNFCReadFragment.show(getSupportFragmentManager(), NFCReadFragment.TAG);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.save_action);
        MenuItem sorting = menu.findItem(R.id.sorting_date);
//        MenuItem sortAsc = menu.findItem(R.id.sorting_ascending_date);
//        MenuItem sortDesc = menu.findItem(R.id.sorting_descending_date);

        item.setVisible(false);
        sorting.setVisible(false);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mNFC.getNfcAdapter() != null)
            mNFC.getNfcAdapter().enableForegroundDispatch(this, mPendingIntent, mNFC.getIntentFilters(), mNFC.getNFCTechLists());
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mNFC.getNfcAdapter() != null)
            mNFC.getNfcAdapter().disableForegroundDispatch(this);
    }

    @Override
    public void onDialogDisplayed(boolean isWrite) {
        isDialogDisplayed = true;
        this.isWrite = isWrite;
    }

    @Override
    public void onDialogDismissed() {
        isDialogDisplayed = false;
        isWrite = false;
    }

}