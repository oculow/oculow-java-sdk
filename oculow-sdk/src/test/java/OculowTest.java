import com.oculow.Oculow;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OculowTest {
    private Oculow oculow;

    @BeforeTest
    public void setup(){
        oculow = new Oculow();
        oculow.setApiKey("bf4df221-ba67-43af-b66d-55d3cabe9cb5");
        oculow.setAppId("project-lince");
        oculow.setExecutionId("5b2eee27-fbaa-408b-aece-f30e7361f064");
    }
    @Test
    public void testUploadImage() {
        String out = oculow.uploadImage("C:\\Users\\potos\\Desktop\\error_example.PNG");
        String assignedOut = oculow.getExecutionID();
        assertNotEquals(out,"" );
        assertNotEquals(assignedOut,"");
        assertEquals(out, assignedOut);
    }
    @Test
    public void testGetResult() {
        oculow.dispose();

    }
    @AfterTest
    public void teardown(){
        oculow = null;
    }

}