package Gateway;

import java.util.Iterator;

/**
 * An gateway interface that communicates string by string.
 */
public interface IGateway2 extends Iterator<String> {

    /**
     * Opens the gateway for reading.
     * @return  true if the gateway has opened successfully
     */
    boolean openForRead();

    /**
     * Closes the gateway for reading.
     * @return  true if the gateway has closed successfully
     */
    boolean closeForRead();

    /**
     * Opens the gateway for writing.
     * @return  true if the gateway has opened successfully
     */
    boolean openForWrite();

    /**
     * Closes the gateway for writing.
     * @return  true if the gateway has closed successfully
     */
    boolean closeForWrite();

    /**
     * Writes to the gateway, only possible if gateway is open for write.
     * @param str  the string to be sent to gateway
     */
    void write(String str);
}

