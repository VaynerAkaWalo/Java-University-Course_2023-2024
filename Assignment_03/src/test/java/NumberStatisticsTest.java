import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NumberStatisticsTest {

    NumberStatistics numberStatistics = new NumberStatistics();

    @Test
    void simpleCase() {
        numberStatistics.sideLength(5);

        Map<Integer, Set<Position>> neighbours = Map.of(
                1, Set.of(new Position(0, 0), new Position(2, 1), new Position(2, 3)),
                2, Set.of(new Position(1, 3)),
                3, Set.of(new Position(0, 3)),
                4, Set.of(new Position(3, 2), new Position(4, 1))
        );

        numberStatistics.addNumbers(neighbours);

        Map<Integer, Map<Integer, Integer>> map = numberStatistics.neighbours(new Position(2, 2), 8);

        System.out.println(map);
    }

}