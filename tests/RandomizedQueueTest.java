import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RandomizedQueueTest {

    private RandomizedQueue<Integer> queue;

    @Before
    public void setUp() {
        queue = new RandomizedQueue<Integer>();
    }

    @Test(expected = NullPointerException.class)
    public void enqueueNullItemThrowsNullPointerException() {
        queue.enqueue(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void dequeueOnEmptyQueueThrowsNoSuchElementException() {
        queue.dequeue();
    }

    @Test(expected = NoSuchElementException.class)
    public void sampleOnEmptyQueueThrowsNoSuchElementException() {
        queue.sample();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeOnIteratorThrowsOperationNotSupportedException() {
        Iterator<Integer> iter = queue.iterator();
        iter.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void nextOnEmptyIteratorThrowsNoSuchElementException() {
        Iterator<Integer> iter = queue.iterator();
        iter.next();
    }

    @Test
    public void isEmptyIsTrueForNewQueue() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void isEmptyIsFalseForNotEmptyQueue() {
        queue.enqueue(1);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void enqueueAddsItemsInRandomOrder() {
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertThat(toList(queue), hasItems(1, 2, 3));
    }

    @Test
    public void dequeueRemovesRandomItems() {
        RandomizedQueue<Integer> queue = fromList(Arrays.asList(1, 2, 3, 4, 5));
        Integer removed = queue.dequeue();

        assertThat(queue.size(), is(4));
        assertThat(toList(queue), not(hasItem(removed)));
    }

    @Test
    public void sampleReturnsRandomItem() {
        RandomizedQueue<Integer> queue = fromList(Arrays.asList(1, 2, 3, 4, 5));
        Integer sample = queue.sample();

        assertThat(queue.size(), is(5));
        assertThat(toList(queue), hasItem(sample));
    }

    private RandomizedQueue<Integer> fromList(List<Integer> list) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (Integer item : list) {
            queue.enqueue(item);
        }
        return queue;
    }

    private List<Integer> toList(RandomizedQueue<Integer> queue) {
        List<Integer> list = new ArrayList<Integer>();
        for (Integer item : queue) {
            list.add(item);
        }
        return list;
    }
}