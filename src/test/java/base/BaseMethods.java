package base;

import Element_Helper.ElementHelper;
import jdk.jfr.Description;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;


public class BaseMethods {

    protected ElementHelper elementHelper;

    public WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseMethods.class);


    public BaseMethods(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.elementHelper = new ElementHelper();
    }

    @Description("Waits until the element identified by the given key is visible on the page.")
    public void waitElementVisible(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            logger.info("Element visible key : " + key);
        } catch (Exception e) {
            Assert.fail("Element Waits visible error,  key : " +key + " ERROR : " +e );

        }
    }
    @Description("Waits until the element identified by the given key is present in the DOM (regardless of visibility).")
    public void waitElementPresent(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            logger.info("Element is present in the DOM. Key: " + key);
        } catch (Exception e) {
            Assert.fail("Element presence wait failed. Key: " + key + " ERROR: " + e);
        }
    }

    @Description("Waits until the element identified by the given key is clickable.")
    public void waitElementClickable(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            logger.info("Element clickable key : " + key);
        } catch (Exception e) {
            Assert.fail("Element Waits clickable error,  key : " +key + " ERROR : " +e );
        }
    }

    @Description("Hover over the element specified by the locator key.")
    public void hoverElement(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebElement element = driver.findElement(locator);
            Actions action = new Actions(driver);
            action.moveToElement(element).perform();
            logger.info("Hover over the element succes key : " + key);
        } catch (Exception e) {
            Assert.fail("Element Hover error,  key : " +key + " ERROR : " +e );
        }
    }

    @Description("Scrolls to the element and waits for scroll animation to complete")
    public void scrollElement(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement webElement = driver.findElement(locator);

            JavascriptExecutor je = (JavascriptExecutor) driver;

            // Scroll öncesi pozisyon
            Long scrollPositionBefore = (Long) je.executeScript("return window.pageYOffset;");

            // Scroll işlemi
            je.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center', behavior: 'smooth'});", webElement);

            // Scroll animasyonunun bitmesini bekle
            wait.until(driver -> {
                Long currentPosition = (Long) je.executeScript("return window.pageYOffset;");
                try {
                    Thread.sleep(1000); // Kısa bekleme
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Long newPosition = (Long) je.executeScript("return window.pageYOffset;");
                return currentPosition.equals(newPosition); // Pozisyon sabitlendi mi?
            });

            // Element kontrolları
            wait.until(ExpectedConditions.visibilityOf(webElement));
            wait.until(ExpectedConditions.elementToBeClickable(webElement));

            logger.info("Scrolls to the element with animation success key : " + key);
        } catch (Exception e) {
            Assert.fail("Error while scrolling to element: " + key + " ERROR : " + e);
        }
    }

    @Description("Performs click action with retry mechanism and JavaScript fallback.")
    public void performClickWithRetry(String elementKey,int maxRetries) throws InterruptedException {
        for (int i = 0; i < maxRetries; i++) {
            try {
                waitElementPresent(elementKey);
                scrollElement(elementKey);
                Thread.sleep(500);
                waitElementVisible(elementKey);
                waitElementClickable(elementKey);

                performClickAction(elementKey);

                logger.info("Click performed successfully on attempt: " + (i + 1));
                return;

            } catch (Exception e) {
                if (i == maxRetries - 1) {

                    WebElement element = driver.findElement(elementHelper.getElementInfoToBy(elementKey));
                    JavascriptExecutor je = (JavascriptExecutor) driver;
                    je.executeScript("arguments[0].click();", element);
                    logger.info("Click performed with JavaScript after retries");
                    return;

                } else {
                    Thread.sleep(1000);
                }
            }
        }
    }

    @Description("Performs the actual click action.")
    private void performClickAction(String elementKey){
        By elementBy = elementHelper.getElementInfoToBy(elementKey);
        WebElement element = driver.findElement(elementBy);
        element.click();
    }
}
