package pages;

import Element_Helper.ElementHelper;
import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;

public class HomePage extends BaseMethods {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);


    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Description("Waits until the element identified by the given key is visible on the page.")
    public void verifyYouInHomePage() {
        try {
            waitElementPresent("announce_bar");
            waitElementPresent("slideBar_logo");
            log.info("You are in home page");

        } catch (Exception e) {
            Assert.fail("You are not in home page. ERROR : " + e);
        }
    }


}
