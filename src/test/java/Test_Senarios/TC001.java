package Test_Senarios;

import driver.Driver;
import jdk.jfr.Description;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;

public class TC001 extends Driver {

    @Test(priority = 0, description = "Critical: Visit Insider homepage and verify the page opens correctly, then navigate to Careers page and check important sections.")
    @Description("1. Visit https://useinsider.com/ and verify Insider homepage is displayed.\n"
                + "2. Click on 'Company' menu, then select 'Careers'. Verify Careers page opens with Locations, Teams, and Life at Insider blocks visible.")
    public void testInsiderHomeAndCareersPage() {

        HomePage homePage = new HomePage(driver);


        homePage.verifyYouInHomePage();

    }
}
