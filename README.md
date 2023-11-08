## Cases ToCover
- Open the app & select a random item from the "New Products List" & add it to the shopping cart.
- Verify that the same app info are displayed in both the item details screen & in the cart details screen.
- The use can select the quantity of items to be added to the cart & verify that the quantity selected is the same in cart details & the total price = itemPrice * quantity of items


### To Run in maven
- mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties

### To Generate Allure Report
- mvn clean test -Pgeneric -DconfigFilePath=configFiles/android.properties allure:serve

