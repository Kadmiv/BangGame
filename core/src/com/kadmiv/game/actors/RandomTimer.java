package com.kadmiv.game.actors;

import static java.lang.Thread.sleep;

public class RandomTimer {


    public interface CallBack {
        void ready();

        void steady();

        void bang();
    }

    final int MAX_READY_TIME = 2;
    final int MAX_STEADY_TIME = 3;
    final int MAX_BANG_TIME = 5;

    CallBack callBack;

    public void registerCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void start() {
        Runnable runnable = () -> {
            double delay = Math.random() * MAX_READY_TIME + 1;
            doCommand(delay, MAX_READY_TIME);
            delay = Math.random() * MAX_STEADY_TIME;
            doCommand(delay, MAX_STEADY_TIME);
            delay = Math.random() * MAX_BANG_TIME;
            doCommand(delay, MAX_BANG_TIME);
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void doCommand(double delay, int command) {
        try {
            sleep((long) (delay * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (command) {
            case MAX_READY_TIME:
                callBack.ready();
                break;
            case MAX_STEADY_TIME:
                callBack.steady();
                break;
            case MAX_BANG_TIME:
                callBack.bang();
                break;
        }
    }

}
