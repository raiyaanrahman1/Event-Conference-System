package Gateway;

import java.util.Iterator;

public interface IGateway2 extends Iterator<String> {
    boolean openForRead();
    boolean closeForRead();
    boolean openForWrite();
    boolean closeForWrite();
    void write(String str);
}

