package ru.spbau.karlina.task4;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/** Class has one public method which unzip file with ending ".zip" that found by the path given */
public class UnzipArchivator {
    public void unzip(String path, String find) throws Exception{
        try {
            File[] list = new File(path).listFiles();
            for (File file : list) {
                if (file.getName().endsWith(".zip")) {
                    try {
                        ZipFile zip = new ZipFile(file.getAbsolutePath());
                        Enumeration entries = zip.entries();

                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            if (entry.getName().matches(find)) {

                                if (entry.isDirectory()) {
                                    new File(file.getParent(), entry.getName()).mkdirs();
                                } else {
                                    write(zip.getInputStream(entry),
                                            new BufferedOutputStream(new FileOutputStream(
                                                    new File(file.getParent(), entry.getName()))));
                                }
                            }
                        }

                        zip.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
