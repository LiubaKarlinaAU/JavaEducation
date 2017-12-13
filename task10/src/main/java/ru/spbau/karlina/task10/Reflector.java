package ru.spbau.karlina.task10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Reflector {
    public void printStructure(Class<?> someClass) throws FileNotFoundException {
    File file = new File (someClass.getSimpleName() + ".java");
        PrintWriter pr = new PrintWriter(file);
        pr.print("Hello Vasya!\n");
        pr.flush();
    }
}
