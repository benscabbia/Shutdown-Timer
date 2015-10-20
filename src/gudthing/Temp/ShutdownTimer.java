package gudthing.Temp;
/**
 * Created by Ben on 27/08/2015.
 * This is a simple shutdown timer for Windows
 * You can specify time in h/m/s and can also abort shutdown
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ShutdownTimer extends JFrame implements ActionListener{
    JTextField hour, minutes, seconds;
    JButton btn, btn2;
    JLabel label1, label2, label3, label4;

    public ShutdownTimer() {
        //JFrame frame = new JFrame();
        super("");
        //frame.setSize(130, 180);
        //noinspection MagicConstant
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(new FlowLayout());

        setSize(130, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        label1 = new JLabel("      Hours");
        label2 = new JLabel("  Minutes");
        label3 = new JLabel("Seconds");
        label4 = new JLabel("\t\t\t\t\t\t\tBy Gudthing");

        label4.setFont(label4.getFont().deriveFont(8.0f));

        hour = new JTextField(5);
        minutes = new JTextField(5);
        seconds = new JTextField(5);

        hour.setText("0"); //set default hour
        minutes.setText("20"); //set default minutes
        seconds.setText("0"); //set default seconds

        btn = new JButton("Start Timer     ");
        btn.addActionListener(this);

        btn2 = new JButton("Halt Timer       ");
        btn2.addActionListener(this);

        /*frame.add(label1);
        frame.add(hour);
        frame.add(label2);
        frame.add(minutes);
        frame.add(label3);
        frame.add(seconds);
        frame.add(btn);
        frame.add(btn2);
        frame.add(label4);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);*/
        add(label1);
        add(hour);
        add(label2);
        add(minutes);
        add(label3);
        add(seconds);
        add(btn);
        add(btn2);
        add(label4);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ev) {
        if (ev.getActionCommand().equals("Start Timer     ")) {
            int h, m, s;
            long ms;
            String shutdown;
            try {
                h = hour.getText().equals("") ? 0 : Integer.parseInt(hour.getText());
                m = minutes.getText().equals("") ? 0 : Integer.parseInt(minutes.getText());
                s = seconds.getText().equals("") ? 0 : Integer.parseInt(seconds.getText());

                ms = (h * 3600) + (m * 60) + (s);
                shutdown = "shutdown -s -t " + ms;
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(shutdown);
                System.exit(0);

            } catch (NumberFormatException | IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec("shutdown -a");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
