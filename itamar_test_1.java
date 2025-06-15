import java.util.ArrayList;
import java.util.Collections;

class HeapToCompareWith {
    private ArrayList<Integer> arr = new ArrayList<>();

    public void insert(int key) {
        arr.add(key);
    }

    public Integer findMin() {
        if (arr.isEmpty()) return null;
        return Collections.min(arr);
    }

    public void deleteMin() {
        Integer min = findMin();
        if (min != null) arr.remove(min);
    }

    public void decreaseKey(int oldKey, int diff) {
        int idx = arr.indexOf(oldKey);
        if (idx != -1) {
            arr.set(idx, oldKey - diff);
        }
    }

    public int size() {
        return arr.size();
    }
}
public class itamar_test_1 {
    public static void main(String[] args) {
        itamar_test_1 test = new itamar_test_1();
        // test.testInsertAndFindMin();
        // test.testDeleteMinAndFindMin();
        // test.testInsertDeleteMinRandomOrder();
        // test.testDuplicateKeysAndInterleavedOps();
        // test.testFibonacciHeapVsNaiveHeap();
        // test.testDecreaseKeyAndDeleteMin();
        // test.testHeapStructureAfterOps();
        test.testLargeDecreaseKeyDeleteMinLoop();
        System.out.println("All manual tests completed.");
    }

    public void testInsertAndFindMin() {
        System.out.println("testInsertAndFindMin");
        FibonacciHeap heap = new FibonacciHeap(2);
        checkNull(heap.findMin(), "Empty heap should have null min");
        heap.insert(10, "a");
        checkEquals(10, heap.findMin().key, "Min should be 10");
        heap.insert(5, "b");
        checkEquals(5, heap.findMin().key, "Min should be 5");
        heap.insert(20, "c");
        checkEquals(5, heap.findMin().key, "Min should be 5");
        heap.insert(1, "d");
        checkEquals(1, heap.findMin().key, "Min should be 1");
    }

    public void testDeleteMinAndFindMin() {
        System.out.println("testDeleteMinAndFindMin");
        FibonacciHeap heap = new FibonacciHeap(2);
        heap.insert(10, "a");
        heap.insert(5, "b");
        heap.insert(20, "c");
        heap.insert(1, "d");
        checkEquals(1, heap.findMin().key, "Min should be 1");
        heap.deleteMin();
        checkEquals(5, heap.findMin().key, "Min should be 5");
        heap.deleteMin();
        checkEquals(10, heap.findMin().key, "Min should be 10");
        heap.deleteMin();
        checkEquals(20, heap.findMin().key, "Min should be 20");
        heap.deleteMin();
        checkNull(heap.findMin(), "Heap should be empty");
    }

    public void testInsertDeleteMinRandomOrder() {
        System.out.println("testInsertDeleteMinRandomOrder");
        FibonacciHeap heap = new FibonacciHeap(2);
        int[] values = {7, 3, 17, 24, 1, 9, 2};
        for (int v : values) heap.insert(v, Integer.toString(v));
        checkEquals(1, heap.findMin().key, "Min should be 1");
        heap.deleteMin(); // remove 1
        checkEquals(2, heap.findMin().key, "Min should be 2");
        heap.deleteMin(); // remove 2
        checkEquals(3, heap.findMin().key, "Min should be 3");
        heap.deleteMin(); // remove 3
        checkEquals(7, heap.findMin().key, "Min should be 7");
        heap.deleteMin(); // remove 7
        checkEquals(9, heap.findMin().key, "Min should be 9");
        heap.deleteMin(); // remove 9
        checkEquals(17, heap.findMin().key, "Min should be 17");
        heap.deleteMin(); // remove 17
        checkEquals(24, heap.findMin().key, "Min should be 24");
        heap.deleteMin(); // remove 24
        checkNull(heap.findMin(), "Heap should be empty");
    }

    public void testDuplicateKeysAndInterleavedOps() {
        System.out.println("testDuplicateKeysAndInterleavedOps");
        FibonacciHeap heap = new FibonacciHeap(2);
        heap.insert(4, "a");
        heap.insert(4, "b");
        heap.insert(2, "c");
        heap.insert(8, "d");
        checkEquals(2, heap.findMin().key, "Min should be 2");
        heap.deleteMin(); // remove 2
        checkEquals(4, heap.findMin().key, "Min should be 4");
        heap.deleteMin(); // remove one 4
        checkEquals(4, heap.findMin().key, "Min should be 4");
        heap.insert(1, "e");
        checkEquals(1, heap.findMin().key, "Min should be 1");
        heap.deleteMin(); // remove 1
        checkEquals(4, heap.findMin().key, "Min should be 4");
        heap.deleteMin(); // remove last 4
        checkEquals(8, heap.findMin().key, "Min should be 8");
        heap.deleteMin(); // remove 8
        checkNull(heap.findMin(), "Heap should be empty");
    }

    public void testFibonacciHeapVsNaiveHeap() {
        System.out.println("testFibonacciHeapVsNaiveHeap");
        FibonacciHeap fib = new FibonacciHeap(2);
        HeapToCompareWith naive = new HeapToCompareWith();
        java.util.Random rand = new java.util.Random(42);

        int maxVal = 100;
        for (int i = 1; i <= maxVal; i++) {
            fib.insert(i, Integer.toString(i));
            naive.insert(i);
        }

        // Repeat: compare min, delete min 5 times, insert 3 times, compare min, until empty
        while (naive.size() > 0) {
            // Compare min before deletes
            Integer naiveMin = naive.findMin();
            FibonacciHeap.HeapNode fibMin = fib.findMin();
            boolean bothNull = naiveMin == null && fibMin == null;
            boolean bothEqual = naiveMin != null && fibMin != null && naiveMin.equals(fibMin.key);
            if (bothNull) {
                System.out.println("PASS: Both heaps empty");
            } else if (bothEqual) {
                System.out.println("PASS: min=" + naiveMin);
            } else {
                System.err.println("FAIL: Mismatch min: naive=" + naiveMin + ", fib=" + (fibMin == null ? null : fibMin.key));
            }

            //insert to both:
            int val = rand.nextInt(maxVal * 2) + 1;
            fib.insert(val, Integer.toString(val));
            naive.insert(val);

            // Delete min 5 times
            for (int i = 0; i < 5 && naive.size() > 0; i++) {
                fib.deleteMin();
                naive.deleteMin();
            }


        }
    }

    public void testDecreaseKeyAndDeleteMin() {
        System.out.println("testDecreaseKeyAndDeleteMin");
        FibonacciHeap fib = new FibonacciHeap(2);
        HeapToCompareWith naive = new HeapToCompareWith();
        FibonacciHeap.HeapNode[] fibNodes = new FibonacciHeap.HeapNode[1000];
        int[] naiveKeys = new int[1000];
        // Insert 1000 into both heaps
        for (int i = 0; i < 1000; i++) {
            fibNodes[i] = fib.insert(1000 + i, Integer.toString(1000 + i));
            naive.insert(1000 + i);
            naiveKeys[i] = 1000 + i;
        }
        java.util.Random rand = new java.util.Random(123);
        // Do 100 decreaseKeys
        for (int i = 0; i < 100; i++) {
            int idx = rand.nextInt(1000);
            int diff = rand.nextInt(500) + 1;
            fib.decreaseKey(fibNodes[idx], diff);
            naive.decreaseKey(naiveKeys[idx], diff);
            naiveKeys[idx] -= diff;
        }
        // Do 100 deleteMins, compare mins each time
        for (int i = 0; i < 100; i++) {
            Integer naiveMin = naive.findMin();
            FibonacciHeap.HeapNode fibMin = fib.findMin();
            boolean bothNull = naiveMin == null && fibMin == null;
            boolean bothEqual = naiveMin != null && fibMin != null && naiveMin.equals(fibMin.key);
            if (bothNull) {
                System.out.println("PASS: Both heaps empty");
            } else if (bothEqual) {
                System.out.println("PASS: min=" + naiveMin);
            } else {
                System.err.println("FAIL: Mismatch min: naive=" + naiveMin + ", fib=" + (fibMin == null ? null : fibMin.key));
            }
            fib.deleteMin();
            naive.deleteMin();
        }
    }

    public void testHeapStructureAfterOps() {
        System.out.println("testHeapStructureAfterOps");
        FibonacciHeap heap = new FibonacciHeap(2);
        FibonacciHeap.HeapNode[] nodes = new FibonacciHeap.HeapNode[100];
        // Insert 100 items
        for (int i = 0; i < 100; i++) {
            nodes[i] = heap.insert(1000 + i, Integer.toString(1000 + i));
        }
        // Check structure after insertions
        if (checkHeapStructure(heap)) {
            System.out.println("PASS: Structure valid after insertions");
        } else {
            System.err.println("FAIL: Structure invalid after insertions");
        }
        // Do 20 deleteMins
        for (int i = 0; i < 20; i++) {
            heap.deleteMin();
        }
        // Check structure after deleteMins
        if (checkHeapStructure(heap)) {
            System.out.println("PASS: Structure valid after deleteMins");
        } else {
            System.err.println("FAIL: Structure invalid after deleteMins");
        }
        // Do 20 decreaseKeys
        java.util.Random rand = new java.util.Random(456);
        for (int i = 0; i < 20; i++) {
            int idx = rand.nextInt(100);
            if (nodes[idx] != null && nodes[idx].key > 1) {
                heap.decreaseKey(nodes[idx], rand.nextInt(nodes[idx].key - 1) + 1);
            }
        }
        // Check structure after decreaseKeys
        if (checkHeapStructure(heap)) {
            System.out.println("PASS: Structure valid after decreaseKeys");
        } else {
            System.err.println("FAIL: Structure invalid after decreaseKeys");
        }
    }

    public void testLargeDecreaseKeyDeleteMinLoop() {
        System.out.println("testLargeDecreaseKeyDeleteMinLoop");
        // Adjustable parameters
        int N = 4; // number of inserts
        int DECREASE_KEYS_PER_LOOP = 10;
        int DELETE_MINS_PER_LOOP = 10;

        FibonacciHeap fib = new FibonacciHeap(2);
        HeapToCompareWith naive = new HeapToCompareWith();
        FibonacciHeap.HeapNode[] fibNodes = new FibonacciHeap.HeapNode[N];

        //insert keys
        int[] naiveKeys = new int[N];
        for (int i = 0; i < N; i++) {
            fibNodes[i] = fib.insert(i + 1, Integer.toString(i + 1));
            naive.insert(i + 1);
            naiveKeys[i] = i + 1;
        }
        java.util.Random rand = new java.util.Random(2025);
        int remaining = N;
        while (remaining > 0) {
            // Decrease keys
            for (int i = 0; i < DECREASE_KEYS_PER_LOOP && remaining > 0; i++) {
                int idx = rand.nextInt(N);
                if (fibNodes[idx] != null && fibNodes[idx].key > Integer.MIN_VALUE + 1) {
                    int diff = fibNodes[idx].key + 1 + rand.nextInt(1000); // make negative
                    fib.decreaseKey(fibNodes[idx], diff);
                    naive.decreaseKey(naiveKeys[idx], diff);
                    naiveKeys[idx] -= diff;
                }
            }
            // Delete min
            for (int i = 0; i < DELETE_MINS_PER_LOOP && remaining > 0; i++) {
                Integer naiveMin = naive.findMin();
                FibonacciHeap.HeapNode fibMin = fib.findMin();
                boolean bothNull = naiveMin == null && fibMin == null;
                boolean bothEqual = naiveMin != null && fibMin != null && naiveMin.equals(fibMin.key);
                if (!bothNull && !bothEqual) {
                    System.err.println("FAIL: Mismatch min: naive=" + naiveMin + ", fib=" + (fibMin == null ? null : fibMin.key));
                }
                fib.deleteMin();
                naive.deleteMin();
                remaining--;
            }

            // Check structure after each loop
            if (!checkHeapStructure(fib)) {
                System.err.println("FAIL: Structure invalid after decreaseKeys and deleteMins");
            }
        }
    }

    // Checks the heap structure: min-heap property and lostCount < c for all nodes
    public static boolean checkHeapStructure(FibonacciHeap heap) {
        if (heap == null) return true;
        FibonacciHeap.HeapNode root = heap.getRootList();
        if (root == null) return true;
        int c = heap.getC();
        java.util.HashSet<FibonacciHeap.HeapNode> visited = new java.util.HashSet<>();
        FibonacciHeap.HeapNode current = root;
        do {
            if (!checkSubtree(current, c, visited)) {
                System.err.println("Heap property violated at root node " + current.key);
                return false;
            }
            current = current.next;
        } while (current != root && current != null);
        return true;
    }

    private static boolean checkSubtree(FibonacciHeap.HeapNode node, int c, java.util.HashSet<FibonacciHeap.HeapNode> visited) {
        if (node == null || visited.contains(node)) return true;
        visited.add(node);
        if (node.lostCount >= c) {
            System.err.println("Node " + node.key + " has lostCount >= c: " + node.lostCount);
            return false;
        }
        FibonacciHeap.HeapNode child = node.child;
        if (child != null) {
            FibonacciHeap.HeapNode currChild = child;
            do {
                if (currChild.key < node.key) {
                    System.err.println("Min-heap property violated: parent " + node.key + " > child " + currChild.key);
                    return false;
                }
                if (!checkSubtree(currChild, c, visited)) return false;
                currChild = currChild.next;
            } while (currChild != child && currChild != null);
        }
        return true;
    }

    private void checkEquals(int expected, int actual, String msg) {
        if (expected != actual) {
            System.err.println("FAIL: " + msg + " (expected " + expected + ", got " + actual + ")");
        } else {
            System.out.println("PASS: " + msg);
        }
    }

    private void checkNull(Object obj, String msg) {
        if (obj != null) {
            System.err.println("FAIL: " + msg + " (expected null, got " + obj + ")");
        } else {
            System.out.println("PASS: " + msg);
        }
    }
}
