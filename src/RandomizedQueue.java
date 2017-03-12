import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    /**
     * Construct an empty randomized queue
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    /**
     * @return true when empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @return number of items in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Add the item
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("Item can't be null");
        }
        if (size == items.length) resize(2 * items.length);
        items[size++] = item;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        System.arraycopy(items, 0, temp, 0, size);
        items = temp;
    }

    /**
     * Remove and return random item
     */
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomPosition = StdRandom.uniform(size);
        Item value = items[randomPosition];

        size--;
        items[randomPosition] = items[size];
        items[size] = null;

        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return value;
    }

    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int position = StdRandom.uniform(size);
        return items[position];
    }

    /**
     * return an independent iterator over items in random order
     */
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] iterItems;
        private int currentIdx;

        RandomizedQueueIterator() {
            iterItems = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iterItems[i] = items[i];
            }

            StdRandom.shuffle(iterItems);
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

            return iterItems[currentIdx++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
