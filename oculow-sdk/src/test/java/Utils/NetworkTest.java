package Utils;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

class NetworkTest{
    private Network networkClass = new Network();
    @BeforeTest
    void setUp() {
        networkClass = new Network();
    }
    @AfterTest
    void tearDown() {

        networkClass = null;
    }
    @Test
    void testUploadFile() {
        
    }
}
