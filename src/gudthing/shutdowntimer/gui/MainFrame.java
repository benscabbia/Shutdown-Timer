package gudthing.shutdowntimer.gui;

import gudthing.shutdowntimer.model.TimerControl;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

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
        force.setSelected(true);

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

        //check if system tray is supported
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            //need to add image
            Image image = Toolkit.getDefaultToolkit().getImage("D:\\Users\\Ben\\IdeaProjects\\shutdowntimer\\src\\gudthing\\clock-128.png");

            ActionListener existListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };


            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(existListener);
            popup.add(defaultItem);

            defaultItem = new MenuItem("Open");
            defaultItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                }
            });

            popup.add(defaultItem);
            trayIcon = new TrayIcon(image, "Shutdown Timer", popup);

            //set trayicon properties
            trayIcon.setImageAutoSize(true);
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        setVisible(true);
                        setExtendedState(JFrame.NORMAL);
                    }
                }
            });

        } else {
            System.out.println("ST not supported");
        }

        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                        // trayIcon.displayMessage("I'm Running", "This program is still running. ", TrayIcon.MessageType.INFO );
                    } catch (AWTException ex) {
                        System.out.println("unable to add to tray");
                    }
                }
                if (e.getNewState() == 7) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                    } catch (AWTException ex) {
                        System.out.println("unable to add to system tray");
                    }
                }
                if (e.getNewState() == MAXIMIZED_BOTH) {
                    tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
                if (e.getNewState() == NORMAL) {
                    tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
            }
        });
        //setIconImage(Toolkit.getDefaultToolkit().getImage(""));




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
