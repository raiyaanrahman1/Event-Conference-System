package Gateway;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileGateway implements IGateway {

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
                String[] line = l.split(" ");
                if (line.length == 3) {
                    userInfo.add(Arrays.asList(line));
                }
            }
            return userInfo;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
            Files.write(Paths.get(this.fileName), line.getBytes(),
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

