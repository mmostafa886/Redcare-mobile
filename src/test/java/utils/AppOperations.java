package utils;

import java.io.IOException;

/**
 * Handling the Android app Kill & Launch actions as it is needed in some test cases
 */
public class AppOperations {
    public void restartApp() throws IOException, InterruptedException {
        String appKill = "adb shell am force-stop shop.shop_apotheke.com.shopapotheke &&  adb shell pm clear shop.shop_apotheke.com.shopapotheke";
        String appLaunch = "adb shell am start -n shop.shop_apotheke.com.shopapotheke/com.shopapotheke.activities.main.MainActivity";

        Process killProcess = Runtime.getRuntime().exec(appKill);
        // Wait for the process to complete
        killProcess.waitFor();
        System.out.println("App Killed");

        Process launchProcess = Runtime.getRuntime().exec(appLaunch);
        // Wait for the process to complete
        launchProcess.waitFor();
        System.out.println("App Launched");
    }
}
