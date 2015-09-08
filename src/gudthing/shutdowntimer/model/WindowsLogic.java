package gudthing.shutdowntimer.model;

import java.io.IOException;

/**
 * Created by Ben on 08/09/2015.
 */
public class WindowsLogic {
    String shutdown;

    public void startShutdown(long millisecs, boolean forced) {
        System.out.println(millisecs);
        shutdown = "shutdown -s -t " + millisecs;
        if (forced) {
            shutdown += " -f";
        }

        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(shutdown);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
        System.out.println(shutdown);
    }

    public void abortShutdown() {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("shutdown -a");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
