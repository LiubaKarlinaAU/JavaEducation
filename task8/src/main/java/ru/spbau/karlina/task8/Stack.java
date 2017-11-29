package ru.spbau.karlina.task8;

import org.jetbrains.annotations.NotNull;

import java.util.EmptyStackException;

/**
 * Class Stack is representation of stack container with all general methods except search
 *
 * @param <T> type of stored elements
 */
public class Stack<T> {
    private Node<T> head = null;

    /**
     * Method checks existing the stack elements
     *
     * @return true if there isn't any elements and false otherwise
     */
    public boolean empty() {
        return head == null;
    }

    /**
     * Returns the top element without removing it from the stack,
     * and throw Exception if stack is empty
     *
     * @return top stack element
     * @throws EmptyStackException if stack is empty
     */
    public T peek() {
        if (empty())
            throw new EmptyStackException();

        return head.data;
    }

    /**
     * Returns the top element with removing it from the stack,
     * and throw Exception if stack is empty
     *
     * @return top stack element
     * @throws EmptyStackException if stack is empty
     */
    public T pop() {
        if (empty())
            throw new EmptyStackException();

        T top = head.data;
        head = head.prev;
        return top;
    }

    /**
     * Insert element to the stack top
     *
     * @param @NotNull T element - to be insert
     * @return inserted element
     */
    public T push(@NotNull T element) {
        Node top = new Node(element, head);
        head = top;

        return element;
    }

    /**
     * Inner class for Stack realisation
     */
    private class Node<T> {
        private T data;
        private Node<T> prev = null;

        public Node(@NotNull T element, Node prev) {
            data = element;
            this.prev = prev;
        }
    }
}