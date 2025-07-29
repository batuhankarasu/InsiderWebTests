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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;


public class BaseMethods {

    protected ElementHelper elementHelper;

    public WebDriver driver;
    private static final Logger log = LogManager.getLogger(BaseMethods.class);


    public BaseMethods(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.elementHelper = new ElementHelper();
    }

    @Description("Waits until the element identified by the given key is visible on the page.")
    public void waitKeyElementVisible(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            log.info("Element visible key : " + key);
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
            log.info("Element is present in the DOM. Key: " + key);
        } catch (Exception e) {
            Assert.fail("Element presence wait failed. Key: " + key + " ERROR: " + e);
        }
    }

    @Description("Waits until the specified web element becomes visible on the page within the given timeout period.")
    public void waitWebElementVisibility(WebElement element, int timeoutSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
            wait.until(ExpectedConditions.visibilityOf(element));
            log.info("Element is present in the DOM. Key: " + element);

        } catch (Exception e) {
            Assert.fail("Element presence wait failed. Key: " + element + " ERROR: " + e);

        }

    }

    @Description("Waits until the element identified by the given key is clickable.")
    public void waitElementClickable(String key) {
        try {
            By locator = elementHelper.getElementInfoToBy(key);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            log.info("Element clickable key : " + key);
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
            log.info("Hover over the element succes key : " + key);
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

            log.info("Scrolls to the element with animation success key : " + key);
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
                waitKeyElementVisible(elementKey);
                waitElementClickable(elementKey);

                performClickAction(elementKey);

                log.info("Click performed successfully on attempt: " + (i + 1));
                return;

            } catch (Exception e) {
                if (i == maxRetries - 1) {

                    WebElement element = driver.findElement(elementHelper.getElementInfoToBy(elementKey));
                    JavascriptExecutor je = (JavascriptExecutor) driver;
                    je.executeScript("arguments[0].click();", element);
                    log.info("Click performed with JavaScript after retries");
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

    @Description("Selects an option from a dropdown element by visible text. Uses element key to locate dropdown and selects option containing the specified text.")
    public void selectDropdownOption(String key, String text) {
        try {
            String selectKey = key;
            String choiceText = text;

            WebElement dropdownElement = driver.findElement(elementHelper.getElementInfoToBy(selectKey));
            Thread.sleep(7000);
            Select select = new Select(dropdownElement);
            select.selectByContainsVisibleText(choiceText);

            log.info("Successfully selected '" + choiceText + "' from dropdown - key: " + selectKey);

        } catch (Exception e) {
            log.error("Failed to select '" + text + "' from dropdown '" + key + "': " + e.getMessage());
            Assert.fail("Dropdown selection failed. Key: " + key + ", Text: " + text + " ERROR: " + e.getMessage());
        }
    }

    @Description("Verifies that the element's visible text matches the expected text exactly.")
    public void verifyKeyElementText(String key, String expectedText) {
        try {
            By elementBy = elementHelper.getElementInfoToBy(key);
            WebElement element = driver.findElement(elementBy);
            String actualText = element.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Text verification failed for element '" + key + "'. Expected: '" + expectedText +
                            "' but actual: '" + actualText + "'");

            log.info("Successfully verified element text - Key: " + key + ", Expected: '" + expectedText +
                    "', Actual: '" + actualText + "'");

        } catch (Exception e) {
            log.error("Error during text verification for key '" + key + "': " + e.getMessage());
            Assert.fail("Cannot verify element text. Key: " + key + ", Expected: " + expectedText + " ERROR: " + e.getMessage());
        }
    }

    @Description("Verifies that the Web element's visible text matches the expected text exactly.")
    public void verifyWebElementText(WebElement element, String expectedText) {
        try {
            String actualText = element.getText().trim();

            Assert.assertEquals(actualText, expectedText,
                    "Text verification failed for element. Expected: '" + expectedText +
                            "' but actual: '" + actualText + "'");

            log.info("Successfully verified element text - Expected: '" + expectedText +
                    "', Actual: '" + actualText + "'");

        } catch (Exception e) {
            log.error("Error during text verification: " + e.getMessage());
            Assert.fail("Cannot verify element text. Expected: " + expectedText + " ERROR: " + e.getMessage());
        }
    }

}

