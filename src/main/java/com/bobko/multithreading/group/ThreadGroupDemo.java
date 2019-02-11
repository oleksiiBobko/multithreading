package com.bobko.multithreading.group;

import javax.swing.JFrame;

public class ThreadGroupDemo implements Runnable {
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {

//        JFrame f = new JFrame();
//        f.setVisible(true);

        Thread one = new Thread(new Runnable() {

            @Override
            public void run() {
//                while(true) {
//                    System.out.println("one");
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

            }
        }, "one");

        one.start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("two");

            }
        }, "two").start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("tree");

            }
        }, "tree").start();


        ThreadGroup tg1 = Thread.currentThread().getThreadGroup();

        System.out.println("Thread Group Name: " + tg1.getName());
        tg1.getParent().list();

    }
}