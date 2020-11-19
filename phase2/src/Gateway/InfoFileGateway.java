package Gateway;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A file gateway for data files.
 */
public class InfoFileGateway implements IGateway2 {
    private final File file;
    private Scanner reader;
    private PrintWriter writer;
    private boolean readable;
    private boolean writeable;

    /**
     * Constructs a new file gateway.
     * @param fileName  the path of the file we want to access
     */
    public InfoFileGateway(String fileName) {
        this.file = new File(fileName);
        this.readable = false;
        this.writeable = false;
    }

    /**
     * Returns true only if this file opens for reading successfully.
     * If it is already open, it cannot open again, so it returns false.
     *
     * @return  true only if this file opens for reading successfully
     */
    @Override
    public boolean openForRead() {
        if (!file.exists()) {
            return false;
        } else {
            if (!readable && !writeable) {
                try {
                    this.reader = new Scanner(file);
                    readable = true;
                    return true;
                } catch (IOException e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Returns true only if this file closes for reading successfully.
     * If it is already closed, it cannot be closed again, so it returns false.
     *
     * @return  true only if this file closes for reading successfully
     */
    @Override
    public boolean closeForRead() {
        if (readable) {
            this.reader.close();
            readable = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true only if this file opens for writing successfully.
     * If it is already open, it cannot open again, so it returns false.
     *
     * @return  true only if this file opens for writing successfully
     */
    @Override
    public boolean openForWrite() {
        if (!readable && !writeable) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                writer = new PrintWriter(fileWriter);
                writeable = true;
                return true;
            } catch (IOException e){
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Returns true only if this file closes for writing successfully.
     * If it is already closed, it cannot be closed again, so it returns false.
     * It saves all the data in the buffer into the file.
     *
     * @return  true only if this file closes for writing successfully
     */
    @Override
    public boolean closeForWrite() {
        if (writeable) {
            this.writer.close();
            writeable = false;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Writes a string to the file buffer iff the buffer is open.
     * @param str  the string to be sent to gateway
     */
    @Override
    public void write(String str) {
        if (writeable) {
            writer.println(str);
        }
    }

    /**
     * Returns true iff the reading buffer has more strings to read from.
     * @return  true iff the reading buffer has more strings to read from
     */
    @Override
    public boolean hasNext() {
        if (readable) {
            return reader.hasNext();
        } else {
            return false;
        }
    }

    /**
     * Returns the next string contained in the reading buffer.
     * @return  the next string contained in the reading buffer.
     * @throws NoSuchElementException  iff the buffer is empty
     * @throws IllegalStateException  iff the buffer is closed.
     */
    @Override
    public String next() throws NoSuchElementException, IllegalStateException {
        if (readable) {
            return reader.nextLine();
        } else {
            throw new IllegalStateException();
        }
    }
}
