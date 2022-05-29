
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class LocalStorage {

    private static final String storageFile = "localStorage.txt";

    public static void setItem(String key, String value) {
        removeItem(key);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, true));
            writer.write(key + ":" + value);
            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static String getItem(String key) {
        String line = "";

        try {
            Scanner reader = new Scanner(new File(storageFile));
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                if (line.split(":")[0].equals(key)) break;
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        if (line.split(":")[0].equals(key)) {
            return line.split(":")[1];
        }
        return null;
    }

    public static void removeItem(String key) {
        ArrayList<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(storageFile), StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).split(":")[0].equals(key)) {
                fileContent.set(i, "");
                break;
            }
        }

        try {
            Files.write(Path.of(storageFile), fileContent, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void clear() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, false));
            writer.write("");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
