package com.bobko.multithreading;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    private Hook hook = new Hook();
    private Object monitor = new Object();

    private Storage storage = new Storage();
    private SomeService someService = new SomeService();

    private LinkedList<Integer> queue = new LinkedList<>();
    private List<Thread> threads = new ArrayList<>();

    private Lock lock = new ReentrantLock(true);
    private Condition c = lock.newCondition();

    public static void main(String[] args) {

        new Main().method();

    }

    private void method() {

        for (int i = 0; i < 12; i++) {

            // storage.setValue(storage.getValue() + 1);

            Thread t = new Thread(() -> {

                while (hook.isActive()) {
                    try {
                        boolean l = lock.tryLock();
                        if (l) {
    //                    synchronized (monitor) {
                        doWork();
                        }
    //                    }
                    } finally {
                        lock.unlock();
                    }
                }

            }, "collector-" + i);

            t.start();

            threads.add(t);

        }

        new Thread(() -> {

            for (int i = 0; i < 10; i++) {
//                synchronized (monitor) {
                try {
                    lock.lock();
                    queue.add(1);
//                    monitor.notifyAll();
                    c.signalAll();
                } finally {
                    lock.unlock();
                }
//                }

            }

            System.err.println(Thread.currentThread().getName() + " Is about to finish");

        }, "producer").start();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        hook.setActive(false);
        mySignal();
//         myNotify();
//        interrupt();
        System.err.println("VALUE = " + storage.getValue());

    }

    private void doWork() {
        if (queue.isEmpty()) {
            try {
                System.err.println(Thread.currentThread().getName() + " is about to wait");
                c.await();
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " was interrupted");
                e.printStackTrace();
            }
        } else {

            Integer nextValue = queue.poll();

            storage.setValue(someService.processValue(storage.getValue(), nextValue == null ? 0 : nextValue));

            // System.out.println(Thread.currentThread().getName()
            // + ":" + storage.getValue() + " I'm off");
        }
        System.err.println(Thread.currentThread().getName() + " is about to release monitor");
        
    }

    private void interrupt() {
        threads.stream().forEach(Thread::interrupt);

    }

    private void myNotify() {
        synchronized (monitor) {
            monitor.notifyAll();
        }

    }

    private void mySignal() {
        try {
        lock.lock();
        c.signalAll();
        } finally {
            lock.unlock();
        }
        

    }

}
