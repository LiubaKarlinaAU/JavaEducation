package ru.spbau.karlina.test1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Single thread representation of MD5 maker */
public class SingleThreadMD5 implements InterfaceMD5 {
    /**
     * @param path - path to make check-sum.
     * @return - byte array result
     * @throws NoSuchAlgorithmException - MessageDigest running exception.
     * @throws IOException - thrown if something wrong with reading/writing in files.
     */
    @Override
    public byte[] getMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        if (Files.isDirectory(path)) {
            return getDirectoryMD5(path);
        } else {
            return getFileMD5(path);
        }
    }

    private byte[] getFileMD5(@NotNull Path path) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        java.io.InputStream inputStream = Files.newInputStream(path);

        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, readBytes);
        }

        return digest.digest();
    }

    private byte[] getDirectoryMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(path.getFileName().toString().getBytes());

        Files.list(path).sorted().forEach(
                (p) -> {
                    if (Files.isDirectory(p)) {
                        try {
                            digest.update(getDirectoryMD5(p));
                        } catch (Exception e) {
                            System.out.println("Problem with directory" + p);
                        }
                    } else {
                        try {
                            digest.update(getFileMD5(p));
                        } catch (Exception e) {
                            System.out.println("Problem with file" + p);
                        }
                    }
                }
        );
        return digest.digest();
    }
}
