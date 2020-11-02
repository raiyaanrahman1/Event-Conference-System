package Gateway;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileGateway implements IGateway{

    private String filename;

    public FileGateway(String fileName){
        this.filename = fileName;
    }

    @Override
    public String read() {
        String text = "";
        try {
            text = new String(Files.readAllBytes(Paths.get(this.filename)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public void write(String content) {
        try {
            Files.write(Paths.get(this.filename), content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
