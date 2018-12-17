package com.kadmiv.game.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gaijin.game.android.dataclasses.ServerResponse;
import com.kadmiv.game.R;
import com.kadmiv.game.rest.ServerAPI;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        serverApi = ServerAPI.Factory.getInstance();
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
