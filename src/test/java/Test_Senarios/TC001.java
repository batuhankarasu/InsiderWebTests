package Test_Senarios;

import driver.Driver;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.*;

public class TC001 extends Driver {

    @Test(priority = 0, description = "High: Verify Insider homepage, navigate to Careers, and complete QA job application flow.")
    @Description("""
        1. Visit https://useinsider.com and confirm homepage is displayed.
        2. Click on 'Company' → 'Careers' and verify Careers page.
        3. Confirm visibility of Locations, Teams, and Life at Insider sections.
        4. Click 'See All Teams' and select 'Quality Assurance'.
        5. On QA page, verify title and click 'See All QA Jobs'.
        6. In Open Positions page:
           - Verify page is loaded,
           - Select location: Istanbul, Turkey,
           - Select department: Quality Assurance,
           - Confirm all jobs match selected filters.
        7. Click the first 'View Role' button and verify the form page.
    """)
    public void testInsiderHomeAndCareersPage() {

        HomePage homePage = new HomePage(driver);
        CareerPage careerPage = new CareerPage(driver);
        QaPage qaPage = new QaPage(driver);
        OpenPossitionsPage openPositionsPage = new OpenPossitionsPage(driver);
        FormPage formPage = new FormPage(driver);

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

        openPositionsPage.verifyOpenPossitionsPace();
        openPositionsPage.selectLocationIstanbulTurkey();
        openPositionsPage.checkJobListItemsDepartment();
        openPositionsPage.checkJobListItemsLocation();
        openPositionsPage.clickFirstJobViewRoleBtn();

        formPage.verifyFormPage();
    }
}
