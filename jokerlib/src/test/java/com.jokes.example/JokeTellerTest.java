package com.jokes.example;

import org.junit.Test;

public class JokeTellerTest {
    @Test
    public void getJokeTest() {
        assert JokeTeller.getJoke().equals(JokeTeller.DEFAULT_JOKE);
    }
}