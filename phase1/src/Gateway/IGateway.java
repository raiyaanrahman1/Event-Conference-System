package Gateway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IGateway extends Iterator<List<String>> {
    void append(List<String> content);
    ArrayList<List<String>> read();
}
