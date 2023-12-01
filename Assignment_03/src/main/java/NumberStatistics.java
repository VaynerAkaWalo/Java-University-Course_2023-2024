import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NumberStatistics implements Statistics {

    private Matrix matrix;

    @Override
    public void sideLength(int length) {
        this.matrix = new Matrix(length);
    }

    @Override
    public void addNumbers(Map<Integer, Set<Position>> numberPositions) {
        for (Map.Entry<Integer, Set<Position>> entry : numberPositions.entrySet()) {
            for (Position position : entry.getValue()) {
                matrix.set(position, entry.getKey());
            }
        }
    }

    @Override
    public Map<Integer, Map<Integer, Integer>> neighbours(Position position, int maxDistanceSquared) {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        int maxDistance = (int) Math.floor(Math.sqrt(maxDistanceSquared));

        for (int i = position.col() - maxDistance; i <= position.col() + maxDistance; i++) {
            for (int j = position.row() - maxDistance; j <= position.row() + maxDistance; j++) {
                int num = matrix.get(i, j);
                if (num != 0 && squaredDistance(position, i, j) <= maxDistanceSquared ) {
                    int sq = squaredDistance(position, i, j);
                    if (!map.containsKey(num)) {
                        map.put(num, new HashMap<>());
                    }
                    map.get(num).merge(sq, 1, Integer::sum);
                }
            }
        }
        return map;
    }

    private static int squaredDistance(Position position, int x, int y) {
        return ((int) Math.pow(Math.abs(x - position.col()), 2) + (int) Math.pow(Math.abs(y - position.row()), 2));
    }

    static class Matrix {
        private final int[][] board;

        public Matrix(int length) {
            this.board = new int[length][length];
        }

        public int get(int x, int y) {
            return board[index(x)][index(y)];
        }

        public void set(Position position, int value) {
            board[index(position.col())][index(position.row())] = value;
        }

        public int index(int i) {
            if (i == 0) return 0;
            if (i > 0) {
                return i % board.length;
            }
            return board.length - (Math.abs(i) % board.length);
        }
    }
}
