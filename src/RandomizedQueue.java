import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first;
    private Node last;

    private int size;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        first = new Node();
        last = new Node();
        first.after = last;
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
     * Add items
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Item can't be null");
        }

        Node newNode = new Node(item);
        Node oldNextNode = first.after;
        first.after = newNode;
        newNode.after = oldNextNode;
        size++;
    }

    private Node getNodeAtPosition(int position) {
        int currentPosition = 0;
        Node currentNode = first;
        while (currentPosition < position) {
            currentNode = currentNode.after;
            currentPosition++;
        }
        return currentNode;
    }

    /**
     * Delete and return random item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomPosition = StdRandom.uniform(size);
        return removeNodeAtPosition(randomPosition);
    }

    private Item removeNodeAtPosition(int position) {
        Node oneBefore = getNodeAtPosition(position - 1);
        Node nodeToRemove = oneBefore.after;
        oneBefore.after = nodeToRemove.after;
        size--;
        return nodeToRemove.value;
    }

    /**
     * return (but do not delete) a random item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int position = StdRandom.uniform(1, size + 1);
        return getNodeAtPosition(position).value;
    }

    /**
     * return an independent iterator over items in random order
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class Node {
        private Node after;
        private Item value;

        Node() {
        }

        Node(Item val) {
            value = val;
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] items;
        private int currentIdx;

        RandomizedQueueIterator() {
            items = (Item[]) new Object[size];
            Node currentNode = first.after;
            for (int i = 0; i < size; i++) {
                items[i] = currentNode.value;
                currentNode = currentNode.after;
            }

            StdRandom.shuffle(items);
            currentIdx = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIdx < size;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return items[currentIdx++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
