package ru.spbau.karlina.test1;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Multi thread MD5 maker realisation
 */
public class ForkJoinMD5 implements InterfaceMD5 {
    /**
     * @param path - path to make check-sum.
     * @return - byte array result
     * @throws NoSuchAlgorithmException - MessageDigest running exception.
     * @throws IOException              - thrown if something wrong with reading/writing in files.
     */
    @Override
    public byte[] getMD5(@NotNull Path path) throws NoSuchAlgorithmException, IOException {
        RecursiveTaskMD5 task = new RecursiveTaskMD5(path);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(task);
    }

    private class RecursiveTaskMD5 extends RecursiveTask<byte[]> {
        private Path path;

        RecursiveTaskMD5(@NotNull Path path) {
            this.path = path;
        }

        @Override
        protected byte[] compute() {
            if (!Files.isDirectory(path)) {
                try {
                    return getFileMD5(path);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Unable to read file.");
                }
            } else {
                try {
                    MessageDigest digest = MessageDigest.getInstance("MD5");
                    digest.update(path.getFileName().toString().getBytes());
                    List<RecursiveTaskMD5> children = new ArrayList<>();
                    Files.list(path).forEach(
                            (p) -> {
                                children.add(new RecursiveTaskMD5(p));
                            }
                    );

                    for (RecursiveTaskMD5 child : children) {
                        child.fork();
                    }
                    for (RecursiveTaskMD5 child : children) {
                        digest.update(child.join());
                    }

                    return digest.digest();
                } catch (Exception e) {
                    System.out.println("Problem with file from directory " + path);
                }
            }
            return null;
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
    }
}
