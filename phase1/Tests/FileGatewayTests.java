import static org.junit.Assert.*;

import Gateway.FileGateway;
import Gateway.IGateway;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileGatewayTests {

    @Test()
    public void testNext() throws IOException {
        Files.deleteIfExists(Paths.get("phase1/src/Controller/test.txt"));
        IGateway g = new FileGateway("phase1/src/Controller/test.txt");
        List<String> expected1 = Arrays.asList("n1", "p1", "A");
        List<String> expected2 = Arrays.asList("n2", "p2", "O");
        ArrayList<List<String>> expectedRead = new ArrayList<>();
        expectedRead.add(expected1);
        expectedRead.add(expected2);
        g.append(expected1);
        g.append(expected2);
        ArrayList<List<String>> actualRead = g.read();
        assertEquals(expectedRead, actualRead);
    }
}