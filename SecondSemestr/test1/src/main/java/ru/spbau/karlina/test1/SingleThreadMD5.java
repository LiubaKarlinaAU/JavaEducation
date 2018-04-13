package ru.spbau.karlina.test1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class SingleThreadMD5 implements InterfaceMD5 {
    @Override
    public byte[] getMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        return new byte[0];
    }
}
