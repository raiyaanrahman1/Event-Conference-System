package Gateway;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class MessageFileGateway implements IGateway2 {
    private File file;
    private Scanner reader;
    private PrintWriter writer;
    private boolean readable;
    private boolean writeable;

    public MessageFileGateway (String fileName) {
        this.file = new File(fileName);
        this.readable = false;
        this.writeable = false;
    }

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

    @Override
    public void write(String str) {
        if (writeable) {
            writer.println(str);
        }
    }

    @Override
    public boolean hasNext() {
        if (readable) {
            return reader.hasNext();
        } else {
            return false;
        }
    }

    @Override
    public String next() throws NoSuchElementException, IllegalStateException {
        if (readable) {
            return reader.nextLine();
        } else {
            throw new IllegalStateException();
        }
    }
}
