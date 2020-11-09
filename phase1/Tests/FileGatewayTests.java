import static org.junit.Assert.*;

import Gateway.FileGateway;
import Gateway.IGateway;
import org.junit.*;

import java.util.Arrays;
import java.util.List;

public class FileGatewayTests {
    @Test(timeout = 50)
    public void testAppendRead() {
        IGateway g = new FileGateway("phase1/src/Controller/LogInInformation.txt");
        g.read();
        List<String> expected = Arrays.asList("n", "p", "A");
        g.append(expected);
        while (g.hasNext()) {
            List<String> actual = g.next();
            assertSame(expected, actual);
        }
    }

    @Test(timeout = 50)
    public void testHasNext() {
        IGateway g1 = new FileGateway("phase1/src/Controller/LogInInformation.txt");
        g1.read();
        List<String> expected = Arrays.asList("n1", "p1", "A");
        g1.append(expected);
        assertSame(g1.hasNext(), false);
    }

    @Test(timeout = 50)
    public void testNext() {
        IGateway g2 = new FileGateway("phase1/src/Controller/LogInInformation.txt");
        g2.read();
        List<String> expected = Arrays.asList("n2", "p2", "O");
        g2.append(expected);
        g2.read();
        assertSame(expected, g2.next());
    }
}