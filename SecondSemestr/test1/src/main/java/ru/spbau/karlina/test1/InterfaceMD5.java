package ru.spbau.karlina.test1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

/** MD5 maker interface */
public interface InterfaceMD5 {

    /**
     * @param path - path to make check-sum.
     * @return - byte array result
     * @throws NoSuchAlgorithmException - MessageDigest running exception.
     * @throws IOException - thrown if something wrong with reading/writing in files.
     */
    byte[] getMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException;
}
