package com.bobko.multithreading.state;

public class ThreadStates {

    private static Object obj = new Object();

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            doRun();
        }, "thread 1");

        printThreadNameAndState(t1);

        t1.start();

        printThreadNameAndState(t1);

        Thread t2 = new Thread(() -> {
            doRun();
        }, "thread 2");

        t2.start();

        printThreadNameAndState(t2);
        printThreadNameAndState(t1);

        synchronized (obj) {
            obj.notify();
        }

        printThreadNameAndState(t1);

    }

    private static void printThreadNameAndState(Thread thread) {
        System.out.println(thread.getName() + ": " + thread.getState());
    }

    private static void doRun() {
        synchronized (obj) {

            try {
                obj.wait(90000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getState());

        }
    }

}
