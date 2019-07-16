import com.oculow.Oculow;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
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
        // Set controlled window size
        Dimension d = new Dimension(800,480);
        driver.manage().window().setSize(d);
    }
    @Test
    public void testCaptureScreen() {
        // launch Fire fox and direct it to the Base URL
        driver.get("https://www.oculow.com/");
        oculow.captureScreen(driver, "created");

        // get the actual value of the title
        String actualTitle = driver.getTitle();

        assert actualTitle.contentEquals("Oculow");

    }
    @AfterTest
    public void teardown(){
        driver.close();
        oculow.dispose();
    }
}