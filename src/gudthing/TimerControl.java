package gudthing;

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
    int sec, min, hour;

    /*Constructor which points class to label*/
    public TimerControl(JFrame mainFrame, JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.mainFrame = mainFrame;
    }

    public void startTimer(int startTime) {
        this.time = startTime;
        formatTime(time);

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decrementTime();
                timeLabel.setText(formatTime(time));
                mainFrame.setTitle(formatTime(time));
                //System.out.println("The time: " + time);

            }
        });
        t.start();
        timeLabel.setText(formatTime(time));
    }

    public void stopTimer() {
        t.stop();
    }

    public String formatTime(long millis) {
        /*sec = (int) (milliseconds / 1000) % 60 ;
        min = (int) ((milliseconds / (1000*60)) % 60);
        hour = (int) ((milliseconds / (1000*60*60)) % 24);*/
        //System.out.println(millis);
        return String.format("%02d:%02d:%02d",
                TimeUnit.SECONDS.toHours(millis),
                TimeUnit.SECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(millis)),
                TimeUnit.SECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(millis)));

    }

    private void decrementTime() {
        //System.out.println("before de " + time);
        time -= 1;
        //System.out.println("after de " + time);
    }


}
