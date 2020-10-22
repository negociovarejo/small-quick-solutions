import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

public class Qcxml {

    public static void main(String[] args)
    {
        if (args.length != 3) {
            System.out.println("Usage: ./progName <folderPath> <currentCode> <newCode>");
            return;
        }

        File dir = new File(args[0]);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.isFile()) {
                    List<String> lines = ReadFrom(child);
                    List<String> newLines = new ArrayList<>();

                    lines.forEach(line -> {
                        newLines.add(line.replace(args[1] + "</cProd>", args[2] + "</cProd>"));
                    });

                    WriteTo(newLines, child);
                }
            }
        }
    }

    private static List<String> ReadFrom(File file)
    {
        List<String> lines = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNext()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        return lines;
    }

    private static void WriteTo(List<String> lines, File file)
    {
        try (PrintWriter writer = new PrintWriter(file)) {
            for (String line : lines) {
                writer.println(line);
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}