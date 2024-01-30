### Cases To be Covered
- Open the app & select a random item from the "New Products List" & add it to the shopping cart.
- Verify that the same app info are displayed in both the item details screen & in the cart details screen.
- The use can select the quantity of items to be added to the cart & verify that the quantity selected is the same in cart details & the total price = itemPrice * quantity of items

### Environments
- Only Android is covered as the iOS app (.app or .ipa).
- In order to be able to execute this script on iOS, we need to put the corresponding locators at each of the "PageObjects" classes then add a specific "configFile" >> (Ex. ios.properties) 

### "apps" folder
Contains the apk file for the app to be tested

### configFiles
Are mainly used to provide the environment specs at which the Text will be executed

### Locating elements not in the current Display
- Elements that are not currently displayed, can't be located (in other Words, they are not in DOM & can't be located)
- In order to locate those elements, Scrolling is regularly used

### Naming & Clarifications
- Using expressive Variables, Methods, Classes, ......... names where the user can understand the target by just reading the name
- Comments were provided whenever needed

### For the Workflow (Github-Actions)
    #The Branch name was modified not to fire the Workflow
    #This is because the APK file size is more than 100MB (Approx. 284MB) which can't be uploaded normally to github
    #but rather using the (Git LFS https://git-lfs.github.com/) which intern affects the github actions (Workflows)

### To Run in maven
- mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties

### To Generate Allure Report
- mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties allure:serve

### To keep the "Allure-Report" execution Trials
- modify the pom xml (the allure plugin section) to be like the following
                      <plugin>
                        <groupId>io.qameta.allure</groupId>
                        <artifactId>allure-maven</artifactId>
                        <version>2.12.0</version>
                        <configuration>
                            <resultsDirectory>${project.basedir}/allure-results</resultsDirectory>
                        </configuration>
                    </plugin>
- use the execution command (mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties  allure:serve  -Dallure.results.directory=./allure-results)
- All the runs can be found under the "Retries" tab in each TestCase.

### To keep the "Allure-Report" execution History
- Same POM modification
- Execute the following terminal commands
  ``````
  rm -rf allure-results
  mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties
  cp -r allure-report/history allure-results/
  allure generate --clean ./allure-results -o ./allure-report
  allure open ./allure-report
  ``````

### To Start/Stop the Appium Server
- `appium` >> to start the appium server without any plugins.
- `appium --use-plugins=gestures` >> With the gesture plugin.
- `pkill -f appium` >> To stop the running Appium instances.

### Automate the script Execution
- An executable file for the test execution `run.sh` is created.
- This file contains all the needed steps starts with starting appium server & ends with opening the Allure report.

============================================

### Script Optimizations
- After updating the appium to the latest version, the script is causing issues on Android 12.
- Starting from Android12L (API level 32), the scripts are working fine.
- Optimize the scroll methods >> Done
- Optimize the driver creation process >> Done.
- Use the ThreadLocal >> TBD.
- Check the parallel execution >> TBD.
- check a replacement for `[driver = new AndroidDriver(new URL("http://localhost:4723"), caps);]` >> Done
