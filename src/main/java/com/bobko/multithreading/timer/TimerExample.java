package com.bobko.multithreading.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {
    
    abstract static class TT extends TimerTask {
        public TT () {
            System.out.println("created");
        }
    }
    
    public static void main(String[] args) {
        TimerExample.TT task = new TimerExample.TT() {
            
            public void run() {
                System.out.println("Task performed on: " + new Date() + " " + "Thread's name: "
                        + Thread.currentThread().getName());
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 1000L;
        timer.scheduleAtFixedRate(task, delay, 1000L);

        timer.cancel();
    }
}
