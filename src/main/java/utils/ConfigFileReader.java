package utils;

import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;

/**
 * This class is used to process the data in the properties file (Ex. config.properties)
 * by dealing with the config file path as a variable
 * so that we can pass it as a variable to the maven run command (mvn clean test -DconfigFilePath=<path_to_properties_file>)
 */
@Log
public class ConfigFileReader {
    public static Properties properties = new Properties();

    public ConfigFileReader(String configFilePath) {
        try (FileInputStream input = new FileInputStream(configFilePath)) {
            properties.load(input);
        } catch (Exception e) {
            log.log(Level.ALL, e.getMessage());
//            e.printStackTrace();
        }
    }

    /*we need to add a new (get) method for every newly added property*/
    public String getAutomationName() {
        return properties.getProperty("automationName");
    }

    public String getPlatformName() {
        return properties.getProperty("platformName");
    }

    public String getDeviceName() {
        return properties.getProperty("deviceName");
    }

    public String getAppPath() {
        return properties.getProperty("appPath");
    }

    public String getPlatformVersion() {
        return properties.getProperty("platformVersion");
    }

    public String getAVDName() {
        return properties.getProperty("avd");
    }

}
