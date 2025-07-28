package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class QaPage extends BaseMethods {
    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public QaPage(WebDriver driver) {
        super(driver);
    }

    @Description("Clicks the 'Quality Assurance' team button on the teams page.")
    public void clickSeeAlQaJobsBtn() {
        try {
            performClickWithRetry("btn_see_all_qa_jobs", 5);
            log.info("Successfully clicked the 'Quality Assurance' team button (key: btn_see_all_qa_jobs).");
        } catch (Exception e) {
            Assert.fail("Failed to click the 'Quality Assurance' team button (key: btn_see_all_qa_jobs) after all attempts. ERROR: " + e.getMessage());
        }
    }


}
