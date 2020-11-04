package Gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

// Make Iterable or at least add method if you can continue reading (need to know where to stop)
// be able to read line by line, method hasNext() like iterable
public class FileGateway implements IGateway, Iterator<String> {

    private List<String> lines;
    private String fileName;

    public FileGateway(String fileName) {
        this.fileName = fileName;
    }

    public void read() {
        try {
            this.lines = Files.readAllLines(Paths.get(this.fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return this.lines.iterator().hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public String next() {
        return this.lines.iterator().next();
    }

    /**
     * Appends to a text file.
     * @param content The content that will get written to text file
     */
    @Override
    public void append(String content) {
        try {
            content += System.lineSeparator();
            Files.write(Paths.get(this.fileName), content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

