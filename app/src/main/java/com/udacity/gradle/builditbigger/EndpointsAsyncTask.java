package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.example.vesko.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<IJokeResult, Void, String> {

    // Please note that Genymotion and standard Android emulators use different IPs to reach "localhost"
    private static final String SERVER_ADDRESS_FOR_EMULATOR = "http://10.0.2.2";
    private static final String SERVER_ADDRESS_FOR_GENYMOTION = "http://10.0.3.2";

    private static MyApi myApiService = null;
    private boolean mIsGenymotion = false;
    private IJokeResult listener;

    public EndpointsAsyncTask(boolean isGenymotion) {
        this.mIsGenymotion = isGenymotion;
    }

    @Override
    protected String doInBackground(IJokeResult... params) {
        if(myApiService == null) {  // Only do this once
            String baseAddress = mIsGenymotion ? SERVER_ADDRESS_FOR_GENYMOTION : SERVER_ADDRESS_FOR_EMULATOR;

            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(baseAddress + ":8081/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        listener = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        listener.onJokeFetched(result);
    }
}