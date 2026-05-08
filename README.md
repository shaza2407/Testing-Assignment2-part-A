📌 Project Overview

This project is a complete Automation Testing Framework developed using:

* Java
* Selenium WebDriver
* TestNG
* Page Object Model (POM)
* Apache POI (Excel Data-Driven Testing)
* Allure Reporting

The framework automates multiple end-to-end test scenarios for the TutorialsNinja demo website:

TutorialsNinja Demo Website￼

The framework follows clean coding practices and supports scalable, reusable, and maintainable automation testing.

⸻

🚀 Features

* ✅ Page Object Model (POM) architecture
* ✅ Data-Driven Testing using Excel files
* ✅ TestNG integration
* ✅ External configuration using .properties file
* ✅ Strong and reliable locators
* ✅ Reusable utility methods
* ✅ Allure Reporting integration
* ✅ Automatic screenshots on test failure
* ✅ Cross-browser support (Chrome / Firefox)
* ✅ Modular and maintainable framework structure
_____

⚙️ Technologies Used

Technology	Purpose
Java	Programming Language
Selenium WebDriver	Browser Automation
TestNG	Test Execution
Apache POI	Excel Handling
Maven	Dependency Management
Allure Report	Test Reporting

⸻

🧪 Automated Test Scenarios

The framework automates the following scenarios:

* User Registration
* Registration Validations
* Valid Login
* Invalid Login
* Currency Change Validation
* Breadcrumb Validation
* Product Sorting
* Product Search
* Advanced Search
* Add Products to Cart
* Checkout & Order Placement

All test data is read dynamically from the Excel sheet.

⸻

📑 Configuration

Configuration values are stored inside:

config.properties

Example:

browser=chrome
baseUrl=http://tutorialsninja.com/demo/index.php?route=common/home
implicitWait=10

⸻

📊 Data-Driven Testing

Test data is stored inside an Excel sheet and read using Apache POI.

Example:

@DataProvider(name = "loginData")
public Object[][] getLoginData() {
    return ExcelUtils.getSheetData("Login");
}

⸻

📸 Allure Reporting

The framework integrates with Allure Reports to provide:

* Test execution summary
* Passed/Failed test statistics
* Screenshots on failure
* Logs and execution details

Generate Allure Report

allure serve allure-results

⸻

▶️ Running the Tests

Run All Tests

mvn test

Run Specific Test

mvn test -Dtest=LoginTest

⸻

✅ Framework Design Highlights

* Separation between test logic and page elements
* Reusable methods and utilities
* Easy maintenance and scalability
* Strong locators strategy
* Dynamic test execution using external data
