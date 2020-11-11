package Gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileGateway implements IGateway, Iterator<List<String>> {

    private final String fileName;
    private ArrayList<List<String>> userInfo;

    public FileGateway(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Reads the file
     *
     * @return an arraylist of lists of strings which represents each line of the file
     * with the user information.
     */
    public ArrayList<List<String>> read() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(this.fileName));
            userInfo = new ArrayList<>();
            for (String l : lines) {
                userInfo.add(Arrays.asList(l.split(" ")));
            }
            return userInfo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns a boolean if there exists a next element in the iteration.
     *
     * @return whether there is a next element in the iteration
     */
    @Override
    public boolean hasNext() {
        return this.userInfo.iterator().hasNext();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public List<String> next() {
        return this.userInfo.iterator().next();
    }

    /**
     * Appends to a text file.
     *
     * @param content The content that will get written to text file
     */
    @Override
    public void append(List<String> content) {
        try {
            String line = String.join(" ", content).concat(System.lineSeparator());
            Files.write(Paths.get(this.fileName), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

