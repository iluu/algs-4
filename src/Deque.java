import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = new Node();
    private Node last = new Node();
    private int size = 0;

    /**
     * Construct an empty deque
     */
    public Deque() {
        first.after = last;
        last.before = first;
    }

    /**
     * @return true when empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Insert the item at the front
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }

        Node newNode = new Node(item);
        Node nextRef = first.after;
        nextRef.before = newNode;

        newNode.after = nextRef;
        newNode.before = first;

        first.after = newNode;

        size++;
    }

    /**
     * Insert the item at the end
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("Item cannot be null");
        }

        Node newNode = new Node(item);
        Node prevRef = last.before;

        prevRef.after = newNode;

        newNode.before = prevRef;
        newNode.after = last;

        last.before = newNode;

        size++;
    }

    /**
     * Delete and return the item at the front
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node toRemove = first.after;
        first.after = toRemove.after;
        first.after.before = first;
        return toRemove.value;
    }

    /**
     * Delete and return the item at the end
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node toRemove = last.before;
        last.before = toRemove.before;
        last.before.after = last;
        return toRemove.value;
    }

    /**
     * return an iterator over items in order from front to end
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {
        private Item value;
        private Node before;
        private Node after;

        public Node() {
        }

        public Node(Item item) {
            value = item;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return current.after != last;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.after;
            return current.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
