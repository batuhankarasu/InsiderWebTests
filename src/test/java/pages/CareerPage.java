package pages;

import base.BaseMethods;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

public class CareerPage extends BaseMethods {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public CareerPage(WebDriver driver) {
        super(driver);
    }

    @Description("Verified that the 'Our Locations' section is present on the Careers page.")
    public void verifyLocationsInCareers(){
        try {
            String key = "conteiner_locetion";
            String titleKey = "title_locations";
            String expectedText = "Our Locations";

            waitElementPresent(key);
            waitElementWithKeyVisible(key);
            verifyElementWithKeyText(titleKey,expectedText);

            log.info("Verify element. key: btn_careers_link");

        } catch (Exception e) {
            Assert.fail("Can't verify Locations ERROR : " + e);
        }
    }

    @Description("Verified that the 'Find your calling' section is present on the Careers page.")
    public void verifyTeamsnCareers(){
        try {
            String key = "conteiner_teams";
            String titleKey = "title_teams";
            String expectedText = "Find your calling";

            waitElementPresent(key);
            waitElementWithKeyVisible(key);
            verifyElementWithKeyText(titleKey,expectedText);

            log.info("Verify element. key: conteiner_teams");

        } catch (Exception e) {
            Assert.fail("Can't verify Teams ERROR : " + e);
        }
    }

    @Description("Verified that the 'Life at Insider' section is present on the Careers page.")
    public void verifyLifeAtInsider(){
        try {
            String key = "swiper_lifeAtInsider";
            String titleKey = "title_lifeAtInsider";
            String expectedText = "Life at Insider";

            waitElementPresent(key);
            waitElementWithKeyVisible(key);
            verifyElementWithKeyText(titleKey,expectedText);

            log.info("Verify element. key: conteiner_teams");

        } catch (Exception e) {
            Assert.fail("Can't verify Teams ERROR : " + e);
        }
    }

    @Description("Clicks the 'See all teams' button on the Careers page.")
    public void clickSeeAllTeamsBtn() {
        try {

            performClickWithRetry("btn_see_all_teams",5);
            log.info("Clicked See All Teams Btn");

        } catch (Exception e) {
            Assert.fail("See all teams btn can't clicked after all attempts ERROR: " + e);
        }
    }

    @Description("Clicks the Quality Assurance team selection button on the Careers page.")
    public void clickQATeamSellection(){
        try {
            performClickWithRetry("btn_team_quality_assurance",5);
            log.info("Clicked QA Team Sellection");

        } catch (Exception e) {
            Assert.fail("Quality Assurance team button can't be clicked after all attempts ERROR: " + e);
        }
    }
}
