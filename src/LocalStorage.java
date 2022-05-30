
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class LocalStorage {

    /*

    About LocalStorage:
    This local storage system, with methods inspired by the HTML Web Local Storage API, works by storing data in
    key-value pairs, and are saved to a .txt file. Key-value pairs are separated by three colons. Like in the webs Local
    Storage, there can not be duplicates of a key.

     */

    private static final String storageFile = "localStorage.txt";

    public static void setItem(String key, String value) {
        // removes item so that it could be replaced with new, updated value
        removeItem(key);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, true));

            // separates key and value by three colons
            writer.write(key + ":::" + value);

            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static String getItem(String key) {
        String line = "";

        try {
            Scanner reader = new Scanner(new File(storageFile));

            while (reader.hasNextLine()) { // looks for key
                line = reader.nextLine();
                if (line.split(":::")[0].equals(key)) { // splits line by three colons, breaks loop if key found
                    break;
                }
            }

            reader.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        if (line.split(":::")[0].equals(key)) { // if key found, returns value
            return line.split(":::")[1];
        }
        return null; // if key not found, returns null
    }

    public static void removeItem(String key) {
        ArrayList<String> fileContent = null;

        try {
            // creates array list with each element in the list being a line in localStorage.txt
            fileContent = new ArrayList<>(Files.readAllLines(Path.of(storageFile), StandardCharsets.UTF_8));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

        for (int i = 0; i < fileContent.size(); i++) { // loops through each line
            if (fileContent.get(i).split(":::")[0].equals(key)) { // if it finds the key
                // replaces key with an empty string
                fileContent.set(i, "");
                break;
            }
        }

        try {
            // rewrites entire file but without the given key and its corresponding value
            Files.write(Path.of(storageFile), fileContent, StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public static void clear() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(storageFile, false));

            // overwrites entire file with empty string
            writer.write("");

            writer.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

}
