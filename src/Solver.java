import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private Step currentNode;

    private static class Step {
        Board board;
        Step previous;
        int moves;
        int priority;

        Step(Board board, Step previous, int moves) {
            this.board = board;
            this.previous = previous;
            this.moves = moves;
            this.priority = board.manhattan() + moves;
        }
    }

    private MinPQ<Step> twinGameTree;
    private MinPQ<Step> gameTree;
    private Board initial;

    /**
     * find a solution to the initial board (using the A* algorithm)
     */
    public Solver(Board initial) {
        this.initial = initial;
        this.gameTree = new MinPQ<Step>(comparator());
        this.twinGameTree = new MinPQ<Step>(comparator());

        findSolution();
    }

    private void findSolution() {
        currentNode = new Step(initial, null, 0);
        Step twinNode = new Step(initial.twin(), null, 0);

        while (!currentNode.board.isGoal() && !twinNode.board.isGoal()) {
            addToQueue(gameTree, currentNode);
            currentNode = gameTree.delMin();

            addToQueue(twinGameTree, twinNode);
            twinNode = twinGameTree.delMin();
        }

        if (!currentNode.board.isGoal()) {
            currentNode = null;
        }
    }

    private static void addToQueue(MinPQ<Step> tree, Step step) {
        for (Board board : step.board.neighbors()) {
            if (step.previous == null || !board.equals(step.previous.board)) {
                tree.insert(new Step(board, step, step.moves + 1));
            }
        }
    }

    private Comparator<Step> comparator() {
        return new Comparator<Step>() {
            @Override
            public int compare(Step first, Step second) {
                return first.priority < second.priority ? -1 : (first.priority == second.priority ? 0 : 1);
            }
        };
    }

    /**
     * is the initial board solvable?
     */
    public boolean isSolvable() {
        return currentNode != null;
    }

    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        return isSolvable() ? currentNode.moves : -1;
    }

    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (isSolvable()) {
            List<Board> result = new ArrayList<Board>();
            Step current = currentNode;
            while (current != null) {
                result.add(0, current.board);
                current = current.previous;
            }
            return result;
        } else {
            return null;
        }

    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
