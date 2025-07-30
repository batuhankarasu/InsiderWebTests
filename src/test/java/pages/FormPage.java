package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class FormPage extends BaseMethods {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public FormPage(WebDriver driver) {
        super(driver);
    }

    @Description("Verifies the job application form page by checking the logo, apply button, and expected location text after switching to the new tab.")
    public void verifyFormPage() {
        try {

            String labelLogoImgKey = "img_label_logo";
            String applyButtonKey = "btn_apply_for_this_job";
            String jobLocationKey = "text_job_location";
            String expectedLocationText = "Istanbul, Turkiye";

            switchToNewTab();

            waitElementWithKeyVisible(labelLogoImgKey);
            waitElementWithKeyVisible(applyButtonKey);
            verifyElementWithKeyText(jobLocationKey, expectedLocationText);

            log.info("Form page verified successfully with logo, apply button, and job location text.");

        } catch (Exception e) {
            takeScreenshotForPage("FormPage_verifyFormPage");
            Assert.fail("Form page verification failed. ERROR: " + e.getMessage());
        }
    }
}
