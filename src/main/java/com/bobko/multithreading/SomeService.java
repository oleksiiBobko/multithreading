package com.bobko.multithreading;

import java.util.Random;

public class SomeService {

    public int processValue(int currentValue, int nextValue) {

        try {
            Thread.sleep(new Random().nextInt(2));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return currentValue + nextValue;

    }

}
