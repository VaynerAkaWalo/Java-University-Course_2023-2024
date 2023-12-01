import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Calculator extends CalculatorOperations{

    private final Deque<Integer> deque = new ArrayDeque<>(CalculatorOperations.STACK_SIZE);
    private final int[] memory = new int[CalculatorOperations.MEMORY_SIZE];
    private int accumulator = 0;


    @Override
    public void setAccumulator(int value) {
        accumulator = value;
    }

    @Override
    public int getAccumulator() {
        return accumulator;
    }

    @Override
    public int getMemory(int index) {
        return memory[index];
    }

    @Override
    public void accumulatorToMemory(int index) {
        memory[index] = accumulator;
    }

    @Override
    public void addToAccumulator(int value) {
        accumulator += value;
    }

    @Override
    public void subtractFromAccumulator(int value) {
        accumulator -= value;
    }

    @Override
    public void addMemoryToAccumulator(int index) {
        accumulator += memory[index];
    }

    @Override
    public void subtractMemoryFromAccumulator(int index) {
        accumulator -= memory[index];
    }

    @Override
    public void reset() {
        Arrays.fill(memory, 0);
        deque.clear();
        accumulator = 0;
    }

    @Override
    public void exchangeMemoryWithAccumulator(int index) {
        int temp = accumulator;
        accumulator = memory[index];
        memory[index] = temp;
    }

    @Override
    public void pushAccumulatorOnStack() {
        deque.push(accumulator);
    }

    @Override
    public void pullAccumulatorFromStack() {
        accumulator = deque.pop();
    }
}
