package ru.spbau.karlina.task10;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Reflector {

    /**
     * Prints structure of class to same named file.
     *
     * @param someClass class to make reflection
     * @throws IOException if failed to create file
     */
    public void printStructure(@NotNull Class<?> someClass) throws IOException, NoSuchMethodException {
        @NotNull StringBuilder fileLines = new StringBuilder();

        printPackage(someClass, fileLines);
        printImports(someClass, fileLines);
        printClass(someClass, fileLines);
        writeToFile("src/main/java/ru/spbau/karlina/task10/" + someClass.getSimpleName() + ".java", fileLines);
    }

    private void writeToFile(@NotNull String fileName, @NotNull StringBuilder fileLines) throws IOException {
        File file = new File(fileName);
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        writer.write(fileLines.toString());
        writer.flush();
        writer.close();
    }

    private void printClass(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append("\n");
        printClassHeader(someClass, fileLines);
        printClassFields(someClass.getDeclaredFields(), fileLines);
        fileLines.append("\n");
        printClassConstructors(someClass, fileLines);
        printClassMethods(someClass, fileLines);

        for (@NotNull Class<?> inner : someClass.getDeclaredClasses()) {
            printClass(inner, fileLines);
        }

        fileLines.append("}");
    }

    private void printClassMethods(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        for (Method method : someClass.getDeclaredMethods()) {
            int modifiers = method.getModifiers();
            fileLines.append(modifiers == 0 ?
                    "" : Modifier.toString(modifiers) + " ")
                    .append(makeGenericSignature(method))
                    .append(method.getReturnType().getSimpleName().toString() + " ")
                    .append(method.getName())
                    .append(makeParametrList(method));
            System.out.println(method.getName());
            if (!Modifier.isNative(modifiers) && !Modifier.isAbstract(modifiers)) {
                fileLines.append(" {\n").append(getBody(method)).append("\n}");
            } else {
                fileLines.append(";");
            }
            fileLines.append("\n\n");
        }
    }

    @NotNull
    private String getBody(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType == void.class) {
            return "";
        }

        String value = "null";


        if (returnType.isPrimitive()) {
            value = "0";
        }

        if (returnType == boolean.class) {
            value = "false";
        }

        return "return " + value + ";";
    }

    private void printClassConstructors(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        for (Constructor constructor : someClass.getDeclaredConstructors()) {
            fileLines.append(constructor.getModifiers() == 0 ?
                    "" : Modifier.toString(constructor.getModifiers()) + " ")
                    .append(makeGenericSignature(constructor))
                    .append(someClass.getSimpleName())
                    .append(makeParametrList(constructor))
                    .append(" {\n}\n\n");
        }
    }

    @NotNull
    private String makeGenericSignature(@NotNull Executable executable) {
        @NotNull Pattern pattern = Pattern.compile("(<.*?>)");
        String genericString = executable.toGenericString();
        @NotNull Matcher matcher = pattern.matcher(genericString.substring(0, genericString.indexOf('(')));
        return matcher.find() ? matcher.group(1) + " " : "";
    }

    @NotNull
    private String makeParametrList(@NotNull Executable executable) {
        Parameter[] parametrs = executable.getParameters();
        Type[] parametrTypes = executable.getGenericParameterTypes();
        Stream.Builder<String> builder = Stream.builder();
        for (int i = 0; i < parametrs.length; ++i) {
            builder.add(parametrTypes[i].getTypeName() + " " + parametrs[i].getName());
        }

        return builder.build()
                .collect(Collectors.joining(", ", "(", ")"))
                .toString();
    }

    private void printClassFields(Field[] declaredFields, @NotNull StringBuilder fileLines) {
        Arrays.stream(declaredFields).map(f -> Modifier.toString(f.getModifiers()) + " " + f.getGenericType() + " " + f.getName() + ";\n").forEach(fileLines::append);
    }

    private void printClassHeader(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append(Modifier.toString(someClass.getModifiers())).append(" ")
                .append(someClass.isInterface() ? "" : "class ")
                .append(someClass.getSimpleName());

        addExtendedAndImplemented(someClass, fileLines);
        fileLines.append(" {\n");

    }

    private void addExtendedAndImplemented(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        Class<?> superClass = someClass.getSuperclass();
        if (superClass != null) {
            fileLines.append(" extends " + superClass.getSimpleName());
        }

        if (someClass.getInterfaces().length != 0 || someClass.getGenericInterfaces().length != 0) {
            fileLines.append(superClass == null ? "" : ",");
            fileLines.append(Arrays.stream(someClass.getInterfaces())
                    .map(in -> " " + in.getSimpleName()).collect(Collectors.joining(",", " implements", "")));
        }
    }

    private void printPackage(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append(this.getClass().getPackage()).append(";\n");
    }

    private void printImports(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) throws NoSuchMethodException {
        @NotNull Set<String> imports = new HashSet<>();

        addFieldsImports(imports, someClass.getDeclaredFields());
        addConstructorsImports(imports, someClass.getConstructors());
        addMethodImports(imports, someClass);

        fileLines.append("\n");
        imports.stream().map(s -> "import " + s + ";\n").forEach(fileLines::append);
    }

    private void addFieldsImports(@NotNull Set<String> imports, @NotNull Field[] declaredFields) {
        Arrays.stream(declaredFields).forEach(f -> addTypeToImports(imports, f.getType()));
    }

    private void addConstructorsImports(@NotNull Set<String> imports, @NotNull Constructor<?>[] constructors) {
        for (@NotNull Constructor<?> cons : constructors) {
            for (@NotNull Class<?> type : cons.getParameterTypes()) {
                addTypeToImports(imports, type);
            }
        }
    }

    private void addMethodImports(@NotNull Set<String> imports, @NotNull Class<?> someClass) {
        for (@NotNull Method method : someClass.getDeclaredMethods()) {
            for (@NotNull Class<?> type : method.getParameterTypes()) {
                addTypeToImports(imports, type);
            }

            if (method.getReturnType() != someClass) {
                addTypeToImports(imports, method.getReturnType());
            }
        }
    }

    private static void addTypeToImports(@NotNull Set<String> imports, @NotNull Class<?> type) {
        if (!type.isPrimitive()) {
            imports.add(type.getCanonicalName());
        }
    }
}