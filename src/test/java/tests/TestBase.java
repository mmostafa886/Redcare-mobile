package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.CommandExecution;
import utils.ConfigFileReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class TestBase {
    public static AppiumDriver driver;
    public static String platform;
    public static WebDriverWait wait;
    public static final long WAIT = 15;
    CommandExecution commandExecution;

    public static void genericSetUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();

        /** Retrieve the config file path from command line which is also passed to the Surefire plugin configuration*/
        String configFilePath = System.getProperty("configFilePath");

        // Initialize ConfigReader with the specified config file path
        ConfigFileReader config = new ConfigFileReader(configFilePath);

        //Assign every Capability to one of the entries from the properties(config) file
        caps.setCapability("avd", config.getAVDName());//This is commented only for the CI, but it can be used normally when testing locally
        caps.setCapability("automationName", config.getAutomationName());
        caps.setCapability("platformName", config.getPlatformName());
        caps.setCapability("deviceName", config.getDeviceName());
        caps.setCapability("platformVersion", config.getPlatformVersion()); //This is commented only for the CI, but it can be used normally when testing locally
        caps.setCapability("app", System.getProperty("user.dir") + config.getAppPath());

        /**Get the current platform from the properties file
         * & based on that value, the right driver (Android/iOS) is started
         */
        platform = String.valueOf(config.getPlatformName());
        if ("Android".equalsIgnoreCase(platform)) {
            driver = new AndroidDriver(new URL("http://localhost:4723"), caps);
        } else if ("iOS".equalsIgnoreCase(platform)) {
            driver = new IOSDriver(new URL("http://localhost:4723"), caps);
        } else {
            throw new IllegalArgumentException("Platform not Supported!");
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT), Duration.ofMillis(200));
    }

    /**
     * Kill/Close the driver active instance then close/shutdown the used Android Emulator
     * This is supposed to be modified with a selection mechanism (between android & iOS)
     * following the same strategy we did in the same class for starting the driver (in the genericSetUp() method)
     */
    public void tearDown() {
//        String command = "killall Simulator";
        String[] command = {"adb", "emu", "kill"};
        if (null != driver) {
            driver.quit();
        }
        commandExecution = new CommandExecution();
        commandExecution.executeCommand(command, "Shutdown Emulator");
    }
}
