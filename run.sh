#1-Start Appium Server
appium --use-plugins=gestures & sleep 5 &
##wait for 5 seconds & continue (whatever the results is)


#2-Remove the old created Allure results folder
rm -rf allure-results

#3-Execute the test script based on specific configuration file
mvn clean test -Pgeneric -DconfigFilePath=src/main/resources/configFiles/android.properties

#4-Copy the old reports history folder (to keep the execution history)
cp -r allure-report/history allure-results/

#5-Generate new Allure report
allure generate --clean ./allure-results -o ./allure-report

#7-Kill the opened Appium instance
pkill -f appium

#6-Open the generated allure report
allure open ./allure-report

