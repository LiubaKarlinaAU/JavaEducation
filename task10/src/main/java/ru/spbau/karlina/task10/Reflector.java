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
import java.util.stream.Collector;
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

    /**
     * Print difference of two classes on screen.
     *
     * @param firstClass  first class to compare
     * @param secondClass secondClass to compare
     * @param writer      stream write to
     * @return true if classes are different and false otherwise
     */
    public boolean diffClasses(@NotNull Class<?> firstClass, @NotNull Class<?> secondClass, @NotNull PrintStream writer) {
        return printDifferentFields(firstClass, secondClass, writer) |
                printDifferentFields(secondClass, firstClass, writer) |
                printDifferentMethods(firstClass, secondClass, writer) |
                printDifferentMethods(secondClass, firstClass, writer);
    }

    private boolean printDifferentMethods(@NotNull Class<?> first, @NotNull Class<?> second, @NotNull PrintStream writer) {
        @NotNull Set<String> classMethods = Arrays.stream(first.getDeclaredMethods())
                .map(Method::toGenericString).collect(Collectors.toCollection(HashSet::new));

        return Arrays.stream(second.getDeclaredMethods())
                .map(Method::toGenericString).filter(s -> !classMethods.contains(s))
                .peek(s -> System.out.println(s)).count() > 0;
    }

    private boolean printDifferentFields(@NotNull Class<?> first, @NotNull Class<?> second, @NotNull PrintStream writer) {
        @NotNull Set<String> classFields = Arrays.stream(first.getDeclaredFields())
                .map(Field::toGenericString).collect(Collectors.toCollection(HashSet::new));

        return Arrays.stream(second.getDeclaredFields())
                .map(Field::toGenericString).filter(s -> !classFields.contains(s))
                .peek(s -> System.out.println(s)).count() > 0;
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
            //System.out.println(method.getName());
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

    private void printClassFields(@NotNull Field[] declaredFields, @NotNull StringBuilder fileLines) {
        for (Field field : declaredFields) {
            fileLines.append(Modifier.toString(field.getModifiers()))
                    .append(" " + getGenericFieldType(field))
                    .append(" " + field.getName());

            if (Modifier.isFinal(field.getModifiers())) {
                fileLines.append(" = " + getDefaultValue(field.getType()));
            }

            fileLines.append(";\n");
        }
    }

    @NotNull
    private String getGenericFieldType(@NotNull Field field) {
        //System.out.println(field.getType().getTypeName());
        return field.getGenericType().getTypeName();
    }

    @NotNull
    private static String getDefaultValue(@NotNull Class<?> type) {
        if (!type.isPrimitive()) {
            return "null";
        }

        if (type == boolean.class) {
            return "false";
        }

        return "0";
    }

    private void printClassHeader(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append(Modifier.toString(someClass.getModifiers())).append(" ")
                .append(someClass.isInterface() ? "" : "class ")
                .append(someClass.getSimpleName())
                .append(getClassGenericSignature(someClass));

        addExtendedAndImplemented(someClass, fileLines);
        fileLines.append(" {\n");

    }


    private static String getClassGenericSignature(@NotNull Class<?> someClass) {
        TypeVariable<? extends Class<?>>[] typeParameters = someClass.getTypeParameters();
        return (typeParameters.length > 0 ?
                Arrays.stream(typeParameters)
                        .map(TypeVariable::getName)
                        .collect(Collectors.joining(", ", "<", ">")) :
                "");
    }

    private void addExtendedAndImplemented(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        Class<?> superClass = someClass.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            fileLines.append(" extends " + superClass.getSimpleName());
        }

        if (someClass.getInterfaces().length != 0 || someClass.getGenericInterfaces().length != 0) {
            fileLines.append(Arrays.stream(someClass.getInterfaces())
                    .map(in -> " " + in.getSimpleName()).collect(Collectors.joining(",", " implements", "")));
        }
    }

    private void printPackage(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) {
        fileLines.append(this.getClass().getPackage()).append(";\n");
    }

    private void printImports(@NotNull Class<?> someClass, @NotNull StringBuilder fileLines) throws NoSuchMethodException {
        @NotNull Set<String> imports = new HashSet<>();

        collectImports(someClass, imports);

        fileLines.append("\n");
        imports.stream().map(s -> "import " + s + ";\n").forEach(fileLines::append);
    }

    private static void collectImports(@NotNull Class<?> someClass, @NotNull Set<String> imports) {
        addFieldsImports(imports, someClass.getDeclaredFields());
        addConstructorsImports(imports, someClass.getConstructors());
        addMethodImports(imports, someClass);

        addInheritedImports(imports, someClass);
        addInnerImports(imports, someClass.getDeclaredClasses());
    }

    private static void addInheritedImports(@NotNull Set<String> imports, @NotNull Class someClass) {
        if (someClass.getSuperclass() != null) {
            addTypeToImports(imports, someClass.getSuperclass());
        }

        for (@NotNull Class<?> superclass : someClass.getInterfaces()) {
            addTypeToImports(imports, superclass);
        }
    }

    private static void addInnerImports(@NotNull Set imports, @NotNull Class[] classes) {
        for (@NotNull Class<?> inner : classes) {
            collectImports(inner, imports);
        }
    }

    private static void addFieldsImports(@NotNull Set<String> imports, @NotNull Field[] declaredFields) {
        Arrays.stream(declaredFields).forEach(f -> addTypeToImports(imports, f.getType()));
    }

    private static void addConstructorsImports(@NotNull Set<String> imports, @NotNull Constructor<?>[] constructors) {
        for (@NotNull Constructor<?> cons : constructors) {
            for (@NotNull Class<?> type : cons.getParameterTypes()) {
                addTypeToImports(imports, type);
            }
        }
    }

    private static void addMethodImports(@NotNull Set<String> imports, @NotNull Class<?> someClass) {
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
        if (!type.isPrimitive() && type != Object.class) {
            if (type.isArray()) {
                addTypeToImports(imports, type.getComponentType());
            } else {
                imports.add(type.getCanonicalName());
            }
        }
    }
}