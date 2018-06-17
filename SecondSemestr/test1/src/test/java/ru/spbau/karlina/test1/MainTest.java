package ru.spbau.karlina.test1;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/** MD5 makers tests */
public class MainTest {

    /** Single thread md5 test */
    @Test
    public void simpleTest1() {
        InterfaceMD5 singleThreadMD5 = new SingleThreadMD5();
        try {
            Path path = Paths.get(System.getProperty("user.dir"));
            byte[] result = singleThreadMD5.getMD5(path);
        } catch (Exception e) {
        }
    }


    /** Multi thread md5 test */
    @Test
    public void simpleTest2() {
        InterfaceMD5 forkJoinMD5 = new ForkJoinMD5();
        try {
            Path path = Paths.get(System.getProperty("user.dir"));
            byte[] result = forkJoinMD5.getMD5(path);
        } catch (Exception e) {
        }
    }
}