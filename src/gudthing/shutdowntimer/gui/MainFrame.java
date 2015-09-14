package gudthing.shutdowntimer.gui;

import gudthing.shutdowntimer.model.TimerControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ben on 03/09/2015.
 */
public class MainFrame extends JFrame {
    private JPanel timePanel, buttonPanel;
    private JTextField hour, minutes, seconds;
    private JCheckBox force;
    private JButton start, end;
    private JLabel timeLabel;
    private TimerControl timerControl;
    private boolean running;
    TrayIcon trayIcon;
    SystemTray tray;

    public MainFrame() {
        super("00:00:00");

        setLayout(new BorderLayout());

        //initialize Panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(0, 2, 0, 5));
        timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        running = false;
        hour = new JTextField(5);
        hour.setText("00");
        minutes = new JTextField(5);
        minutes.setText("20");
        seconds = new JTextField(5);
        seconds.setText("00");
        force = new JCheckBox();

        timeLabel = new JLabel("00:00:00");
        timePanel.add(new JLabel("Timer:"));
        timePanel.add(timeLabel);

        timerControl = new TimerControl(this, timeLabel);

        timePanel.add(new JLabel("Hours:"));
        timePanel.add(hour);
        timePanel.add(new JLabel("Minutes:"));
        timePanel.add(minutes);
        timePanel.add(new JLabel("Seconds:"));
        timePanel.add(seconds);
        timePanel.add(new JLabel("Force?"));
        timePanel.add(force);

        add(timePanel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());

        start = new JButton("Start Timer");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int h, m, s, ms;
                boolean forced;
                h = hour.getText().equals("") ? 0 : Integer.parseInt(hour.getText()) * 3600;
                m = minutes.getText().equals("") ? 0 : Integer.parseInt(minutes.getText()) * 60;
                s = seconds.getText().equals("") ? 0 : Integer.parseInt(seconds.getText());
                forced = force.isSelected() ? true : false;
                ms = h + m + s;
                timerControl.startTimer(ms, forced);
                running = true;
                toggleButtonStatus();
            }
        });

        end = new JButton("End Timer");
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if there's an active timer

                timerControl.stopTimer();


                //ensure user can't disable start with no timer running
                if (running) {
                    toggleButtonStatus();
                }

            }
        });

        buttonPanel.add(start, BorderLayout.CENTER);
        buttonPanel.add(end, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.SOUTH);

        //frame operations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(190, 220));
        //setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void setFrameTitle(String title) {
        this.setTitle(title);
    }

    //used to stop further input while timer is running
    private void toggleButtonStatus() {
        if (start.isEnabled()) {
            hour.setEnabled(false);
            minutes.setEnabled(false);
            seconds.setEnabled(false);
            start.setEnabled(false);
            running = true;
        } else {
            hour.setEnabled(true);
            minutes.setEnabled(true);
            seconds.setEnabled(true);
            start.setEnabled(true);
            running = false;
        }

    }

}
