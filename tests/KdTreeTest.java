import edu.princeton.cs.algs4.Point2D;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class KdTreeTest {

    @Test
    public void shouldInsertNodesCorrectly() {
        final KdTree tree = new KdTree();

        final Point2D point1 = new Point2D(0.7, 0.2);
        tree.insert(point1);
        assertThat(tree.contains(point1), is(true));

        final Point2D point2 = new Point2D(0.5, 0.4);
        tree.insert(point2);
        assertThat(tree.contains(point2), is(true));

        final Point2D point3 = new Point2D(0.2, 0.3);
        tree.insert(point3);
        assertThat(tree.contains(point3), is(true));
        assertThat(tree.size(), is(equalTo(3)));
    }

    @Test
    public void shouldNotGrowSizeWhenAddingExistingPoint() {
        final KdTree tree = new KdTree();

        final Point2D point1 = new Point2D(0.7, 0.2);
        tree.insert(point1);
        tree.insert(point1);

        assertThat(tree.size(), is(equalTo(1)));
    }

    @Test
    public void shouldInsertNodeBeReflectedInTreeSize() {
        final KdTree tree = new KdTree();

        final Point2D point1 = new Point2D(0.7, 0.2);
        tree.insert(point1);
        assertThat(tree.size(), is(equalTo(1)));
    }

    @Test
    public void shouldReturnValidValuesFromContains() {
        final KdTree tree = new KdTree();
        tree.insert(new Point2D(1.0, 1.0));
        assertThat(tree.contains(new Point2D(0.0, 0.0)), is(false));

        tree.insert(new Point2D(0.0, 0.0));
        assertThat(tree.contains(new Point2D(1.0, 0.0)), is(false));
    }
}