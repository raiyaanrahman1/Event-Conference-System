import static org.junit.Assert.*;

import Gateway.FileGateway;
import Gateway.IGateway;
import org.junit.*;

public class FileGatewayTests {
    @Test(timeout = 50)
    public void testWriteRead() {
        IGateway g = new FileGateway("fileName1.txt");
        String expected = "testing writing";
        g.write(expected);
        String actual = g.read();
        assertSame(expected, actual);
    }
}