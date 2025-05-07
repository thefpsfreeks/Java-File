import java.io.*;
import java.util.*;
public class Six {
    public static void main(String[] args) {
        try {
            File file = new File("Sort.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            Collections.sort(lines);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String sortedLine : lines) { 
                writer.write(sortedLine);
                writer.newLine();
            }
            writer.close();
            System.out.println("File content sorted successfully.");
        }catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
