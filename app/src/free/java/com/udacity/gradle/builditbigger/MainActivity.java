package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vesko.android.jokeslibrary.JokesActivity;


public class MainActivity extends GenericActivity {

    private String mJoke;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Free version of the app, prepare an interstitial for it
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                displayJoke();
            }
        });
        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("YOUR_DEVICE_HASH")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onJokeFetched(String joke) {
        hideLoadingProgress();
        mJoke = joke;

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            // No ad available yet, display joke right away
            displayJoke();
        }
    }

    private void displayJoke() {
        Intent i = new Intent(this, JokesActivity.class);
        i.putExtra(JokesActivity.EXTRA_JOKE, mJoke);
        startActivity(i);
    }
}
