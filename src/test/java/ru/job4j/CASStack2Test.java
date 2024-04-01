package ru.job4j;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;


class CASStack2Test {

    @Test
    public void whenAddSevenValuesThenGenSevenValues() {
        CASStack2<Integer> stack = new CASStack2<>();
        int[] values = {1,4,5,6,4,3,5};
        int[] expected = new int[values.length];
        Arrays.stream(values).forEach(stack::push);
        for (int i = values.length - 1; i >= 0; i--) {
            expected[i] = stack.pop();
        }
        stack.push(3);
        assertThat(values).isEqualTo(expected);
    }
    @Test
    public void testPopOutOfStackException() {
        CASStack2<Integer> stack = new CASStack2<>();
        int[] values = {1};
        Arrays.stream(values).forEach(stack::push);
        stack.pop();
        assertThatThrownBy(() -> stack.pop()).isExactlyInstanceOf(IllegalArgumentException.class);
    }

}