package com.kadmiv.game.rest;

import com.gaijin.game.android.dataclasses.ServerResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ServerAPI {

    @GET()
    Call<ServerResponse> connectToServer(@Url String fileUrl);

    class Factory {

        private static ServerAPI api;

        public static ServerAPI getInstance() {
            if (api == null) {
                // Init REST API
                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://html5test.com/")
                        .build();
                api = retrofit.create(ServerAPI.class);
                return api;
            } else {
                return api;
            }
        }
    }

}
