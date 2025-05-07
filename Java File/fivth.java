import java.io.*;
public class fivth {
     public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.toUpperCase()).append(System.lineSeparator());
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
            System.out.println("File content converted to uppercase successfully.");
        } 
        catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
