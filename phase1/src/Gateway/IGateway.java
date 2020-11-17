package Gateway;

import java.util.ArrayList;
import java.util.List;

/**
 *  Interface that deals with the file for Log in.
 */
public interface IGateway  {
    /**
     * Appends to a text file.
     *
     * @param content The content that will get written to text file
     */
    void append(List<String> content);

    /**
     * Reads the file
     *
     * @return an arraylist of lists of strings which represents each line of the file
     * with the user information.
     */
    ArrayList<List<String>> read();
}
