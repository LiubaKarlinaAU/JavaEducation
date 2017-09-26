package ru.spbau.karlina.task1;

public class SimpleHash {
    private int range;

    public SimpleHash( int maximum ) {
        range = maximum;
    }

    /**
     * @param key String to make hash
     * @return Number in range 0...(range - 1)
     */
    public int getHash( String key ) {
        int hash = 7;

        for (int i = 0; i < key.length(); i++) {
            hash = hash * 3 + key.charAt(i);
        }

        return hash % range;
    }

    public void changeRange( int newRange ) {
        range = newRange;
    }
}
