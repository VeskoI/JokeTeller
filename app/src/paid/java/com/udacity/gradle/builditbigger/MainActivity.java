package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.vesko.android.jokeslibrary.JokesActivity;


public class MainActivity extends GenericActivity {

    @Override
    public void onJokeFetched(String joke) {
        Intent i = new Intent(this, JokesActivity.class);
        i.putExtra(JokesActivity.EXTRA_JOKE, joke);
        startActivity(i);
    }
}
