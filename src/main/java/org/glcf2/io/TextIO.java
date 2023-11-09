package org.glcf2.io;

import java.io.*;

public final class TextIO {
    private TextIO() {}

    public static String read(String fileName) throws IOException {
        StringBuilder code = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(ResourceReader.read(fileName)));
        String line;

        while ((line = br.readLine()) != null) {
            code.append(line);
            code.append("\n");
        }

        br.close();

        return code.toString();
    }

    public static String read(String fileName, ExceptionCatcher<String> ec) {
        try {
            StringBuilder code = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(ResourceReader.read(fileName)));
            String line;

            while ((line = br.readLine()) != null) {
                code.append(line);
                code.append("\n");
            }

            br.close();

            return code.toString();
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }

    public static String read(File fileName) throws IOException {
        StringBuilder code = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            code.append(line);
            code.append("\n");
        }

        br.close();

        return code.toString();
    }

    public static String read(File fileName, ExceptionCatcher<String> ec) {
        try {
            StringBuilder code = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {
                code.append(line);
                code.append("\n");
            }

            br.close();

            return code.toString();
        } catch (IOException e) {
            if (ec == null) return null;
            return ec.invoke(e);
        }
    }
}
