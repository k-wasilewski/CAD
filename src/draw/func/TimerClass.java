package draw.func;

import draw.Canvas;

import java.awt.event.ActionEvent;

public class TimerClass {
    private javax.swing.Timer timer;
    private Canvas canvas;

    public TimerClass(Canvas canvas) {
        this.canvas=canvas;
        this.timer=canvas.getTimer();
    }

    public void start() {
        if (timer == null || (timer != null && !timer.isRunning())) {
            timer = new javax.swing.Timer(100, canvas);
            timer.setRepeats(true);
            timer.start();
        }
    }
}
