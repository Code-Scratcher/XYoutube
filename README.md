# Youtube Search Functionality Automation
## This repository contains a Java Selenium project with TestNG automation for verifying Youtube search functionality. The project includes the following:
- Selenium WebDriver
- TestNG framework
- Gradle build tool
- Java 11
- Python 3 for platform assessment (not mandatory).

# Project Structure
## The project structure is as follows:
- src/test/java/demo/wrappers/Wrappers.java -> contains the page object classes and utility classes
- src/test/java/demo/TestCases.java -> contains the test classes
- build.gradle - contains the project dependencies and build configurations
- src/test/java/demo/utils/ExcelReaderUtil.java -> contains utility class and methods to read data from *.xlsx file , return type is `Object[][]`
- src/test/java/demo/utils/ExcelDataProvider.java -> contains method with `@DataProvider` annotation , return type is `Object[][]`
- src/test/resources/data.xlsx -> contains data  -> for implementing a data-driven testcase(TC04)
- src/test/resources/testng.xml -> TestNG suit

# Getting Started
## To run the project locally, follow the instructions below:
1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Install the required dependencies(java 11, python 3, git bash) and the executeable to PATH Environment Variable.
4. Execute the command in `chmod +x ./gradlew` in **Bash terminal**.
5. Build the project running command `./gradlew build` in **Bash terminal** or `.\gradlew.bat build` in **PowerShell** terminal.
6. Run the test script by running `./gradlew test` test in **Bash terminal** or `.\gradlew.bat test` in **PowerShell** terminal.

# Test Class
## The test class TestCases.java contains the following:
- Test annotations @Test for test methods :
  - testCase01() method
  - testCase02() method
  - testCase03() method
  - testCase03() method
  - testCase04(String searchTerm) method
- @BeforeTest :
  - startBrowser() method
- @AfterTest :
  - endTest() method

# Dependencies
## The project dependencies are managed by Gradle and are defined in the build.gradle file. The following dependencies are included:
- Selenium
- TestNG framework
- Apache POI library

# Privacy Information
This project does not collect or store any personal information. The project uses Selenium WebDriver to automate web browsing and does not interact with any user data.

# Contact
For any questions or concerns, [Contact me via email](mailto:d.dey.4999@gmail.com)
