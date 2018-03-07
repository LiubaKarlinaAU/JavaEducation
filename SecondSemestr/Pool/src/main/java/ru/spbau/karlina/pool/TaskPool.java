package ru.spbau.karlina.pool;

public class TaskPool {
    private Thread[] threadArray;
    private final int countOfThreads;

    public TaskPool(int countOfThreads) {
        threadArray = new Thread[countOfThreads];
        this.countOfThreads = countOfThreads;
    }
}
