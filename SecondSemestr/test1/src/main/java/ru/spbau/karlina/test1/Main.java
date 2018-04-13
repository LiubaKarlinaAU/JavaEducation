package ru.spbau.karlina.test1;

import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Write the name of the directory, please.");
            return;
        }

        if (args.length > 1) {
            System.out.println("Too many arguments.");
            return;
        }

        InterfaceMD5 singleThreadMD5 = new SingleThreadMD5();
        try {
            long startTime = System.currentTimeMillis();
            byte[] result = singleThreadMD5.getMD5(Paths.get(args[0]));
            System.out.println(result);
            long currentTime = System.currentTimeMillis();
            System.out.println(currentTime - startTime + " time of single thread MD5 running.");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        InterfaceMD5 forkJoinMD5 = new ForkJoinMD5();
        try {
            long startTime = System.currentTimeMillis();
            byte[] result = forkJoinMD5.getMD5(Paths.get(args[0]));
            System.out.println(result);
            long currentTime = System.currentTimeMillis();
            System.out.println(currentTime - startTime + " time of fork and join MD5 running.");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}