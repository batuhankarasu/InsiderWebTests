package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class QaPage extends BaseMethods {
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public QaPage(WebDriver driver) {
        super(driver);
    }

    @Description("Clicks the 'Quality Assurance' team button on the Qa Assurange page.")
    public void clickSeeAlQaJobsBtn() {
        try {
            performClickWithRetry("btn_see_all_qa_jobs", 5);
            log.info("Successfully clicked the 'Quality Assurance' team button (key: btn_see_all_qa_jobs).");
        } catch (Exception e) {
            Assert.fail("Failed to click the 'Quality Assurance' team button (key: btn_see_all_qa_jobs) after all attempts. ERROR: " + e.getMessage());
        }
    }

    @Description("Verified that the 'Qa Assurange' text is present on the Qa Assurange page.")
    public void verifyQaAssurengePage(){
        try {
            String key = "title_qa_assurance";

            waitElementPresent(key);
            waitKeyElementVisible(key);

            By locetionsBy = elementHelper.getElementInfoToBy(key);
            WebElement locetions = driver.findElement(locetionsBy);
            Assert.assertEquals(locetions.getText(), "Quality Assurance",
                    "Expected: 'Our Locations' but actual: '" + locetions.getText() + "' text is incorrect");

            log.info("Verify element. key: btn_careers_link");

        } catch (Exception e) {
            Assert.fail("Can't verify LOcations ERROR : " + e);
        }
    }


}
