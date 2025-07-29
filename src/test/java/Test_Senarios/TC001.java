package Test_Senarios;

import driver.Driver;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.HomePage;
import pages.OpenPossitionsPage;
import pages.QaPage;

public class TC001 extends Driver {

    @Test(priority = 0, description = "Critical: Visit Insider homepage and verify the page opens correctly, then navigate to Careers page and check important sections.")
    @Description("1. Visit https://useinsider.com/ and verify Insider homepage is displayed.\n"
                + "2. Click on 'Company' menu, then select 'Careers'. Verify Careers page opens with Locations, Teams, and Life at Insider blocks visible.")
    public void testInsiderHomeAndCareersPage() {

        HomePage homePage = new HomePage(driver);
        CareerPage careerPage = new CareerPage(driver);
        QaPage qaPage = new QaPage(driver);
        OpenPossitionsPage openPossitionsPage = new OpenPossitionsPage(driver);

        homePage.clickAcceptAllCookies();
        homePage.verifyYouInHomePage();
        homePage.hoverCompanyDropdown();
        homePage.clickCareersBtn();

        careerPage.verifyLocationsInCareers();
        careerPage.verifyTeamsnCareers();
        careerPage.verifyLifeAtInsider();
        careerPage.clickSeeAllTeamsBtn();
        careerPage.clickQATeamSellection();

        qaPage.verifyQaAssurengePage();
        qaPage.clickSeeAlQaJobsBtn();

        openPossitionsPage.verifyOpenPossitionsPace();
        openPossitionsPage.selectLocationIstanbulTurkey();
        openPossitionsPage.sellectDepartmentQualityAssurance();
        openPossitionsPage.checkJobListItemsDepartment();
        openPossitionsPage.checkJobListItemsLocation();


    }
}
