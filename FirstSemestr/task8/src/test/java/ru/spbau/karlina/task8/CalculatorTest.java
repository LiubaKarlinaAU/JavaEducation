package ru.spbau.karlina.task8;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CalculatorTest {
    /**
     * Testing building reverse polish notation on simple example
     * Using mocked object
     */
    @Test
    public void makeReversePolishNotationTest1() {
        String expression = "1";

        Stack mockedDigits = mock(Stack.class);
        Stack mockedOperators = mock(Stack.class);

        when(mockedOperators.peek())
                .thenReturn('(');

        when(mockedOperators.pop())
                .thenReturn('(');


        Calculator calculator = new Calculator(mockedOperators, mockedDigits);
        assertEquals("1", calculator.makeReversePolishNotation(expression));

        verifyZeroInteractions(mockedDigits);
        verify(mockedOperators, times(1)).push(anyChar());
    }

    /**
     * Testing building reverse polish notation on example expression
     * Using mocked object
     */
    @Test
    public void makeReversePolishNotationTest2() {
        String expression = "1*2+4";

        Stack<Double> mockedDigits = mock(Stack.class);
        Stack<Character> mockedOperators = mock(Stack.class);

        when(mockedOperators.empty())
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false);

        when(mockedOperators.peek())
                .thenReturn('(')
                .thenReturn('*')
                .thenReturn('(');

        when(mockedOperators.pop())
                .thenReturn('*')
                .thenReturn('+')
                .thenReturn('(');

        Calculator calculator = new Calculator(mockedOperators, mockedDigits);
        assertEquals("12*4+", calculator.makeReversePolishNotation(expression));

        verify(mockedOperators, times(3)).push(anyChar());
        verifyZeroInteractions(mockedDigits);
    }

    /**
     * Testing calculating of notation on simple example
     * Using mocked object
     */
    @Test
    public void calculateNotationTest1() {
        String notation = "1";

        Stack<Double> mockedDigits = mock(Stack.class);
        Stack<Character> mockedOperators = mock(Stack.class);

        when(mockedDigits.pop())
                .thenReturn((double) 1);

        Calculator calculator = new Calculator(mockedOperators, mockedDigits);
        assertEquals(1, (int) calculator.calculateNotation(notation));

        verifyZeroInteractions(mockedOperators);
        verify(mockedDigits, times(1)).push(anyDouble());
    }


    /**
     * Testing calculating of notation on example notation
     * Using mocked object
     */
    @Test
    public void calculateNotationTest2() {
        String notation = "12*4+";

        Stack<Double> mockedDigits = mock(Stack.class);
        Stack<Character> mockedOperators = mock(Stack.class);

        when(mockedDigits.pop())
                .thenReturn((double) 2)
                .thenReturn((double) 1)
                .thenReturn((double) 4)
                .thenReturn((double) 2)
                .thenReturn((double) 6);

        Calculator calculator = new Calculator(mockedOperators, mockedDigits);
        assertEquals(6, (int) calculator.calculateNotation(notation));

        verify(mockedDigits, times(5)).push(anyDouble());
        verifyZeroInteractions(mockedOperators);
    }
}