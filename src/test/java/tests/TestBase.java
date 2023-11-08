package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    public static void genericSetUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        // Retrieve the config file path from command line
        String configFilePath = System.getProperty("configFilePath");
        // Initialize ConfigReader with the specified config file path
        ConfigFileReader config = new ConfigFileReader(configFilePath);

//        caps.setCapability("avd", config.getAVDName());
        caps.setCapability("automationName", config.getAutomationName());
        caps.setCapability("platformName", config.getPlatformName());
        caps.setCapability("deviceName", config.getDeviceName());
//        caps.setCapability("platformVersion", config.getPlatformVersion()); //This is commented only for the CI, but it can be used normally when testing locally
        caps.setCapability("app", System.getProperty("user.dir") + config.getAppPath());

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

    public static void tearDown() {
//        String command = "killall Simulator";
        String command = "adb emu kill";
        if (null != driver) {
            driver.quit();
        }
        try {
            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            // Wait for the process to complete
            process.waitFor();
            System.out.println("Test Finished.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
