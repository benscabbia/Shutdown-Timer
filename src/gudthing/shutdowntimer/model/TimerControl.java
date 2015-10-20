package gudthing.shutdowntimer.model;

import javax.swing.*;
import java.awt.*;
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
    TrayIcon trayIcon;
    boolean started = false;//used to determine if timer is active
    boolean notification = false; //used to determine whether notication has appeared

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
                started = true;
                decrementTime();
                String formatTime = formatTime(time);
                timeLabel.setText(formatTime);
                mainFrame.setTitle(formatTime);
                if (time <= 300 && !notification) {
                    //mainFrame.showTrayIcon("s", "");
                    if (trayIcon != null) {
                        trayIcon.displayMessage("5 Minutes to go!", "Captain, the system will shut down in 5minutes!. ", TrayIcon.MessageType.INFO);
                    }
                    System.out.println("less than min to gooooooo");
                    notification = true;
                }
            }
        });
        t.start();
        windowLogic.startShutdown(startTime, forced);

    }

    public void stopTimer() {
        //checks to see if there's an active timer
        if (started) {
            started = false;
            t.stop();
            timeLabel.setText("00:00:00");
            mainFrame.setTitle("00:00:00");
            if (trayIcon != null) {
                notification = false;
            }
        }
        //let abort still run, incase a different instance is running
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

    public void setTrayIcon(TrayIcon trayicon) {
        this.trayIcon = trayicon;
    }


}
