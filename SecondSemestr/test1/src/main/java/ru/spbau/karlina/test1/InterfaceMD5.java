package ru.spbau.karlina.test1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public interface InterfaceMD5 {
    byte[] getMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException;
}
