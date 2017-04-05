import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class UDPTest {

    @Test
    public void testReceivedMessage() {
        try {
            UDPClient udpClient = new UDPClient("qwe");
            Assert.assertEquals("QWE",udpClient.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
