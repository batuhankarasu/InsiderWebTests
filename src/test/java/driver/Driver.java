package driver;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;


public class Driver {

    protected WebDriver driver;

    @BeforeMethod
    @Parameters("baseUrl")
    public void setUp(@Optional("https://useinsider.com/")String baseUrl) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-geolocation");
        options.addArguments("--disable-save-password-bubble");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(baseUrl);

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
