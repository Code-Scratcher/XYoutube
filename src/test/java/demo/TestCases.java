package demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.s;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
    ChromeDriver driver;
    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */   
    @Test(enabled = true)
    public void testCase01() {    
        System.out.println("Test Case 01 : Start");
        try {
            SoftAssert sa = new SoftAssert();

            driver.get("https://www.youtube.com/");
            Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Current page is not youtube.com");
            System.out.println("Log : Opened youtube.com");

            //open side bar and click on about in footer
            Wrappers.openSideBarToSelect(driver, "About");
            System.out.println("Log : Opened side bar and clicked on About");
            WebElement aboutMessageElement = Wrappers.findWebElement(driver, By.xpath("//section[@class='ytabout__content' and contains(text(),'')]"), 3, 1);
            System.out.println("About message: " + aboutMessageElement.getText());
            
            sa.assertAll();
            
        } catch (Exception e) {
            // Log the exception or throw a custom exception
            System.out.println("Exception in TestCase01: " + e.getMessage());
            Assert.fail("Exception in TestCase01: " + e.getMessage());
        }

        System.out.println("Test Case 01 : End");
    }  
    
    @Test(enabled = true)
    public void testCase02() {
        System.out.println("Test Case 02 : Start");
        try {
            SoftAssert sa = new SoftAssert();
            
            driver.get("https://www.youtube.com/");
            System.out.println("Log : Opened youtube.com/");
            Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Current page is not youtube.com");
            System.out.println("Log : Opened youtube.com");

            //open side bar and click on Films
            Wrappers.openSideBarToSelect(driver, "Films");
            System.out.println("Log : Opened side bar and clicked on Films");

            
            String filmGenre = "Top selling"; // change this to the genre you want to test(when not signed in "Top selling" is the only genre available)
            String rightArrowButtonXpath = "//*[@id='title' and contains(text(),'"+filmGenre+"')]//ancestor::*[@id='dismissible']//*[@id='right-arrow']//button";
            String leftArrowButtonXpath = "//*[@id='title' and contains(text(),'"+filmGenre+"')]//ancestor::*[@id='dismissible']//*[@id='left-arrow']//button";

            
            WebDriverWait wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(rightArrowButtonXpath)));
            // click until right arrow button is not visible
            System.out.println("Log : Clicking right arrow until last movie card is visible");

            try {
                while (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(rightArrowButtonXpath)))!=null) {  // TimeoutException when arrow button is not visible
                    WebElement rightArrowButton = driver.findElement(By.xpath(rightArrowButtonXpath));
                    Wrappers.clickWebElement(driver, rightArrowButton);    
                }
            } catch (TimeoutException e) {
                // TODO: handle exception
                System.out.println("Log : Error in clicking right arrow button -> No right Arrow found");
            }

            String filmGenreSectionXpath = "//div[@id='dismissible' and descendant::span[@id='title' and contains(text(),'"+filmGenre+"')]]"; // film genre section(Eg: Top selling, Music, etc)
            WebElement filmGenreSectionElement = Wrappers.findWebElement(driver, By.xpath(filmGenreSectionXpath), 3, 1);

            String movieCardXpath = ".//*[@id='items']/ytd-grid-movie-renderer"; // child of filmGenreSectionElement // all movie cards in the genre
            List<WebElement> movieCardElements = filmGenreSectionElement.findElements(By.xpath(movieCardXpath));
            System.out.println("Log : Number of movie cards in "+filmGenre+" genre: "+movieCardElements.size());

            String contentratinglabelXpath = ".//*[contains(@class,'badge-style-type-simple')]//p"; // child of movieCardElements
            WebElement lastcontentratinglabelElement = movieCardElements.get(movieCardElements.size()-1).findElement(By.xpath(contentratinglabelXpath)); // last movie card in the genre
            sa.assertEquals(lastcontentratinglabelElement.getText(), "U/A", "Last movie in "+filmGenre+" genre is not U/A rated");

            String movieMetaData = ".//*[contains(@class,'grid-movie-renderer-metadata')]"; // child of movieCardElements (movie genre, year)
            if (wait.until(ExpectedConditions.visibilityOf(movieCardElements.get(movieCardElements.size()-1).findElement(By.xpath(movieMetaData))))!=null) {
                String movieMetaDataText = movieCardElements.get(movieCardElements.size()-1).findElement(By.xpath(movieMetaData)).getText().trim();
                System.out.println("Log : Metadata of last movie in "+filmGenre+" genre: "+movieMetaDataText);

                String movieGenre = movieMetaDataText.substring(0, movieMetaDataText.length()-7); // remove year from metadata
                System.out.println("movieGenre: "+movieGenre); // debugging purpose
                Set<String> genres = new HashSet<>(Arrays.asList("Comedy", "Romance", "Sport", "Documentary", "Indian cinema", "Horror", "Drama", "Action & adventure"));
                sa.assertTrue(genres.contains(movieGenre), "Metadata is not visible for last movie in "+filmGenre+" genre");
            }

            sa.assertAll();

        }   catch (Exception e) {
            // Log the exception or throw a custom exception
            System.out.println("Exception in TestCase02: " + e.getMessage());
            Assert.fail("Exception in TestCase02: " + e.getMessage());    
        }

        System.out.println("Test Case 02 : End");
    }

    @Test(enabled = true)
    public void testCase03() {
        System.out.println("Test Case 03 : Start");

        try {
            SoftAssert sa = new SoftAssert();
            
            driver.get("https://www.youtube.com/");
            System.out.println("Log : Opened youtube.com/");
            Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Current page is not youtube.com");
            System.out.println("Log : Opened youtube.com");

            //open side bar and click on Films
            String sideBarOption = "Music"; // change this to the section you want to test
            Wrappers.openSideBarToSelect(driver, sideBarOption);
            System.out.println("Log : Opened side bar and clicked on Music");

            //'s Biggest Hits ommited as quote is interfering with xpath
            String musicSection = "India"; // change this to the music section you want to test
            WebElement musicSectionElement = Wrappers.findWebElement(driver, By.xpath("//div[@id='dismissible' and descendant::span[@id='title' and contains(text(),'"+musicSection+"')]]"), 3, 1);

            String showMoreButtonXpath = ".//*[contains(@class,'button-container') and not(@hidden)]//button"; // child of musicSectionElement
            WebElement showMoreButtonElement = musicSectionElement.findElement(By.xpath(showMoreButtonXpath));

            Wrappers.clickWebElement(driver, showMoreButtonElement);
            System.out.println("Log : Clicked on Show More button");

            String musicPlayListCardsXpath = ".//div[@id='content']"; // child of musicSectionElement // all music play list cards in the section
            List<WebElement> musicPlayListCardElements = musicSectionElement.findElements(By.xpath(musicPlayListCardsXpath)); // all music play lsit cards in the section
            System.out.println("Log : Number of music cards in "+musicSection+" section: "+musicPlayListCardElements.size());

            String musicPlayListCardTitleXpath = ".//a/span[contains(@class,'yt-core-attributed-string')]"; // child of musicPlayListCardElements
            WebElement lastMusicPlayListCardTitleElements = musicPlayListCardElements.get(musicPlayListCardElements.size()-1).findElement(By.xpath(musicPlayListCardTitleXpath)); // last music play list card titles in the section
            System.out.println("Log : Title of last music card in "+musicSection+" section: "+lastMusicPlayListCardTitleElements.getText());

            String musicPlayListSongCountXpath = ".//*[contains(@class,'thumbnail-overlay')]//div[@class='badge-shape-wiz__text']";// child of musicPlayListCardElements
            WebElement lastMusicPlayListSongCountElement = musicPlayListCardElements.get(musicPlayListCardElements.size()-1).findElement(By.xpath(musicPlayListSongCountXpath)); // last music play list card song count in the section
            String songCountText = lastMusicPlayListSongCountElement.getText();
            System.out.println("Log : Song count of last music card in "+musicSection+" section: "+songCountText);

            int songCountInPlayList = Integer.parseInt(lastMusicPlayListSongCountElement.getText().replaceAll("[^0-9]", ""));
            sa.assertTrue(songCountInPlayList<=50, "Song count is not visible for last music card in "+musicSection+" section");

            sa.assertAll();
            
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception in TestCase03: " + e.getMessage());
        }

        System.out.println("Test Case 03 : End");
    }

    @Test(enabled = true)
    public void testCase04() {
        System.out.println("Test Case 04 : Start");

        try {
            SoftAssert sa = new SoftAssert();
            
            driver.get("https://www.youtube.com/");
            System.out.println("Log : Opened youtube.com/");
            Assert.assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Current page is not youtube.com");
            System.out.println("Log : Opened youtube.com");

            // signin
            // Wrappers.signIn(driver, "testemail@gmail.com", "t123es&2tPasWor@d");

            //open side bar and click on News
            String sideBarOption = "News"; // change this to the section you want to test
            Wrappers.openSideBarToSelect(driver, sideBarOption);
            System.out.println("Log : Opened side bar and clicked on News");

            String newsSection = "Latest news posts"; // change this to the section you want to test
            String newsSectionXpath = "//div[@id='dismissible' and descendant::span[@id='title' and contains(text(),'"+newsSection+"')]]"; // news section(Eg: Latest news posts, Top stories, etc)
            WebElement newsSectionElement = Wrappers.findWebElement(driver, By.xpath(newsSectionXpath), 3, 1);

            String showMoreButtonXpath = ".//*[contains(@class,'button-container') and not(@hidden)]//button"; // child of newsSectionElement
            WebElement showMoreButtonElement = newsSectionElement.findElement(By.xpath(showMoreButtonXpath));
            Wrappers.clickWebElement(driver, showMoreButtonElement);

            String newsPostXpath = ".//div[@id='content' and contains(@class,'ytd-rich-item-renderer')]"; // child of newsSectionElement // all news posts in the section
            List<WebElement> newsPostsElements = newsSectionElement.findElements(By.xpath(newsPostXpath));
            System.out.println("Log : Number of news posts in "+newsSection+" section: "+newsPostsElements.size());

            // String contentratinglabelXpath = ".//*[contains(@class,'badge-style-type-simple')]//p"; // child of movieCardElements
            // WebElement lastcontentratinglabelElement = movieCardElements.get(movieCardElements.size()-1).findElement(By.xpath(contentratinglabelXpath)); // last movie card in the genre
            // sa.assertEquals(lastcontentratinglabelElement.getText(), "U/A", "Last movie in "+filmGenre+" genre is not U/A rated");

            // String movieMetaData = ".//*[contains(@class,'grid-movie-renderer-metadata')]"; // child of movieCardElements (movie genre, year)
            // if (Wrappers.findWebElement(driver, By.xpath(movieMetaData), 3, 1)!=null) {
            //     String movieMetaDataText = movieCardElements.get(movieCardElements.size()-1).findElement(By.xpath(movieMetaData)).getText().trim();
            // }

            sa.assertAll();
        } catch (Exception e) {
            // TODO: handle exception
            Assert.fail("Exception in TestCase04: " + e.getMessage());
        }

        System.out.println("Test Case 04 : End");
    }
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--user-data-dir=c:/Users/debop/AppData/Local/Google/Chrome/User Data/"); // for login using existing user data(mandatory for TC04)

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }    
}