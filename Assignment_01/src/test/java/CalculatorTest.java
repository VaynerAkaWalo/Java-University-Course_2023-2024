import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class CalculatorTest {

    private CalculatorOperations calculator;

    @BeforeEach
    void beforeEach() {
        calculator = new Calculator();
    }

    private static Stream<Integer> setAccumulatorShouldSetAccumulatorToGivenValue() {
        return Stream.of(0, 1, 2, 3, 5, 8, 13, -21, -34);
    }

    private static Stream<Arguments> valuesForAdditionTests() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 5), 11),
                Arguments.of(List.of(10, 20, 30, 50), 110),
                Arguments.of(List.of(-1, -2, -3, -5), -11),
                Arguments.of(List.of(-1, 2, -3, 5), 3)
        );
    }

    private static Stream<Arguments> valuesForSubtractionTests() {
        return Stream.of(
                Arguments.of(List.of(2), -2),
                Arguments.of(List.of(1, 2, 3, 5), -11),
                Arguments.of(List.of(-1, -2, -3, -5), 11),
                Arguments.of(List.of(-1, 2, -3, 5), -3)
        );
    }

    private static Stream<List<Integer>> accumulatorToMemoryShouldSetMemoryAtGivenIndexToAccumulatorValue() {
        return Stream.of(
                List.of(1, 2, 3, 4, 5),
                List.of(-1, 2, -3, 4, -5),
                List.of(10, 20, 30, 40 , 50)
        );
    }

    private static Stream<List<Integer>> pushAccumulatorOnStackShouldPushAccumulatorValueOnStack() {
        return Stream.of(
                List.of(1),
                List.of(1, 2, 3, 4, 5),
                List.of(1, -2, 3, -4, 5),
                List.of(10, 20, 30, 40, -50)
        );
    }

    @Test
    void calculatorShouldHaveNoArgsConstructor() {
        assertThat(new Calculator(), notNullValue());
    }

    @Test
    void afterCreationCalculatorShouldHaveZeroedMemoryAndAccumulator() {
        for (int i = 0; i < CalculatorOperations.MEMORY_SIZE; i++) {
            assertThat(calculator.getMemory(i), is(0));
        }
        assertThat(calculator.getAccumulator(), is(0));

        for (int i = 0; i < CalculatorOperations.STACK_SIZE; i++) {
            try {
                calculator.pullAccumulatorFromStack();
                assertThat(calculator.getAccumulator(), is(0));
            } catch (EmptyStackException e) {
                assertThat(e.getClass(), is(EmptyStackException.class));
            } catch (NoSuchElementException e) {
                assertThat(e.getClass(), is(NoSuchElementException.class));
            }
        }
    }

    @Test
    void resetShouldResetCalculatorToItsInitialState() {
        calculator.setAccumulator(10);
        calculator.accumulatorToMemory(15);
        calculator.pushAccumulatorOnStack();

        calculator.reset();

        for (int i = 0; i < CalculatorOperations.MEMORY_SIZE; i++) {
            assertThat(calculator.getMemory(i), is(0));
        }
        assertThat(calculator.getAccumulator(), is(0));

        for (int i = 0; i < CalculatorOperations.STACK_SIZE; i++) {
            try {
                calculator.pullAccumulatorFromStack();
                assertThat(calculator.getAccumulator(), is(0));
            } catch (EmptyStackException e) {
                assertThat(e.getClass(), is(EmptyStackException.class));
            } catch (NoSuchElementException e) {
                assertThat(e.getClass(), is(NoSuchElementException.class));
            }
        }
    }

    @ParameterizedTest
    @MethodSource
    void setAccumulatorShouldSetAccumulatorToGivenValue(int number) {
        calculator.setAccumulator(number);

        assertThat(calculator.getAccumulator(), is(number));
    }

    @ParameterizedTest
    @MethodSource("valuesForAdditionTests")
    void addToAccumulatorShouldAddGivenValueToAccumulator(List<Integer> numbers, int excepted) {
        numbers.forEach(calculator::addToAccumulator);

        assertThat(calculator.getAccumulator(), is(excepted));
    }

    @ParameterizedTest
    @MethodSource("valuesForSubtractionTests")
    void subtractFromAccumulatorShouldSubtractGivenValueFromAccumulator(List<Integer> numbers, int excepted) {
        numbers.forEach(calculator::subtractFromAccumulator);

        assertThat(calculator.getAccumulator(), is(excepted));
    }

    @ParameterizedTest
    @MethodSource
    void accumulatorToMemoryShouldSetMemoryAtGivenIndexToAccumulatorValue(List<Integer> numbers) {
        for (int i = 0; i < numbers.size(); i++) {
            calculator.setAccumulator(numbers.get(i));
            calculator.accumulatorToMemory(i);

            assertThat(calculator.getMemory(i), is(numbers.get(i)));
        }
    }

    @ParameterizedTest
    @MethodSource
    void pushAccumulatorOnStackShouldPushAccumulatorValueOnStack(List<Integer> numbers) {
        numbers.forEach(number -> {
            calculator.setAccumulator(number);
            calculator.pushAccumulatorOnStack();
        });

        numbers = new LinkedList<>(numbers);
        Collections.reverse(numbers);

        numbers.forEach(number -> {
            calculator.pullAccumulatorFromStack();
            assertThat(calculator.getAccumulator(), is(number));
        });
    }

    @ParameterizedTest
    @ValueSource(ints = {-3, -1, 0, 3, 5})
    void exchangeMemoryWithAccumulatorShouldExchangeValueBetweenAccumulatorAndMemoryAtGivenIndex(int number) {
        for (int i = 0; i < CalculatorOperations.MEMORY_SIZE; i++) {
            calculator.setAccumulator(number);

            assertThat(calculator.getMemory(i), is(0));
            assertThat(calculator.getAccumulator(), is(number));

            calculator.exchangeMemoryWithAccumulator(i);

            assertThat(calculator.getMemory(i), is(number));
            assertThat(calculator.getAccumulator(), is(0));
        }
    }

    @ParameterizedTest
    @MethodSource("valuesForAdditionTests")
    void addMemoryToAccumulatorShouldAddMemoryValueToAccumulator(List<Integer> numbers, int excepted) {
        for (int i = 0; i < numbers.size(); i++) {
            calculator.setAccumulator(numbers.get(i));
            calculator.accumulatorToMemory(i);
        }

        calculator.setAccumulator(0);

        for (int i = 0; i < numbers.size(); i++) {
            calculator.addMemoryToAccumulator(i);
        }

        assertThat(calculator.getAccumulator(), is(excepted));
    }

    @ParameterizedTest
    @MethodSource("valuesForSubtractionTests")
    void subtractFromAccumulatorShouldAddMemoryValueToAccumulator(List<Integer> numbers, int excepted) {
        for (int i = 0; i < numbers.size(); i++) {
            calculator.setAccumulator(numbers.get(i));
            calculator.accumulatorToMemory(i);
        }

        calculator.setAccumulator(0);

        for (int i = 0; i < numbers.size(); i++) {
            calculator.subtractMemoryFromAccumulator(i);
        }

        assertThat(calculator.getAccumulator(), is(excepted));
    }
}
