import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class BoardTest {

    private static final Board GOAL_BOARD_3D = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
    private static final Board GOAL_BOARD_2D = new Board(new int[][]{{1, 2}, {3, 0}});

    @Test
    public void shouldReturnValidDimension() {
        assertThat(GOAL_BOARD_3D.dimension(), is(equalTo(3)));
    }

    @Test
    public void mutatingInputArrayShouldNotChangeBoard() {
        int[][] blocks = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        Board board = new Board(blocks);

        blocks = new int[][]{{1, 2}, {3, 0}};

        assertThat(board.dimension(), is(equalTo(3)));
    }

    @Test
    public void hammingReturnsValidNumberOfBlocksOutOfPlace() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});

        assertThat(board.hamming(), is(equalTo(5)));
    }

    @Test
    public void hammingReturnsValidNumberOfBlocksOutOfPlaceFor2D() {
        Board board = new Board(new int[][]{{1, 3}, {2, 0}});

        assertThat(board.hamming(), is(equalTo(2)));
    }

    @Test
    public void hammingReturnsZeroWhenAllBlocksAreInPlace() {
        assertThat(GOAL_BOARD_3D.hamming(), is(equalTo(0)));
    }

    @Test
    public void manhattanReturnsValidSumOfDistances() {
        Board board = new Board(new int[][]{{8, 1, 3}, {4, 0, 2}, {7, 6, 5}});

        assertThat(board.manhattan(), is(equalTo(10)));
    }

    @Test
    public void manhattanReturnsZeroWhenAllBlocksAreInPlace() {
        assertThat(GOAL_BOARD_3D.manhattan(), is(equalTo(0)));
    }

    @Test
    public void isGoalReturnsValidValueFor3DBoard() {
        assertThat(GOAL_BOARD_3D.isGoal(), is(true));
    }

    @Test
    public void isGoalReturnsValidValueFor2DBoard() {
        Board board = new Board(new int[][]{{1, 2}, {3, 0}});

        assertThat(board.isGoal(), is(true));
    }

    @Test
    public void twinShouldNotBeEqualToCurrentBoard3D() {
        Board board = GOAL_BOARD_3D.twin();

        assertThat(board.isGoal(), is(false));
        assertThat(board, is(not(equalTo(GOAL_BOARD_3D))));
        assertThat(board.hamming(), is(equalTo(2)));
    }

    @Test
    public void twinShouldNotBeEqualToCurrentBoard2D() {
        Board board = GOAL_BOARD_2D.twin();

        assertThat(board.isGoal(), is(false));
        assertThat(board, is(not(equalTo(GOAL_BOARD_2D))));
        assertThat(board.hamming(), is(equalTo(2)));
    }

    @Test
    public void toStringFor3DImplementedAccordingToSpec() {
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        String result = "3\n" + " 0  1  3 \n" + " 4  2  5 \n" + " 7  8  6 ";

        assertThat(board.toString(), is(equalTo(result)));
    }

    @Test
    public void neighboursReturnsCorrectNumberOfBoards() {
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});

        Iterable<Board> neighbors = board.neighbors();
        int count = 0;
        for (Board neighbor : neighbors) {
            assertThat(neighbor, is(not(equalTo(board))));
            count++;
        }

        assertThat(count, is(equalTo(2)));
    }

    @Test
    public void calculatingNeighboursDoesNotChangeBoard() {
        Board board = new Board(new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}});
        String result = "3\n" + " 0  1  3 \n" + " 4  2  5 \n" + " 7  8  6 ";

        board.neighbors();

        assertThat(board.toString(), is(equalTo(result)));
    }

}