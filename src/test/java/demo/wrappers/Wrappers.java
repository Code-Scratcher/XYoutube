package demo.wrappers;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    static WebDriverWait wait;
    static JavascriptExecutor jsExecutor;
    
    public static WebElement findWebElement(WebDriver driver, By locator, int retryCount, long retryDelay) throws InterruptedException {
        int attempt = 0;
        while (attempt<retryCount) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
                WebElement webElements = driver.findElement(locator);
                return webElements;
            } catch (Exception e) {
                attempt++;
                if(attempt<retryCount) {
                    System.out.println("Attempt " + attempt + " failed to find element. Retrying in " + retryDelay + " seconds.");
                } else {
                    System.out.println("Error finding element after " + retryCount + " attempts: " + e.getMessage());
                    throw e;
                }
                Thread.sleep(retryDelay * 1000);
                System.out.println("Error finding element : " + e.getMessage());
                throw e;
            }
        }
        return null;
    }

    public static List<WebElement> findWebElementList(WebDriver driver, By locator, int retryCount, long retryDelay) throws InterruptedException {
        int attempt = 0;
        while (attempt<retryCount) {
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
                List<WebElement> webElements = driver.findElements(locator);
                return webElements;
            } catch (Exception e) {
                attempt++;
                if(attempt<retryCount) {
                    System.out.println("Attempt " + attempt + " failed to find element. Retrying in " + retryDelay + " seconds.");
                } else {
                    System.out.println("Error finding element after " + retryCount + " attempts: " + e.getMessage());
                    throw e;
                }
                Thread.sleep(retryDelay * 1000);
                System.out.println("Error finding element : " + e.getMessage());
                throw e;
            }
        }
        return null;
    }

    public static void clickWebElement(WebDriver driver, By locator) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            WebElement clickableElement = Wrappers.findWebElement(driver, locator, 3, 1);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", clickableElement); // scroll to center of viewport
            clickableElement.click();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error in clicking element : "+e.getMessage());
            throw e;
        }
    }

    public static void clickWebElement(WebDriver driver, WebElement webElement) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(webElement));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", webElement); // scroll to center of viewport
            webElement.click();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error in clicking element : "+e.getMessage());
            throw e;
        }
    }

    public static void sendKeys(WebDriver driver, By locator, String keys) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            WebElement textInputElement = Wrappers.findWebElement(driver, locator, 3, 1);
            textInputElement.click();
            textInputElement.clear();
            textInputElement.sendKeys(keys);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error sending keys : " + e.getMessage());
            throw e;
        }
    }

    public static void sendKeys(WebDriver driver, WebElement webElement, String keys) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(webElement));
            webElement.click();
            webElement.clear();
            webElement.sendKeys(keys);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error sending keys : " + e.getMessage());
            throw e;
        }
    }

    public static void openSideBarToSelect(WebDriver driver, String option) {
        try {

            jsExecutor = (JavascriptExecutor) driver;

            String xpath = "//*[@id='container']//*[@id='guide-icon']/span/div"; // open side bar button
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            WebElement sidebar = driver.findElement(By.xpath(xpath));
            jsExecutor.executeScript("arguments[0].scrollIntoView();", sidebar); // scroll to center of viewport
            sidebar.click();
            
            HashSet<String> footerHashSet = new HashSet<String>(Arrays.asList("About", "Press", "Copyright", 
                    "Contact us", "Creators", "Advertise", "Developers", "Terms", "Privacy", "Policy & Safety", 
                    "How YouTube works", "Test new features"));

            if(!footerHashSet.contains(option)) {
                System.out.println("Log : Clicking Element in Sidebar Section");
                String sideBarOption = "//*[@id='contentContainer']//yt-formatted-string[contains(text(),'"+option+"')]"; // sidebar option
                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sideBarOption)));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sideBarOption)));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sideBarOption)));
                WebElement sidebarOption = Wrappers.findWebElement(driver, By.xpath(sideBarOption), 3, 1);
                jsExecutor.executeScript("arguments[0].scrollIntoView();", sidebarOption);
                sidebarOption.click();
            } else {
                System.out.println("Log : Clicking Element in Sidebar  Footer Section");
                String sideBarFooterOption = "//*[@id='contentContainer']//*[@id='footer']//a[contains(text(),'"+option+"')]"; // sidebar footer option
                wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(sideBarFooterOption)));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(sideBarFooterOption)));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(sideBarFooterOption)));
                WebElement sideBarFooterOptionElement = driver.findElement(By.xpath(sideBarFooterOption));
                jsExecutor.executeScript("arguments[0].scrollIntoView();", sideBarFooterOptionElement);
                sideBarFooterOptionElement.click();
            }
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error in clicking sidebar : " + e.getMessage());
        }
    }
    
    public static void signIn(WebDriver driver, String email, String password) {
        try {
            System.out.println("Log : Signing in");
            jsExecutor = (JavascriptExecutor) driver;
            String signInButtonXpath = "//*[@id='buttons']//a[@aria-label='Sign in']//*[@class='yt-spec-touch-feedback-shape__fill']"; // sign in button
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(signInButtonXpath)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(signInButtonXpath)));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(signInButtonXpath)));
            WebElement signInButtonWebElement = driver.findElement(By.xpath(signInButtonXpath));   
            jsExecutor.executeScript("arguments[0].scrollIntoView();", signInButtonWebElement); // scroll to center of viewport
            signInButtonWebElement.click();

            String emailInputXpath = "//input[@type='email']"; // email input
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(emailInputXpath)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(emailInputXpath)));
            WebElement emailInputElement = Wrappers.findWebElement(driver, By.xpath(emailInputXpath), 3, 1);
            Wrappers.sendKeys(driver, emailInputElement, email);

            String nextButtonXpath = "//span[contains(text(),'Next')]"; // next button
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nextButtonXpath)));
            WebElement nextButtonElement = Wrappers.findWebElement(driver, By.xpath(nextButtonXpath), 3, 1);
            Wrappers.clickWebElement(driver, nextButtonElement);

            String passwordInputXpath = "//input[@type='password']"; // password input
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(passwordInputXpath)));
            WebElement passwordInputElement = driver.findElement(By.xpath(passwordInputXpath));
            Wrappers.sendKeys(driver, passwordInputElement, password);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(nextButtonXpath)));
            WebElement nextButtonElementForPassword = Wrappers.findWebElement(driver, By.xpath(nextButtonXpath), 3, 1);
            Wrappers.clickWebElement(driver, nextButtonElementForPassword);

        } catch (Exception e) {
            System.out.println("Error in signing in : " + e.getMessage());
        }
    }
}
