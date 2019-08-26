package Unit;

import com.oculow.COMPARISON;
import com.oculow.MANAGEMENT;
import com.oculow.Oculow;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;

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
    public void testManagementConfig(){
        /*
         * Verify that all management levels are going to set a correct int value, as expected in backend.
         */
        assertEquals(MANAGEMENT.MANUAL.ordinal(), 0);
        assertEquals(MANAGEMENT.ASSISTED.ordinal(), 1);
        assertEquals(MANAGEMENT.FORCE_NEW.ordinal(), 2);
        assertEquals(MANAGEMENT.FORCE_ALL.ordinal(), 3);
    }
    @Test
    public void testComparionConfig(){
        /*
         * Verify that all comparison logic are going to set a correct int value, as expected in backend.
         */
        assertEquals(COMPARISON.PIXEL_DIFF.ordinal(), 0);
        assertEquals(COMPARISON.IGNORE_AA.ordinal(), 1);
        assertEquals(COMPARISON.IGNORE_COLOR.ordinal(), 2);
        assertEquals(COMPARISON.DETECT_ERRORS.ordinal(), 3);
    }

    @Test
    public void testUploadImage() {
        // TODO This is not a unit test, its a api test!
        // TODO Parametrize image so it runs on any pc!
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