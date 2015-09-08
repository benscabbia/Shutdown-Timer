package gudthing.shutdowntimer.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by Ben on 07/09/2015.
 */
public class TimerControl {
    Timer t;
    JLabel timeLabel;
    int time;
    JFrame mainFrame;
    WindowsLogic windowLogic;

    /*Constructor which points class to label*/
    public TimerControl(JFrame mainFrame, JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.mainFrame = mainFrame;
        windowLogic = new WindowsLogic();
    }

    public void startTimer(int startTime, boolean forced) {
        this.time = startTime;
        formatTime(time);

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrementTime();
                String formatTime = formatTime(time);
                timeLabel.setText(formatTime);
                mainFrame.setTitle(formatTime);
            }
        });
        t.start();
        windowLogic.startShutdown(startTime, forced);

    }

    public void stopTimer() {
        t.stop();
        windowLogic.abortShutdown();
    }

    private String formatTime(long millisecs) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.SECONDS.toHours(millisecs),
                TimeUnit.SECONDS.toMinutes(millisecs) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(millisecs)),
                TimeUnit.SECONDS.toSeconds(millisecs) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(millisecs)));

    }

    private void decrementTime() {
        time -= 1;
    }


}
