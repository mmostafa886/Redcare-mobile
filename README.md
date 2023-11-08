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

