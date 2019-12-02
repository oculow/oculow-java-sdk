//import com.oculow.COMPARISON;
//import com.oculow.MANAGEMENT;
//import com.oculow.Oculow;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//public class OculowSeleniumTest {
//    private Oculow oculow;
//    private WebDriver driver;
//    @BeforeTest
//    private void setup(){
//        oculow = new Oculow();
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        oculow.setBaselineManagement(MANAGEMENT.ASSISTED);
//        oculow.setComparison(COMPARISON.IGNORE_AA);
//        oculow.setApiKey("10eVwxGqZMkJILKrlPnL8RmHZjAhDiNy","qzSqIAHye2MJvrt37VxzBsv4ADwO9Q7G");
//        oculow.setAppId("oculow");
//    }
//
//    @Test
//    private void testCaptureScreen() {
//        // launch Fire fox and direct it to the Base URL
//        driver.get("https://www.oculow.com/");
//        // get the actual value of the title
//        String actualTitle = driver.getTitle();
//        assert actualTitle.contentEquals("Oculow");
//
//        oculow.captureScreen(driver, "home page low res");
//
//        WebElement _el = driver.findElement(By.id("landing-email"));
//
//        _el.click();
//
//        driver.get("https://www.oculow.com/dashboard/index.html");
//
//
//        oculow.captureScreen(driver, "dashboard index");
//    }
//
//    @AfterTest
//    private void teardown(){
//        driver.close();
//        oculow.dispose();
//    }
//}