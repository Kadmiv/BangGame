package com.kadmiv.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kadmiv.game.fragments.ErrorFragment;
import com.kadmiv.game.fragments.InProcessFragment;
import com.kadmiv.game.fragments.WebFragment;
import com.kadmiv.game.services.LoadAnswerService;

public class FirstActivity extends AppCompatActivity {
    LoaderDBReceiver loaderReceiver;
    FragmentManager fragmentManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        registerReceiver();
        loadData();
    }

    protected void onStart() {
        super.onStart();
        registerReceiver();
    }

    protected void onStop() {
        super.onStop();
        unregisterLoaderReceiver(loaderReceiver);
    }

    // This function load data from DB or file, if DB id empty
    private void loadData() {
        //Loading data process view
        InProcessFragment inProcessFragment = new InProcessFragment();
        replaceFragment(inProcessFragment);

        if (true) {
            // Check internet connection
            if (App.hasConnection(getApplicationContext())) {
                startService(new Intent(this, LoadAnswerService.class));
                return;
            } else {
                sendNokBroadcast(getString(R.string.check_internet));
            }
        } else {
            Intent broadcastIntent = new Intent(getString(R.string.BROADCAST_ACTION));
            broadcastIntent.putExtra(getString(R.string.EXTRA_STATUS), getString(R.string.STATUS_OK));
            sendBroadcast(broadcastIntent);
        }

    }

    private void sendNokBroadcast(String wrongInfo) {
        Intent broadcastIntent = new Intent(getString(R.string.BROADCAST_ACTION));
        broadcastIntent.putExtra(getString(R.string.EXTRA_STATUS), getString(R.string.STATUS_NOK));
        broadcastIntent.putExtra(getString(R.string.EXTRA_CONNECTION_RESULT), wrongInfo);
        sendBroadcast(broadcastIntent);
    }

    private void replaceFragment(Fragment fragmentForChange) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.first_fragment_container, fragmentForChange);
        transaction.commit();
    }

    public class LoaderDBReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(getString(R.string.EXTRA_STATUS));
//            Toast.makeText(context, status, Toast.LENGTH_LONG).show()

            String response = "";
            switch (status) {
                case "STATUS_OK":
                    unregisterLoaderReceiver(loaderReceiver);
                    WebFragment countryFragment = new WebFragment();
                    response = intent.getStringExtra(getString(R.string.EXTRA_CONNECTION_RESULT));
                    replaceFragment(countryFragment);
                    break;
                case "STATUS_NOK":
                    response = intent.getStringExtra(getString(R.string.EXTRA_CONNECTION_RESULT));
                    ErrorFragment errorFragment = new ErrorFragment();
                    errorFragment.setErrorText("Error! $response");
                    replaceFragment(errorFragment);
                    break;
                case "RELOAD":
                    loadData();
                    break;
            }
        }
    }

    private void registerReceiver() {
        // Create and connect to Broadcast
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getString(R.string.BROADCAST_ACTION));
        loaderReceiver = new LoaderDBReceiver();
        try {
            registerReceiver(loaderReceiver, intentFilter);
        } catch (Exception ex) {
            unregisterLoaderReceiver(loaderReceiver);
            registerReceiver(loaderReceiver, intentFilter);
        }
    }

    private void unregisterLoaderReceiver(LoaderDBReceiver loaderReceiver) {
        try {
            unregisterReceiver(loaderReceiver);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
    }
}
