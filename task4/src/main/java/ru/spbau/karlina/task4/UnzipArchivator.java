package ru.spbau.karlina.task4;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Class has one public method which unzip file with ending ".zip" that found by the path given
 */
class UnzipArchivator {
    /**
     * Find all files ending on '.zip' and unzip only files there that matched to the regex
     *
     * @param path   a directory where we are looking for zip files
     * @param toFind regular expression to matching with possible files
     */
    public void unzip(@NotNull String path, @NotNull String toFind) {
        regex = toFind;
        try {
            unzipArchives(new File(path));
        } catch (NullPointerException e) {
            System.out.print("File '" + path + "' doesn't exist. \n");
        }
    }

    private String regex;

    private void unzipMatchingFiles(@NotNull File currentZipFile) {
        try {
            ZipFile zip = new ZipFile(currentZipFile.getAbsolutePath());
            Enumeration entries = zip.entries();

            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                Pattern pattern = Pattern.compile(regex);
                Matcher m = pattern.matcher(entry.getName());
                if (!entry.isDirectory() && m.matches()) {
                    try {
                        File file = new File(currentZipFile.getParent() + "/" + entry.getName());
                        write(zip.getInputStream(entry), new BufferedOutputStream(new FileOutputStream(file)));
                    } catch (FileNotFoundException e) {
                        System.out.print("File '" + entry.getName() + " not found.\n");
                    } catch (IOException e) {
                        System.out.print("A problem with writing to '" + entry + "' file. \n");
                    }
                }
            }
        } catch (IOException e) {
            System.out.print("There is a problem with unzip '" + currentZipFile + "' file.\n");
        }
    }

    private void unzipArchives(@NotNull File file) throws NullPointerException {
        File[] fileList = file.listFiles();

        for (File current : fileList) {
            if (current.isDirectory()) {
                unzipArchives(current);
            } else {
                if (current.getName().endsWith(".zip")) {
                    unzipMatchingFiles(current);
                }
            }
        }
    }

    private static void write(@NotNull InputStream in, @NotNull OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
