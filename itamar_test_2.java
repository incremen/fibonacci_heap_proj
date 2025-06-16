import java.util.ArrayList;
import java.util.Collections;

class NaiveHeap {
    private ArrayList<Integer> arr = new ArrayList<>();

    public void insert(int key) {
        arr.add(key);
    }

    public Integer findMin() {
        return Collections.min(arr);
    }

    public void deleteMin() {
        Integer min = findMin();
        arr.remove(min);
    }

    public void decreaseKey(int oldKey, int diff) {
        int idx = arr.indexOf(oldKey);
        arr.set(idx, oldKey - diff);
    }

    public int size() {
        return arr.size();
    }
}

public class itamar_test_2 {
    public void testLargeDecreaseKeyDeleteMinLoop() {
        int N = 100_000;
        int OPS = 1000;
        FibonacciHeap fib = new FibonacciHeap(2);
        NaiveHeap naiveHeap = new NaiveHeap();
        ArrayList<FibonacciHeap.HeapNode> fibNodes = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            fibNodes.add(fib.insert(i + 1, Integer.toString(i + 1)));
            naiveHeap.insert(i + 1);
        }
        java.util.Random rand = new java.util.Random(2025);
        while (!fibNodes.isEmpty()) {
            // 1. Do OPS decrease keys randomly on alive nodes
            for (int i = 0; i < OPS; i++) {
                if (fibNodes.isEmpty()) break;
                int idx = rand.nextInt(fibNodes.size());
                FibonacciHeap.HeapNode node = fibNodes.get(idx);
                if (node != null && node.key > Integer.MIN_VALUE + 1) {
                    int diff = 100_000;
                    fib.decreaseKey(node, diff);
                    naiveHeap.decreaseKey(node.key + diff, diff); // node.key + diff is the old key
                }
            }
            // 2. Do OPS delete mins
            for (int i = 0; i < OPS && !fibNodes.isEmpty(); i++) {
                Integer naiveMin = naiveHeap.findMin();
                FibonacciHeap.HeapNode fibMin = fib.findMin();
                boolean bothNull = naiveMin == null && fibMin == null;
                boolean bothEqual = naiveMin != null && fibMin != null && naiveMin.equals(fibMin.key);
                if (!bothNull && !bothEqual) {
                    System.err.println("FAIL: Mismatch min: naive=" + naiveMin + ", fib=" + (fibMin == null ? null : fibMin.key));
                    System.err.println("Heap size: " + fibNodes.size());
                    printHeap.printFibonacciHeap(fib);
                    System.err.println();
                }
                // Remove the deleted min from fibNodes by value
                if (fibMin != null) {
                    fibNodes.remove(fibMin);
                }
                fib.deleteMin();
                naiveHeap.deleteMin();
            }
        }
    }
}
