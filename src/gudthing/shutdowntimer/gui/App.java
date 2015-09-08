package gudthing.shutdowntimer.gui;

import javax.swing.*;

/**
 * Created by Ben on 03/09/2015.
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
        //SwingUtilities.invokeLater(() -> new ShutdownTimer()); //http://www.developer.com/java/start-using-java-lambda-expressions.html
        //SwingUtilities.invokeLater(ShutdownTimer::new); //https://blog.idrsolutions.com/2015/02/java-8-method-references-explained-5-minutes/
    }
}
