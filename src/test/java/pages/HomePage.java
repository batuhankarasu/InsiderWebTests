package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class HomePage extends BaseMethods {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);


    public HomePage(WebDriver driver) {
        super(driver);
    }

    @Description("Acsept all cookies btn clik")
    public void clickAcceptAllCookies() {
        try {
            performClickWithRetry("btn_accept_all_cookie",5);
            log.info("Click performed key: btn_accept_all_cookie");

        } catch (Exception e) {
            Assert.fail("Cookies btn can't clicked ERROR: " + e);
        }
    }


    @Description("Verifies that the user is on the homepage by checking specific elements.")
    public void verifyYouInHomePage() {
        try {
            waitElementPresent("announce_bar");
            waitElementPresent("slideBar_logo");
            log.info("You are in home page");

        } catch (Exception e) {
            Assert.fail("You are not in home page. ERROR : " + e);
        }
    }

    @Description("Checks that the Company dropdown is visible and hovers over it.")
    public void hoverCompanyDropdown(){
        try {
            String key = "dropdown_company";
            waitElementPresent(key);
            waitKeyElementVisible(key);
            hoverElement(key);
            log.info("Hover performed key: dropdown_company");

        } catch (Exception e) {
            Assert.fail("Can't hover key : dropdown_company element. ERROR : " + e);
        }
    }

    @Description("Clicks the Careers button from the dropdown on the homepage.")
    public void clickCareersBtn(){
        try {
            performClickWithRetry("btn_careers_link",5);
            log.info("Click performed key: btn_careers_link");

        } catch (Exception e) {
            Assert.fail("Career btn can't clicked ERROR: "+e);
        }

    }




}
