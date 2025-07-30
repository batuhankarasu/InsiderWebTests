package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class OpenPossitionsPage extends BaseMethods {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public OpenPossitionsPage(WebDriver driver) {
        super(driver);
    }

    @Description("Verifies that the user is on the open possitions page by checking page title.")
    public void verifyOpenPossitionsPace(){
        try {

            String key = "title_open_possitions_page";
            String expectedText = "All open positions";

            waitElementPresent(key);
            waitElementWithKeyVisible(key);
            verifyElementWithKeyText(key,expectedText);

            log.info("You are in open possitions page");

        } catch (Exception e) {
            takeScreenshotForPage("OpenPossitionsPage_verifyOpenPossitionsPace");
            Assert.fail("You are not in open possitions page. ERROR : " + e);
        }
    }

    @Description("Selects Istanbul, Turkiye from location dropdown.")
    public void selectLocationIstanbulTurkey(){
        try {
            String sellectLocations = "sellect_location";
            String locationText ="Istanbul, Turkiye";

            selectDropdownOption(sellectLocations,locationText);

            log.info("Successfully selected location: " + locationText);
        } catch (Exception e) {
            takeScreenshotForPage("OpenPossitionsPage_selectLocationIstanbulTurkey");
            Assert.fail("Cannot select Istanbul location. ERROR: " + e.getMessage());
        }
    }

    @Description("Verifies that all job list items have the expected department (Quality Assurance).")
    public void checkJobListItemsDepartment(){
        try {
            String departmentKey = "text_jobList_item_department_name";
            String expectedText = "Quality Assurance";
            String joblistItemKey = "jobList_item";

            log.info("Starting department verification for all job list items...");
            Thread.sleep(1000);
            By jobElementsBy = elementHelper.getElementInfoToBy(joblistItemKey);
            List<WebElement> jobElements = new ArrayList<>();
            for (WebElement element : driver.findElements(jobElementsBy)){
                if (element.isDisplayed()){
                    jobElements.add(element);
                }
            }

            if (jobElements.isEmpty()) {
                takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsDepartment");
                Assert.fail("No job items found for department verification. Key: " + joblistItemKey);
            }

            log.info("Found " + jobElements.size() + " job items to verify departments");

            for (int i = 0; i < jobElements.size(); i++) {
                WebElement jobElement = jobElements.get(i);
                try {
                    By departmentElementBY = elementHelper.getElementInfoToBy(departmentKey);
                    WebElement departmentElement = jobElement.findElement(departmentElementBY);

                    waitWebElementVisibility(departmentElement,20);

                    verifyWebElementText(departmentElement,expectedText);

                    log.info("✓ Job item " + (i + 1) + " department verified: '" + expectedText + "'");

                } catch (Exception e) {
                    takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsDepartment");
                    Assert.fail("Department verification failed for job item " + (i + 1) + ". ERROR: " + e.getMessage());
                }

            }

            log.info("All " + jobElements.size() + " job items department verification completed successfully!");

        } catch (Exception e) {
            takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsDepartment");
            Assert.fail("Cannot verify job list departments. ERROR: " + e.getMessage());
        }
    }

    @Description("Verifies that all job list items have the expected location (Istanbul, Turkiye).")
    public void checkJobListItemsLocation(){
        try {
            String locationKey = "text_jobList_item_location_name";
            String expectedText = "Istanbul, Turkiye";
            String joblistItemKey = "jobList_item";

            log.info("Starting location verification for all job list items...");

            By elementBy = elementHelper.getElementInfoToBy(joblistItemKey);
            List<WebElement>  jobElements = driver.findElements(elementBy);

            if (jobElements.isEmpty()) {
                takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsLocation");
                Assert.fail("No job items found for location verification. Key: " + joblistItemKey);
            }

            log.info("Found " + jobElements.size() + " job items to verify locations");

            for (int i = 0; i < jobElements.size(); i++) {
                WebElement jobElement = jobElements.get(i);
                try {
                    By locationElementBY = elementHelper.getElementInfoToBy(locationKey);
                    WebElement locationElement = jobElement.findElement(locationElementBY);

                    waitWebElementVisibility(locationElement,20);

                    verifyWebElementText(locationElement,expectedText);

                    log.info("✓ Job item " + (i + 1) + " location verified: '" + expectedText + "'");

                } catch (Exception e) {
                    takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsLocation");
                    Assert.fail("Location verification failed for job item " + (i + 1) + ". ERROR: " + e.getMessage());
                }
            }

            log.info("All " + jobElements.size() + " job items location verification completed successfully!");

        } catch (Exception e) {
            takeScreenshotForPage("OpenPossitionsPage_checkJobListItemsLocation");
            Assert.fail("Cannot verify job list locations. ERROR: " + e.getMessage());

        }
    }


    @Description("Hovers over the first job item and clicks its 'View Role' button.")
    public void clickFirstJobViewRoleBtn() {
        int jobIndex = 0;

        try {
            String jobListItemKey = "jobList_item";
            String viewRoleBtnKey = "jobList_item_viewRole_btn";

            log.info("Attempting to view job role at index: " + jobIndex);

            By jobElementBy = elementHelper.getElementInfoToBy(jobListItemKey);
            By viewRoleBtnBy = elementHelper.getElementInfoToBy(viewRoleBtnKey);

            List<WebElement> jobElements = driver.findElements(jobElementBy);
            List<WebElement> viewRoleBtns = driver.findElements(viewRoleBtnBy);

            if (jobElements.isEmpty() || jobIndex >= jobElements.size()) {
                takeScreenshotForPage("OpenPossitionsPage_clickFirstJobViewRoleBtn");
                Assert.fail("No job element found at index " + jobIndex);
            }

            if (viewRoleBtns.isEmpty() || jobIndex >= viewRoleBtns.size()) {
                takeScreenshotForPage("OpenPossitionsPage_clickFirstJobViewRoleBtn");
                Assert.fail("No 'View Role' button found at index " + jobIndex);
            }

            WebElement jobElement = jobElements.get(jobIndex);
            WebElement viewRoleBtn = viewRoleBtns.get(jobIndex);

            scrollWebElementIntoView(jobElement);
            log.info("Hovering over job element at index: " + jobIndex);
            hoverWebElement(jobElement);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(viewRoleBtn));

            viewRoleBtn.click();

            log.info("Successfully clicked 'View Role' button at index: " + jobIndex);

        } catch (Exception e) {
            takeScreenshotForPage("OpenPossitionsPage_clickFirstJobViewRoleBtn");
            Assert.fail("Cannot click 'View Role' button at index " + jobIndex + ". ERROR: " + e.getMessage());
        }
    }
}
