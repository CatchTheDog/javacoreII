package com.java.core.innerclass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        ActionListener listener = this.new TimerPrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimerPrinter implements ActionListener {
        /**
         * Invoked when an action occurs.
         *
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At the tone, the time is " + new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();
        }

        public void actionPreformed(ActionEvent event) {
            if (TalkingClock.this.beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
