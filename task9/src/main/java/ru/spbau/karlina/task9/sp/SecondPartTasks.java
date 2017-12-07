package ru.spbau.karlina.task9.sp;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(@NotNull List<String> paths, CharSequence sequence) {
        return paths.stream().flatMap(p -> {
            try {
                return Files.lines(Paths.get(p));
            } catch (IOException e) {
                return Stream.empty();
            }
        }).filter(s -> s.contains(sequence)).collect(Collectors.toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {
        throw new UnsupportedOperationException();
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(@NotNull Map<String, List<String>> compositions) {
        compositions.entrySet().stream().sorted(
                Comparator.comparing(entry -> entry.getValue().stream()
                          .collect(Collectors.joining()).length())).findFirst().get();
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(@NotNull List<Map<String, Integer>> orders) {
        return orders.stream().flatMap(m -> m.entrySet().stream())
                     .collect(Collectors.groupingBy(Map.Entry::getKey,
                              Collectors.mapping(Map.Entry::getValue,
                                       Collectors.summingInt(Integer::intValue))));
    }
}
