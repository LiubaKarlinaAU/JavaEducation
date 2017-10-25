package ru.spbau.karlina.task1;

public class SimpleHash {
    public SimpleHash(int maximum) {
        range = maximum;
    }

    /** Make and return hash code
     *  @param key String to make hash
     *  @return Number in range 0...(range - 1)
     */
    public int getHash(String key) {
        int hash = 7;

        for (int i = 0; i < key.length(); i++) {
            hash += 3 * key.charAt(i);
        }

        return hash % range;
    }

    /** Change range for making hash code */
    public void changeRange(int newRange) {
        range = newRange;
    }

    private int range;
}
