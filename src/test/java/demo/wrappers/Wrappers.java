package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
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
            jsExecutor.executeScript("arguments[0].scrollIntoView({block: 'center'});", clickableElement); // scroll to center of viewport
            clickableElement.click();
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
}
