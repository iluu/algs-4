import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DequeTest {

    private Deque<Integer> deque;

    @Before
    public void setUp() {
        deque = new Deque<Integer>();
    }


    @Test(expected = NullPointerException.class)
    public void addFirstThrowsNullPointerExceptionForNullItem() {
        deque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void addLastThrowsNullPointerExceptionForNullItem() {
        deque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstFromEmptyQueueThrowsNoSuchElementException() {
        deque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastFromEmptyQueueThrowsNoSuchElementException() {
        deque.removeLast();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void removeOnIteratorThrowsUnsupportedOperationException() {
        Iterator iterator = deque.iterator();
        iterator.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void nextOnIteratorThrowsNoSuchElementExceptionForEmptyQueue() {
        Iterator iterator = deque.iterator();
        iterator.next();
    }

    @Test
    public void isEmptyIsTrueForEmptyQueue() {
        assertTrue(deque.isEmpty());
    }

    @Test
    public void isEmptyIsFalseForNotEmptyQueue() {
        deque.addFirst(1);
        assertFalse(deque.isEmpty());
    }

    @Test
    public void addFirstAddsItems() {
        deque.addFirst(1);
        deque.addFirst(2);
        assertThat(toList(deque), is(asList(2, 1)));
        assertThat(deque.size(), is(2));
    }

    @Test
    public void addLastAddsItems() {
        deque.addLast(1);
        deque.addLast(2);
        assertThat(toList(deque), is(asList(1, 2)));
    }

    @Test
    public void multipleAddLastAndAddFirstKeepRequestedOrder() {
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(3);
        deque.addLast(4);
        assertThat(toList(deque), is(asList(3, 1, 2, 4)));
    }

    @Test
    public void removeFirstRemovesFirstItem() {
        Deque<Integer> deque = fromList(asList(1, 2, 3, 4));

        deque.removeFirst();
        assertThat(toList(deque), is(asList(2, 3, 4)));
    }

    @Test
    public void removeLastRemovesLastItem() {
        Deque<Integer> deque = fromList(asList(1, 2, 3, 4));

        deque.removeLast();
        assertThat(toList(deque), is(asList(1, 2, 3)));
    }

    @Test
    public void multipleRemoveKeepsRequestedOrder() {
        Deque<Integer> deque = fromList(asList(1, 2, 3, 4, 5));

        deque.removeFirst();
        deque.removeLast();
        deque.removeFirst();
        deque.removeLast();

        assertThat(toList(deque), is(singletonList(3)));
        assertThat(deque.size(), is(1));
    }

    private Deque<Integer> fromList(List<Integer> list) {
        Deque<Integer> deque = new Deque<Integer>();
        for (Integer item : list) {
            deque.addLast(item);
        }
        return deque;
    }

    private List<Integer> toList(Deque<Integer> deque) {
        List<Integer> list = new ArrayList<Integer>();
        for (Integer item : deque) {
            list.add(item);
        }
        return list;
    }
}