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
    private JCheckBox force;
    private JButton start, end;
    private JLabel timeLabel;
    private TimerControl timerControl;
    private boolean running;
    private JSpinner hour, minutes, seconds;
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

        //default 20 minutes
        SpinnerModel spinnerModel = new SpinnerNumberModel(20, 0, 99, 1);
        minutes = new JSpinner();
        minutes.setModel(spinnerModel);
        SpinnerModel spinnerModel2 = new SpinnerNumberModel(0, 0, 99, 1);
        SpinnerModel spinnerModel3 = new SpinnerNumberModel(0, 0, 99, 1);
        hour = new JSpinner();
        seconds = new JSpinner();
        hour.setModel(spinnerModel2);
        seconds.setModel(spinnerModel3);


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

        //start button
        createButtons();

        buttonPanel.add(start, BorderLayout.CENTER);
        buttonPanel.add(end, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setSystemTray();
        setWindowStateListener();

        //frame operations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(190, 220));
        //setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    //method sets system tray  behaviour when minimized such as open & exit options and restore when left-clicked
    //also sets system icon image
    private void setSystemTray() {
        //check if system tray is supported
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            //Image image = Toolkit.getDefaultToolkit().getImage("D:\\Users\\Ben\\IdeaProjects\\shutdowntimer\\src\\gudthing\\shutdowntimer\\data\\clock-128.png");
            //Image image = new ImageIcon(this.getClass().getResource("/data/clock-128.png")).getImage();
            Image image = Toolkit.getDefaultToolkit().createImage(this.getClass().getResource("/gudthing/shutdowntimer/data/clock-128.png"));


            ActionListener existListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            //create right-click popup menu
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
            setIconImage(image);

            //set trayicon properties
            trayIcon.setImageAutoSize(true);
            //make frame visible with single left click
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
    }

    //method minimizes frame to tray and sets system tray icon
    private void setWindowStateListener() {
        addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                        timerControl.setTrayIcon(trayIcon);
                        trayIcon.displayMessage("I'm still here", "I'll keep track of time for you, but you want me gone, right-click and select exit. ", TrayIcon.MessageType.INFO);
                        //showTrayIcon("I'm still here", "I'll keep track of time for you, but you want me gone, right-click and select exit. ");
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
    }

    //create button and listeners
    private void createButtons() {
        //start button
        start = new JButton("Start Timer");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int h, m, s, ms;
                boolean forced;
                h = hour.getValue().equals("") ? 0 : (int) hour.getValue() * 3600;
                m = minutes.getValue().equals("") ? 0 : (int) minutes.getValue() * 60;
                s = seconds.getValue().equals("") ? 0 : (int) seconds.getValue();

                forced = force.isSelected() ? true : false;
                ms = h + m + s;
                timerControl.startTimer(ms, forced);
                running = true;
                toggleButtonStatus();
            }
        });

        //end button
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
    }

    //used to stop further input while timer is running
    private void toggleButtonStatus() {
        if (start.isEnabled()) {
            hour.setEnabled(false);
            minutes.setEnabled(false);
            seconds.setEnabled(false);
            start.setEnabled(false);
            running = true;
            //minimize frame on close if timer active
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);// <- prevent closing if timer on
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    setExtendedState(JFrame.ICONIFIED);
                }
            });
        } else {
            hour.setEnabled(true);
            minutes.setEnabled(true);
            seconds.setEnabled(true);
            start.setEnabled(true);
            running = false;
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
