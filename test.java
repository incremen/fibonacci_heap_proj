public class test {
    public static void main(String[] args) {
        int numberOfInserts = 4; 
        int DECREASE_KEYS_PER_LOOP = 10;

        FibonacciHeap fib = new FibonacciHeap(2);
        FibonacciHeap.HeapNode[] fibNodes = new FibonacciHeap.HeapNode[numberOfInserts];

        // Insert keys
        for (int i = 0; i < numberOfInserts; i++) {
            fibNodes[i] = fib.insert(i + 1, Integer.toString(i + 1));
        }
        java.util.Random rand = new java.util.Random(2025);


        System.out.println("After inserts:");
        printHeap.printFibonacciHeap(fib);

        System.out.println("Now going to decrease keys");
        for (int i = 0; i < DECREASE_KEYS_PER_LOOP; i++) {
            int idx = rand.nextInt(numberOfInserts);

            int diff = 100;
            fib.decreaseKey(fibNodes[idx], diff);
        }
        System.out.println("Heap after loop:");
        printHeap.printFibonacciHeap(fib);

        System.out.println("Now deleting min");
        fib.deleteMin();
        printHeap.printFibonacciHeap(fib);
    }
}