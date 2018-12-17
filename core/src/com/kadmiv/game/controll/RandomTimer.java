package com.kadmiv.game.controll;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class RandomTimer {

    public interface CallBack {
        void ready();

        void steady();

        void bang();
    }

    final int MAX_READY_TIME = 2;
    final int MAX_STEADY_TIME = 3;
    final int MAX_BANG_TIME = 4;

    ArrayList<CallBack> callBack = new ArrayList<>();

    // Bread switch from timer
    private boolean isStoped;

    public void registerCallBack(CallBack callBack) {
        this.callBack.add(callBack);
    }

    public void start() {
        isStoped = false;
        Runnable runnable = () -> {
            double delay = Math.random() * MAX_READY_TIME + 1;
            doCommand(delay, MAX_READY_TIME);
            delay = Math.random() * MAX_STEADY_TIME + 1;
            doCommand(delay, MAX_STEADY_TIME);
            delay = Math.random() * MAX_BANG_TIME + 1;
            doCommand(delay, MAX_BANG_TIME);

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    public void stop() {
        isStoped = true;
    }

    private void doCommand(double delay, int command) {

        try {
            sleep((long) (delay * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isStoped) {
            switch (command) {
                case MAX_READY_TIME:
                    for (CallBack call : callBack) {
                        call.ready();
                    }
                    break;
                case MAX_STEADY_TIME:
                    for (CallBack call : callBack) {
                        call.steady();
                    }
                    break;
                case MAX_BANG_TIME:
                    for (CallBack call : callBack) {
                        call.bang();
                    }
                    break;
            }
        }
    }

    public static class Factory {
        private static RandomTimer randomTimer = null;

        public static RandomTimer instance() {
            if (randomTimer == null) {
                randomTimer = new RandomTimer();
            }
            return randomTimer;
        }
    }

}
