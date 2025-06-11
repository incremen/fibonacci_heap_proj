// Simple test for FibonacciHeap insertions and root list traversal
public class FibonacciHeapInsertTest {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap(2);
        heap.insertKey(10);
        heap.insertKey(5);
        heap.insertKey(20);
        heap.insertKey(7);

        // Traverse the root list and print keys and info
        System.out.println("Root list after inserts:");
        FibonacciHeap.HeapNode start = heap.getRootList();
        if (start == null) {
            System.out.println("Root list is empty.");
            return;
        }
        FibonacciHeap.HeapNode curr = start;
        do {
            System.out.println("key: " + curr.key + ", info: " + curr.info);
            curr = curr.next;
        } while (curr != start);

        // Print mini
        FibonacciHeap.HeapNode min = heap.min;
        System.out.println("Min node: key=" + (min != null ? min.key : "null") + ", info=" + (min != null ? min.info : "null"));
    }
}
