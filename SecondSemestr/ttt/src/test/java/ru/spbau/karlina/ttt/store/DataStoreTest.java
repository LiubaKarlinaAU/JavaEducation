package test.java.ru.spbau.karlina.ttt.store;

import main.java.ru.spbau.karlina.ttt.logic.GameResult;
import main.java.ru.spbau.karlina.ttt.store.DataStore;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataStoreTest {
    @Test
    public void getGameCountInitial() throws Exception {
    assertEquals(0, new DataStore().getGameCount());
    }

    @Test
    public void getCrossWinsInitial() throws Exception {
        assertEquals(0, new DataStore().getCrossWins());

    }

    @Test
    public void getNaughtWinsInitial() throws Exception {
        assertEquals(0, new DataStore().getNaughtWins());

    }

    @Test
    public void getDrawCountInitial() throws Exception {
        assertEquals(0, new DataStore().getDrawCount());
    }

    @Test
    public void getNotFinishedInitial() throws Exception {
        assertEquals(0, new DataStore().getNotFinished());
    }

    @Test
    public void getGameCountAfterRecording() throws Exception {
        DataStore dataStore = new DataStore();
        dataStore.addRecord(GameResult.DRAW);
        assertEquals(1, dataStore.getGameCount());
    }

    @Test
    public void getCrossWinsAfterRecording() throws Exception {
        DataStore dataStore = new DataStore();
        dataStore.addRecord(GameResult.FIRST_WIN);
        assertEquals(1, dataStore.getCrossWins());
    }

    @Test
    public void getNaughtWinsAfterRecording() throws Exception {
        DataStore dataStore = new DataStore();
        dataStore.addRecord(GameResult.SECOND_WIN);
        assertEquals(1, dataStore.getNaughtWins());
    }

    @Test
    public void getDrawCountAfterRecording() throws Exception {
        DataStore dataStore = new DataStore();
        dataStore.addRecord(GameResult.DRAW);
        assertEquals(1, dataStore.getDrawCount());
    }

    @Test
    public void getNotFinishedAfterRecording() throws Exception {
        DataStore dataStore = new DataStore();
        dataStore.addRecord(GameResult.GAME_IN_PROGRESS);
        assertEquals(1, dataStore.getNotFinished());
    }

}