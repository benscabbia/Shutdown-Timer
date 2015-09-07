package gudthing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Ben on 03/09/2015.
 */
public class MainFrame extends JFrame {
    private JPanel timePanel, buttonPanel;
    private JTextField hour, minutes, seconds;
    private JCheckBox force;
    private JButton start, end;
    TrayIcon trayIcon;
    SystemTray tray;

    public MainFrame() {
        super("Smart Mail");
        setLayout(new BorderLayout());

        //initialize Panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(0, 2, 0, 5));
        timePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        hour = new JTextField(5);
        minutes = new JTextField(5);
        seconds = new JTextField(5);
        force = new JCheckBox();

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
        end = new JButton("End Timer");

        buttonPanel.add(start, BorderLayout.CENTER);
        buttonPanel.add(end, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.SOUTH);

        //frame operations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setMinimumSize(new Dimension(200, 200));
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }


}
