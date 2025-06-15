public class test {
    public static void main(String[] args) {
        System.out.println("testLargeDecreaseKeyDeleteMinLoop (clean version)");
        int N = 4; // number of inserts
        int DECREASE_KEYS = 10;

        FibonacciHeap fib = new FibonacciHeap(2);
        FibonacciHeap.HeapNode[] fibNodes = new FibonacciHeap.HeapNode[N];

        // Insert keys
        System.out.println("Inserting keys:");
        for (int i = 0; i < N; i++) {
            fibNodes[i] = fib.insert(i + 1, Integer.toString(i + 1));
            System.out.println("Inserted key: " + (i + 1));
        }
        printHeap.printFibonacciHeap(fib);

        // Decrease keys
        System.out.println("\nDecreasing keys:");
        java.util.Random rand = new java.util.Random(2025);
        for (int i = 0; i < DECREASE_KEYS; i++) {
            int idx = rand.nextInt(N);
            if (fibNodes[idx] != null && fibNodes[idx].key > Integer.MIN_VALUE + 1) {
                int diff = fibNodes[idx].key + 1 + rand.nextInt(1000); // make negative
                fib.decreaseKey(fibNodes[idx], diff);
                System.out.println("Decreased key at idx " + idx + " by " + diff );
                System.out.println("Heap now: ");
                printHeap.printFibonacciHeap(fib);
                System.out.println("Min now: " + fib.findMin().key +"\n\n\n");
            }
        }
        printHeap.printFibonacciHeap(fib);
    }
}