package ru.spbau.karlina.task2;

import java.io.*;
import java.util.Hashtable;

/** Class Trie contain static class Node inside */
public class Trie implements Serializable {
    /** Tree element with Terminal mark - mean it corresponds to storing string */
    static class Node implements Serializable {
        private boolean isTerminal = false;
        private final int depth;
        private int countOfData = 0;
        Hashtable<Character, Node> childrens = new Hashtable<Character, Node>();

        private Node(int depth) {
            this.depth = depth;
        }
    }

    private Node root = new Node(0);

    /**
     * @param element for finding
     * @return latest node with equal to given string prefix
     * */
    private Node find(Node current, String element) {
        if (element.length() > current.depth && current.childrens.containsKey(element.charAt(current.depth))) {
            return find(current.childrens.get(element.charAt(current.depth)), element);
        }
        return current;
    }

    /** Change countOfData in necessary nodes to correct adding String element in the trie */
    private void addOnPath(Node current, String element, int dif, int depth) {
        if (depth > current.depth) {
            current.countOfData += dif;
            if (depth > current.depth + 1) {
                addOnPath(current.childrens.get(element.charAt(current.depth)),
                        element, dif, depth);
            }
        }
    }

    /** Generate necessary nodes to correct adding String element in the trie */
    private void makeNodePath(Node current, String element) {
        if (current.depth < element.length()) {
            Node tmp = new Node(current.depth + 1);
            current.childrens.put(element.charAt(current.depth), tmp);
            makeNodePath(tmp, element);
        } else {
            current.isTerminal = true;
        }
        current.countOfData++;
    }

    /**
     * Trying to find element.
     * If found - change isTerminal mark and countOfData of node;
     * Then deleting unnecessary nodes on backtracking recursion
     * */
    private boolean findForRemoval(Node current, String element) {
        if (current.depth == element.length()) {
            if (current.isTerminal) {
                current.countOfData--;
            }
            return true;
        }
        if (current.childrens.containsKey(element.charAt(current.depth))) {
            Node next = current.childrens.get(element.charAt(current.depth));
            if (findForRemoval(next, element)) {

                if (next.countOfData == 0) {
                    current.childrens.remove(element.charAt(current.depth));
                }
                current.countOfData--;
                return true;
            }
        }
        return false;
    }

    /**
     * Trying to add element
     * @param element for adding
     * @return false if found element in trie and true (with adding element )in otherwise
     * */
    public boolean add(String element) {
        Node found = find(root, element);
        if (element.length() == found.depth && found.isTerminal) {
            return false;
        }
        addOnPath(root, element, 1, found.depth);
        makeNodePath(found, element);
        return true;
    }

    /**
     * Trying to find such element in trie
     * @param element for finding
     * @return false if didn't find and treu in otherwise
     * */
    public boolean contains(String element) {
        Node found = find(root, element);
        if (element.length() == found.depth && found.isTerminal) {
            return true;
        }
        return false;
    }

    /**
     * Trying to find such element and remove it
     * @param element to remove
     * @return false if didn't find and true (with removable) in otherwise
     * */
    public boolean remove(String element) {
        return findForRemoval(root, element);
    }

    public int size() {
        return root.countOfData;
    }

    /** Count amount of string with given prefix */
    public int howManyStartsWithPrefix(String prefix) {
        Node found = find(root, prefix);
        if (found == root) {
            return 0;
        }
        return found.countOfData;
    }

    /** Serialize trie(this) into output stream */
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(out);
            outputStream.writeObject(this);
            outputStream.close();
    }

    /** Deserialise trie(this) from input stream */
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream((in));
        Trie trie = (Trie)inputStream.readObject();
        root = trie.root;
    }
}