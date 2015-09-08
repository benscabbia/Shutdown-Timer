package gudthing;

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
    TrayIcon trayIcon;
    SystemTray tray;

    public MainFrame() {
        super("00:00:00");

        setLayout(new BorderLayout());

        //initialize Panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(0, 2, 0, 5));
        timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        hour = new JTextField(5);
        minutes = new JTextField(5);
        seconds = new JTextField(5);
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
                h = hour.getText().equals("") ? 0 : Integer.parseInt(hour.getText()) * 3600;
                m = minutes.getText().equals("") ? 0 : Integer.parseInt(minutes.getText()) * 60;
                s = seconds.getText().equals("") ? 0 : Integer.parseInt(seconds.getText());
                ms = h + m + s;
                timerControl.startTimer(ms);
            }
        });

        end = new JButton("End Timer");

        buttonPanel.add(start, BorderLayout.CENTER);
        buttonPanel.add(end, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.SOUTH);

        //frame operations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(200, 220));
        //setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void setFrameTitle(String title) {
        this.setTitle(title);
    }
    /*public static void setFrametitle(String title){
        setFrametitle(title);
    }*/

}
