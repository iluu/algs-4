public class Subset {
    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        final RandomizedQueue<String> queue = new RandomizedQueue<String>();

        String item;
        while ((item = StdIn.readString()) != null) {
            if (queue.size() > k) {
                queue.dequeue();
            }
            queue.enqueue(item);
        }

        for (String i : queue) {
            StdOut.println(i);
        }
    }

}
