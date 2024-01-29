package utils;

import java.io.IOException;

/**
 * Handling the Android app Kill & Launch actions as it is needed in some test cases
 */
public class AppOperations {
    CommandExecution commandExecution;

    //This method needs to be checked with Android14
    public void restartApp() {
        commandExecution = new CommandExecution();
        //The command to kill the app
        String[] killCommand = {"adb", "shell", "am", "force-stop", "shop.shop_apotheke.com.shopapotheke", "&&", "adb", "shell", "pm", "clear", "shop.shop_apotheke.com.shopapotheke"};
        commandExecution.executeCommand(killCommand, "App Killed");
        //The command to start the app
        String[] launchCommand = {"adb", "shell", "am", "start", "-n", "shop.shop_apotheke.com.shopapotheke/com.shopapotheke.activities.main.MainActivity"};
        commandExecution.executeCommand(launchCommand, "App Launched");
    }
}
