import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {

    private int[][] board;

    /**
     * construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.board = copyBoard(blocks);
    }

    private int[][] copyBoard(int[][] blocks) {
        int[][] newBoard = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            newBoard[i] = new int[blocks.length];
            System.arraycopy(blocks[i], 0, newBoard[i], 0, blocks.length);
        }
        return newBoard;
    }

    /**
     * board dimension n
     */
    public int dimension() {
        return board.length;
    }

    /**
     * number of blocks out of place
     */
    public int hamming() {
        int result = 0;
        int currentValue = 1;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0 && board[i][j] != currentValue) {
                    result++;
                }
                currentValue++;
            }
        }
        return result;
    }

    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        int result = 0;
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] != 0) {
                    result += distanceToGoal(i, j);
                }
            }
        }
        return result;
    }

    private int distanceToGoal(int row, int col) {
        int value = board[row][col];
        int rowGoal = (value - 1) / dimension();
        int colGoal = (value - 1) % dimension();

        return Math.abs(rowGoal - row) + Math.abs(colGoal - col);
    }

    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    /**
     * a board that is obtained by exchanging any pair of blocks
     */
    public Board twin() {
        final Random random = new Random();

        int[] dim = findValidBlockDimensions(random);
        int[][] newBoard = copyBoard(board);

        swap(newBoard, dim[0], dim[1], dim[0], dim[2]);

        return new Board(newBoard);
    }

    private int[] findValidBlockDimensions(Random random) {
        int row = random.nextInt(dimension());
        int col1 = random.nextInt(dimension() - 1);
        int col2 = col1 + 1;
        if (board[row][col1] == 0 || board[row][col2] == 0) {
            if (row == dimension() - 1) {
                row = row - 1;
            } else {
                row = row + 1;
            }
        }

        return new int[]{row, col1, col2};
    }

    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Set<Board> result = new HashSet<Board>();
        int[] emptyBlock = findEmptyBlock();

        if (emptyBlock[0] != 0) {
            int[][] newBoard = copyBoard(board);
            swap(newBoard, emptyBlock[0], emptyBlock[1], emptyBlock[0] - 1, emptyBlock[1]);
            result.add(new Board(newBoard));
        }

        if (emptyBlock[1] != 0) {
            int[][] newBoard = copyBoard(board);
            swap(newBoard, emptyBlock[0], emptyBlock[1], emptyBlock[0], emptyBlock[1] - 1);
            result.add(new Board(newBoard));
        }

        if (emptyBlock[0] != dimension() - 1) {
            int[][] newBoard = copyBoard(board);
            swap(newBoard, emptyBlock[0], emptyBlock[1], emptyBlock[0] + 1, emptyBlock[1]);
            result.add(new Board(newBoard));
        }

        if (emptyBlock[1] != dimension() - 1) {
            int[][] newBoard = copyBoard(board);
            swap(newBoard, emptyBlock[0], emptyBlock[1], emptyBlock[0], emptyBlock[1] + 1);
            result.add(new Board(newBoard));
        }
        return result;
    }

    private int[] findEmptyBlock() {
        for (int i = 0; i < dimension(); i++) {
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    private void swap(int[][] blocks, int row1, int col1, int row2, int col2) {
        int temp = blocks[row1][col1];
        blocks[row1][col1] = blocks[row2][col2];
        blocks[row2][col2] = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return Arrays.deepEquals(board, board1.board);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(dimension());
        for (int i = 0; i < dimension(); i++) {
            sb.append("\n");
            for (int j = 0; j < dimension(); j++) {
                if (board[i][j] < 10) {
                    sb.append(" ");
                }
                sb.append(board[i][j]).append(" ");
            }
        }
        return sb.toString();
    }

}
