package ru.spbau.karlina.task9.sp;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public final class FirstPartTasks {

    private FirstPartTasks() {
    }

    /** Give a list of all album names from input album stream
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored all album names
     * @throws UnsupportedOperationException() */
    public static List<String> allNames(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    /** Give a list of sorted album names from input album stream
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored sorted album names
     * @throws UnsupportedOperationException() */
    public static List<String> allNamesSorted(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(Collectors.toList());
    }

    /** Give a list of sorted album tracks from input album stream
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored sorted track names from albums
     * @throws UnsupportedOperationException() */
    public static List<String> allTracksSorted(@NotNull Stream<Album> albums) {
        return albums.flatMap(a -> a.getTracks().stream().map(Track::getName))
                     .sorted().collect(Collectors.toList());
    }

    /** Give a list of album (sorted by name) which stored one or more track with raiting more then 95
     * @param @NotNull Stream<Album> albums input album stream
     * @return List<Album> list stored filtered albums
     * @throws UnsupportedOperationException() */
    public static List<Album> sortedFavorites(@NotNull Stream<Album> albums) {
        return albums.filter(a -> a.getTracks().stream().anyMatch(s -> s.getRating() > 95))
                     .sorted((a,b) -> a.getName().compareToIgnoreCase(b.getName()))
                     .collect(Collectors.toList());
    }

    /** Give a map stored pair of artist and his albums
     * @param @NotNull Stream<Album> albums input album stream
     * @return Map<Artist, List<Album>> result map
     * @throws UnsupportedOperationException() */
    public static Map<Artist, List<Album>> groupByArtist(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    /** Give a map stored pair of artist and name of his albums
     * @param @NotNull Stream<Album> albums input album stream
     * @return Map<Artist, List<String>> result map
     * @throws UnsupportedOperationException() */
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist,
                     Collectors.mapping(Album::getName, Collectors.toList())));
    }

    /** Give a count of repeating albums from input stream
     * @param @NotNull Stream<Album> albums input album stream
     * @return long result of counting
     * @throws UnsupportedOperationException() */
    public static long countAlbumDuplicates(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().map(entry -> entry.getValue() - 1).collect(Collectors.summingLong(Long::intValue));
    }

    // Альбом, в котором максимум рейтинга минимален
    // (если в альбоме нет ни одного трека, считать, что максимум рейтинга в нем --- 0)
    public static Optional<Album> minMaxRating(Stream<Album> albums) {
        throw new UnsupportedOperationException();
    }

    // Список альбомов, отсортированный по убыванию среднего рейтинга его треков (0, если треков нет)
    public static List<Album> sortByAverageRating(Stream<Album> albums) {
        throw new UnsupportedOperationException();
        //return albums.sorted(Comparator.comparing(al -> al.getTracks().stream().mapToDouble(Track::getRating).average().orElse(0)).collect(Collectors.toList());
    }

    // Произведение всех чисел потока по модулю 'modulo'
    // (все числа от 0 до 10000)
    public static int moduloProduction(IntStream stream, int modulo) {
        throw new UnsupportedOperationException();
    }

    // Вернуть строку, состояющую из конкатенаций переданного массива, и окруженную строками "<", ">"
    // см. тесты
    public static String joinTo(String... strings) {
        throw new UnsupportedOperationException();
    }

    // Вернуть поток из объектов класса 'clazz'
    public static <R> Stream<R> filterIsInstance(Stream<?> s, Class<R> clazz) {
        throw new UnsupportedOperationException();
    }
}
