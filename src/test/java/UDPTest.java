import org.junit.Assert;
import org.junit.Test;

public class UDPTest {

    @Test
    public void testReceivedMessage() {
            UDPClient udpClient = new UDPClient("qwe");
            Assert.assertEquals("QWE",udpClient.getMessage());
    }
}
