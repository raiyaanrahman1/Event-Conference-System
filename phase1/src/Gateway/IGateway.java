package Gateway;

import java.util.Iterator;

public interface IGateway extends Iterator<String> {
    void append(String content);
    void read();
}
