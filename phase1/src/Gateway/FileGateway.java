package Gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

// Make Iterable or at least add method if you can continue reading (need to know where to stop)
// be able to read line by line, method hasNext() like iterable
public class FileGateway implements IGateway, Iterator<List<String>> {

    private List<String> lines;
    private String fileName;
    private ArrayList<List<String>> UserInfo = new ArrayList<>();

    public FileGateway(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<List<String>> read() {
        try {
            this.lines = Files.readAllLines(Paths.get(this.fileName));
//            ArrayList<List<String>> UserInfo = new ArrayList<>();
            for (String l: lines) {
                UserInfo.add(Arrays.asList(l.split(" " )));
            }
            return UserInfo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
    public List<String> next() {
        return this.UserInfo.iterator().next();
    }

    /**
     * Appends to a text file.
     * @param content The content that will get written to text file
     */
    @Override
    public void append(List<String> content) {
        try {
            String line = new String();
            for (String s: content){
                line += s + " "  ;
            }
            line += System.lineSeparator();
            Files.write(Paths.get(this.fileName), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

