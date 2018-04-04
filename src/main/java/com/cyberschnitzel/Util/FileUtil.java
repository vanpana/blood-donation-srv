package com.cyberschnitzel.Util;

import java.io.*;

public class FileUtil {
    public static String getFileContent(File file) {
        BufferedReader bufferedReader = null;
        StringBuilder content = new StringBuilder();

        try {
            // Get file from resources folder
            bufferedReader = new BufferedReader(new FileReader(file));

            // Build the script from the file
            while (bufferedReader.readLine() != null)
                content.append(bufferedReader.readLine()).append("\n");

        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found:" + fnfe.getMessage());
        } catch (IOException ioe) {
            System.out.println("I/O Exception:" + ioe.getMessage());
        } finally {
            // Safely close the input
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    System.out.println("Failed closing the file: " + e.getMessage());
                }
            }
        }

        return content.toString();
    }
}
