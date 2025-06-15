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
        int remaining = numberOfInserts;
        while (remaining > 0) {
            // Decrease keys
            for (int i = 0; i < DECREASE_KEYS_PER_LOOP && remaining > 0; i++) {
                int idx = rand.nextInt(numberOfInserts);
                
                if (fibNodes[idx] != null && fibNodes[idx].key > Integer.MIN_VALUE + 1) {
                    int diff = fibNodes[idx].key + 1 + rand.nextInt(1000); // make negative
                    fib.decreaseKey(fibNodes[idx], diff);
                }
            }
            System.out.println("size: " + fib.size());

            // printHeap.printFibonacciHeap(fib);

            fib.deleteMin();


            // for (int i = 0; i < DELETE_MINS_PER_LOOP && remaining > 0; i++) {
            //     fib.deleteMin();
            //     remaining--;
            // }
            // // Print heap after each loop
            System.out.println("Heap after loop:");
            printHeap.printFibonacciHeap(fib);
        }
    }
}