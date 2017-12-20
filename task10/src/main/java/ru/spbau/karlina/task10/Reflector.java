package ru.spbau.karlina.task10;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class Reflector {

    /**
     * Prints structure of class to same named file.
     * @param someClass class to make reflection
     * @throws IOException if failed to create file
     */
    public void printStructure(@NotNull Class<?> someClass) throws IOException, NoSuchMethodException {
        @NotNull StringBuilder fileLines = new StringBuilder();

        printPackage(someClass, fileLines);
        printImports(someClass, fileLines);
        printClass(someClass, fileLines);
        writeToFile(someClass.getSimpleName() + ".java", fileLines);
    }

    private void writeToFile(@NotNull String fileName, @NotNull StringBuilder fileLines) throws IOException {
        File file =  new File(fileName);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        writer.write(fileLines.toString());
        writer.flush();
        writer.close();
    }

    private void printClass(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append("\n");
        printClassHeader(someClass, fileLines);
        printClassFields(someClass.getDeclaredFields(), fileLines);

        fileLines.append("}");
    }

    private void printClassFields(Field[] declaredFields, @NotNull StringBuilder fileLines) {
    }

    private void printClassHeader(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append(Modifier.toString(someClass.getModifiers())).append(" ")
                .append(someClass.isInterface() ? "" : "class ")
                .append(someClass.getSimpleName());

        addExtendedAndImplemented(someClass, fileLines);
        fileLines.append(" {\n");

    }

    private void addExtendedAndImplemented(Class<?> someClass, @NotNull StringBuilder fileLines) {
        Class<?> superClass = someClass.getSuperclass();
        if (superClass != null ) {
            fileLines.append(" extends " + superClass.getSimpleName());
        }

        if (someClass.getInterfaces().length != 0 || someClass.getGenericInterfaces().length != 0) {
            fileLines.append(superClass == null ? "" : ",");
            fileLines.append(Arrays.stream(someClass.getInterfaces())
                    .map(in -> " " + in.getSimpleName()).collect(Collectors.joining(",", " implements", "")));
            //fileLines.append(Arrays.stream(someClass.getGenericInterfaces())
            //        .map(in -> in.getTypeName()).collect(Collectors.joining(", ")));
        }
    }

    private void printPackage(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append("import ").append(someClass.getPackage().getName()).append(";\n");
    }

    private void printImports(Class<?> someClass, @NotNull StringBuilder fileLines) throws NoSuchMethodException {
       @NotNull Set<String> imports = new HashSet<>();

       addFieldsImports(imports, someClass.getDeclaredFields(), fileLines);
       addConstructorsImports(imports, someClass.getConstructors(), fileLines);
       addMethodImports(imports, someClass.getDeclaredMethods(), fileLines);

       fileLines.append("\n");
       imports.stream().map(s -> "import " + s + ";\n").forEach(fileLines::append);
    }

    private void addFieldsImports(@NotNull Set<String> imports, Field[] declaredFields, @NotNull StringBuilder fileLines) {
        Arrays.stream(declaredFields).forEach(f -> addTypeToImports(imports, f.getType()));
    }

    private void addConstructorsImports(@NotNull Set<String> imports, Constructor<?>[] constructors, @NotNull StringBuilder fileLines) {
       // Arrays.stream(constructors).forEach(cons -> Arrays.stream(cons.getParameterTypes()).forEach(type -> addTypeToImports(imports, type)));
        for (@NotNull Constructor<?> cons : constructors) {
            for (@NotNull Class<?> type : cons.getParameterTypes()) {
                addTypeToImports(imports, type);
            }
        }
    }

    private void addMethodImports(@NotNull Set<String> imports, Method[] declaredMethods, @NotNull StringBuilder fileLines) {
       for (@NotNull Method method : declaredMethods) {
           for (@NotNull Class<?> type : method.getParameterTypes()) {
               addTypeToImports(imports, type);
           }

           addTypeToImports(imports, method.getReturnType());
       }
    }

    private static void addTypeToImports(@NotNull Set<String> imports, @NotNull Class<?> type) {
        if (!type.isPrimitive()) {
            imports.add(type.getCanonicalName());
        }
    }

}
