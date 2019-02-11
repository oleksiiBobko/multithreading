package com.bobko.multithreading.threadlocal;

import java.util.Random;

public class ThreadLocalExample {

    public static class MyRunnable implements Runnable {

        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

        @Override
        public void run() {
            threadLocal.set(new Random().nextInt());

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(threadLocal.get());
        }
    }


    public static void main(String[] args) {

        MyRunnable sharedRunnableInstance = new MyRunnable();

        new Thread(sharedRunnableInstance).start();
        new Thread(sharedRunnableInstance).start();

    }

}