import com.oculow.COMPARISON;
import com.oculow.MANAGEMENT;
import com.oculow.Oculow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OculowSeleniumTest {
    private Oculow oculow;
    WebDriver driver;
    @BeforeTest
    public void setup(){
        oculow = new Oculow();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        oculow.setBaselineManagement(MANAGEMENT.ASSISTED);
        oculow.setComparison(COMPARISON.IGNORE_AA);
    }
    @Test
    public void testCaptureScreen() {
        // launch Fire fox and direct it to the Base URL
        driver.get("https://www.oculow.com/");
        // get the actual value of the title
        String actualTitle = driver.getTitle();
        assert actualTitle.contentEquals("Oculow");

        oculow.captureScreen(driver, "home page");

        WebElement _el = driver.findElement(By.id("txtEmail"));
//        _el.sendKeys("diego.ferrand@abstracta.com.uy");
        _el = driver.findElement(By.id("txtEmail"));

        oculow.captureScreen(driver, "form submit");

        _el.click();

        driver.get("https://www.oculow.com/dashboard/index.html");

        /*
        #TODO not working on website
        driver.findElement(By.id("btn_signin")).click();
        oculow.captureScreen(driver, "login");
        driver.findElement(By.id("username")).sendKeys("test");
        driver.findElement(By.id("password")).sendKeys("test");

        oculow.captureScreen(driver, "login complete");
        driver.findElement(By.id("btn_signin")).click();
        */

        oculow.captureScreen(driver, "dashboard index");



    }
    @AfterTest
    public void teardown(){
        driver.close();
        oculow.dispose();
    }
}