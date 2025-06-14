public class FibonacciHeapTest {
    public static void main(String[] args) {
        FibonacciHeapTest test = new FibonacciHeapTest();
        test.testInsertAndFindMin();
        test.testDeleteMinAndFindMin();
        test.testInsertDeleteMinRandomOrder();
        test.testDuplicateKeysAndInterleavedOps();
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
