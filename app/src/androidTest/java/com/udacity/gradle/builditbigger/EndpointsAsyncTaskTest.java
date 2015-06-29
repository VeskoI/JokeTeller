package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;
import android.text.TextUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EndpointsAsyncTaskTest extends InstrumentationTestCase implements IJokeResult {

    private CountDownLatch mSignal;
    private String mJoke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mSignal = new CountDownLatch(1);
    }

    public void testAsyncTaskResult() throws Throwable {
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new EndpointsAsyncTask(true).execute(EndpointsAsyncTaskTest.this);
            }
        });

        mSignal.await(22, TimeUnit.SECONDS);
        assertFalse(TextUtils.isEmpty(mJoke));
    }

    @Override
    public void onJokeFetched(String joke) {
        mJoke = joke;
        mSignal.countDown();
    }
}
