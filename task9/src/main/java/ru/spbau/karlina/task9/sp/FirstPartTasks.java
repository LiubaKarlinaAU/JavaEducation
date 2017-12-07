package ru.spbau.karlina.task9.sp;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public final class FirstPartTasks {

    private FirstPartTasks() {
    }

    /**
     * Give a list of all album names from input album stream
     *
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored all album names
     * @throws UnsupportedOperationException()
     */
    public static List<String> allNames(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).collect(Collectors.toList());
    }

    /**
     * Give a list of sorted album names from input album stream
     *
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored sorted album names
     * @throws UnsupportedOperationException()
     */
    public static List<String> allNamesSorted(@NotNull Stream<Album> albums) {
        return albums.map(Album::getName).sorted().collect(Collectors.toList());
    }

    /**
     * Give a list of sorted album tracks from input album stream
     *
     * @param @NotNull Stream<Album> albums input stream
     * @return List<String> list stored sorted track names from albums
     * @throws UnsupportedOperationException()
     */
    public static List<String> allTracksSorted(@NotNull Stream<Album> albums) {
        return albums.flatMap(a -> a.getTracks().stream().map(Track::getName))
                .sorted().collect(Collectors.toList());
    }

    /**
     * Give a list of album (sorted by name) which stored one or more track with raiting more then 95
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return List<Album> list stored filtered albums
     * @throws UnsupportedOperationException()
     */
    public static List<Album> sortedFavorites(@NotNull Stream<Album> albums) {
        return albums.filter(a -> a.getTracks().stream().anyMatch(s -> s.getRating() > 95))
                .sorted((a, b) -> a.getName().compareToIgnoreCase(b.getName()))
                .collect(Collectors.toList());
    }

    /**
     * Give a map stored pair of artist and his albums
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return Map<Artist, List<Album>> result map
     * @throws UnsupportedOperationException()
     */
    public static Map<Artist, List<Album>> groupByArtist(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist));
    }

    /**
     * Give a map stored pair of artist and name of his albums
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return Map<Artist, List<String>> result map
     * @throws UnsupportedOperationException()
     */
    public static Map<Artist, List<String>> groupByArtistMapName(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Album::getArtist,
                Collectors.mapping(Album::getName, Collectors.toList())));
    }

    /**
     * Give a count of repeating albums from input stream
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return long result of counting
     * @throws UnsupportedOperationException()
     */
    public static long countAlbumDuplicates(@NotNull Stream<Album> albums) {
        return albums.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().map(entry -> entry.getValue() - 1).collect(Collectors.summingLong(Long::intValue));
    }

    /**
     * Give a album with the smallest maximum track rating
     * If album doesn't have any track his maximum will be zero
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return Optional<Album> result album
     * @throws UnsupportedOperationException()
     */
    public static Optional<Album> minMaxRating(@NotNull Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(a -> a.getTracks().stream().mapToDouble(Track::getRating).max().orElse(0))).findFirst();
    }

    /**
     * Give a list of albums sorted by average album track rating
     *
     * @param @NotNull Stream<Album> albums input album stream
     * @return List<Album> result list
     * @throws UnsupportedOperationException()
     */
    public static List<Album> sortByAverageRating(@NotNull Stream<Album> albums) {
        return albums.sorted(Comparator.comparing(a -> -a.getTracks().stream().mapToDouble(Track::getRating).average().orElse(0))).collect(Collectors.toList());
    }

    /**
     * Give back composition of numbers from input stream modulo given
     *
     * @param @NotNull IntStream stream input number stream
     * @return int  - result
     * @throws UnsupportedOperationException()
     */
    public static int moduloProduction(@NotNull IntStream stream, int modulo) {
        return stream.reduce(1, (a, b) -> (a * b) % modulo);
    }

    /**
     * Give back a concat of input string array
     * Uses ", " as a delimiter and "<" as beggining and ">" as ending
     *
     * @param @NotNull String... strings input string array
     * @return String result string
     * @throws UnsupportedOperationException()
     */
    public static String joinTo(@NotNull String... strings) {
        return Arrays.stream(strings).collect(Collectors.joining(", ", "<", ">"));
    }

    /**
     * Give back filtered by given instance stream
     *
     * @param @NotNull Stream<?> s input stream
     * @return Stream<R> result stream
     * @throws UnsupportedOperationException()
     * @NotNull Class<R> clazz instance to make filtration
     */
    public static <R> Stream<R> filterIsInstance(@NotNull Stream<?> s, @NotNull Class<R> clazz) {
        return s.filter(clazz::isInstance).map(clazz::cast);
    }
}
