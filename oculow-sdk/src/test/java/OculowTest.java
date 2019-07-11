import org.apache.http.HttpResponse;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OculowTest {
    private Oculow oculow;

    @BeforeTest
    public void setup(){
        oculow = new Oculow();
        oculow.setApiKey("b5bd77ef-4da5-498e-92df-7d8cd2c9b355");
        oculow.setAppId("project-lince");
        oculow.setExecutionId("5b2eee27-fbaa-408b-aece-f30e7361f063");
    }
    @Test
    public void testUploadImage() {
        String out = oculow.uploadImage("C:\\Users\\Potosin\\Desktop\\test_images\\Capture2.PNG");
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