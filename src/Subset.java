public class Subset {


    public static void main(String[] args) {
        final int k = Integer.parseInt(args[0]);
        final RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
            if (queue.size() > k) {
                queue.dequeue();
            }
        }

        for (String i : queue) {
            StdOut.println(i);
        }
    }

}
