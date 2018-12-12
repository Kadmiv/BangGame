package com.kadmiv.game;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kadmiv.game.fragments.ErrorFragment;
import com.kadmiv.game.fragments.InProcessFragment;
import com.kadmiv.game.fragments.WebFragment;
import com.kadmiv.game.services.LoadAnswerService;

public class FirstActivity extends AppCompatActivity {
    LoaderDBReceiver loaderReceiver;
    FragmentManager fragmentManager;
    // This Preferences contain of server answer
    SharedPreferences savedServerAnswer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
    }

    protected void onStart() {
        super.onStart();

        // If server answer is empty - then load info from server
        savedServerAnswer = getSharedPreferences(getString(R.string.EXTRA_SERVER_ANSWER), Context.MODE_PRIVATE);
        String response = savedServerAnswer.getString(getString(R.string.EXTRA_SERVER_ANSWER), "");
        if (response == "") {
            Log.d("12", "Response is empty");
            registerReceiver();
            loadData();
            return;
        }
        Log.d("12", "Response is not empty");
        // Else do something depending on the answer
        doWithResponse(response);
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
                    response = intent.getStringExtra(getString(R.string.EXTRA_CONNECTION_RESULT));
                    doWithResponse(response);
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

    //This function load specific part of program depending on the answer of server
    private void doWithResponse(String response) {

        // Save server answer
        SharedPreferences.Editor editor = savedServerAnswer.edit();
        editor.putString(getString(R.string.EXTRA_SERVER_ANSWER), response);
        editor.apply();

        switch (response) {
            // Load game
            case "true":
                Intent gameIntent = new Intent(getApplicationContext(), AndroidLauncher.class);
                startActivity(gameIntent);
                break;
            // Load site html5test
            case "false":
                WebFragment countryFragment = new WebFragment();
                replaceFragment(countryFragment);
                break;
            default: // For just case
                registerReceiver();
                loadData();
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
