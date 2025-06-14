
public class printHeap {
    // Print the Fibonacci Heap tree structure starting from a given node
    public static void printFibonacciHeap(FibonacciHeap heap) {
        FibonacciHeap.HeapNode min = heap.min;
        if (min == null) {
            System.out.println("(empty heap)");
            return;
        }
        System.out.println("Fibonacci Heap:");
        FibonacciHeap.HeapNode start = min;
        FibonacciHeap.HeapNode curr = min;
        do {
            printTree(curr, "", true);
            curr = curr.next;
        } while (curr != start);
    }

    // Print a single tree rooted at 'root'
    private static void printTree(FibonacciHeap.HeapNode root, String prefix, boolean isLeft) {
        if (root != null) {
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.key);
            if (root.child != null) {
                FibonacciHeap.HeapNode child = root.child;
                FibonacciHeap.HeapNode start = child;
                do {
                    printTree(child, prefix + (isLeft ? "│   " : "    "), true);
                    child = child.next;
                } while (child != start);
            }
        }
    }

    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap(2);

        for (int i = 1; i <= 100; i++) {
            heap.insert(i, null);
        }
        printFibonacciHeap(heap);
        System.out.println("\n\nNow deleting min");
        heap.deleteMin();
        printFibonacciHeap(heap);
        System.out.println("\n\nNow deleting min");
        heap.deleteMin();
        printFibonacciHeap(heap);
    }
}
 