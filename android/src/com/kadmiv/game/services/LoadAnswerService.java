package com.kadmiv.game.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.gaijin.game.android.R;
import com.gaijin.game.android.dataclasses.ServerResponse;
import com.gaijin.game.android.rest.ServerAPI;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;

import static java.lang.Thread.sleep;

public class LoadAnswerService extends Service {

    private ServerAPI serverApi;

    public LoadAnswerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String request = getString(R.string.true_request);
        // Set random request
        if (Math.random() > 0.5) {
            request = getString(R.string.false_request);
        }
        // Send request and get answer. Receive answer to main activity
        serverApi.connectToServer(request).enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                if (response.isSuccessful()) {
                    ServerResponse answer = response.body();
                    Intent broadcastIntent = new Intent(getString(R.string.BROADCAST_ACTION));
                    broadcastIntent.putExtra(getString(R.string.EXTRA_STATUS), getString(R.string.STATUS_OK));
                    broadcastIntent.putExtra(getString(R.string.EXTRA_CONNECTION_RESULT), Boolean.toString(answer.getAllow()));
                    sendBroadcast(broadcastIntent);
                } else {
                    sendNokBroadcast(Integer.toString(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
//                sendNokBroadcast(Integer.toString(response.code()));
                t.getStackTrace();
            }
        });

//        Observable.just("Http Connection")
//                .map(new Function<String, Object>() {
//                    @Override
//                    public Object apply(String s) throws Exception {
//                        loadCountriesAndCities();
//                        return new Object();
//                    }
//                })
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();

        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNokBroadcast(String wrongInfo) {
        Intent broadcastIntent = new Intent(getString(R.string.BROADCAST_ACTION));
        broadcastIntent.putExtra(getString(R.string.EXTRA_STATUS), getString(R.string.STATUS_NOK));
        broadcastIntent.putExtra(getString(R.string.EXTRA_CONNECTION_RESULT), wrongInfo);
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
