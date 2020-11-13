package Gateway;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IGateway  {
    void append(List<String> content);
    ArrayList<List<String>> read();
}
