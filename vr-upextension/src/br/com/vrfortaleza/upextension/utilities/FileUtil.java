/* * Copyright (c) 2018 VR Fortaleza. All rights reserved. * */
package br.com.vrfortaleza.upextension.utilities;

import br.com.vrfortaleza.upextension.UpExtension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @Date: 21/11/2017
 * @author Derick Felix
 */
public class FileUtil {

    private FileUtil()
    {
    }

    /**
     * Returns the content separator of a file by a given line of that file, so
     * it can be used to create tokens. e.g.:
     * {@code String[] tokens = item.split(separator)}
     *
     * @param line a line of a file which will be used for getting the separator
     * of that line
     * @return the separator used by this line
     */
    public static String SEPARATOR(String line)
    {
        String semicolon = ";";
        String tab = "\t";
        String space = "  ";
        String comma = " ,";

        if (line.contains(semicolon)) {
            return semicolon;
        }
        if (line.contains(tab)) {
            return tab;
        }
        if (line.contains(space)) {
            return space;
        }
        if (line.contains(comma)) {
            return comma;
        }

        return "unknown";
    }

    /**
     * Retrieves the location of the jar file which is running this project,
     * e.g: if the file is localized in {@code ~/Documents/jarfile.jar} it will
     * returns its folder {@code ~/Documents/}
     *
     * @return the folder which this application.jar is in
     */
    public static File getJarFileLocation()
    {
        return new File(UpExtension.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getParentFile();
    }

    /**
     * Reads a file into a list of strings, each line of the file is represented
     * as a string of that list
     *
     * @param path the path of the file
     * @return a list of strings
     */
    public static List<String> read(String path)
    {
        List<String> lines = new LinkedList<>();

        try (FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }

        } catch (IOException e) {
            MessageUtil.showException(null, e);
        }
        return lines;
    }

    /**
     * Writes a list of string into a file, each line of the file is represented
     * as a string of that list
     *
     * @param lines the list of strings
     * @param path the path of the file
     */
    public static void write(List<String> lines, String path)
    {
        try (PrintWriter writer = new PrintWriter(path)) {
            for (String line : lines) {
                writer.println(line);
            }
            writer.flush();
        } catch (IOException e) {
            MessageUtil.showException(null, e);
        }
    }

    public static String jasper(String filename)
    {
        File file = new File("/up/extension/rpt/");
        if (file.mkdirs()) {
            Logger.getGlobal().info("Jasper directory created successfully!");
        }
        return file.toPath() + "/" + filename;
    }

}
