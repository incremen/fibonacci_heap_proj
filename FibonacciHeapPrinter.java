// Utility to print a clear representation of a Fibonacci Heap
public class FibonacciHeapPrinter {
    public static void printHeap(FibonacciHeap heap) {
        if (heap == null || heap.getRootList() == null) {
            System.out.println("Heap is empty.");
            return;
        }

        System.out.println("Fibonacci Heap Structure:");
        FibonacciHeap.HeapNode root = heap.getRootList();
        FibonacciHeap.HeapNode currTree = root;
        int treeNum = 1;
        do {
            System.out.print("Tree " + treeNum + ": ");
            printTree(currTree, 0, "");
            System.out.println();
            currTree = currTree.next;
            treeNum++;
        } while (currTree != root);

        // Print minimum
        FibonacciHeap.HeapNode min = heap.min;
        System.out.println("Min node: key=" + (min != null ? min.key : "null") + ", info=" + (min != null ? min.info : "null"));
    }

    // Helper to print a tree rooted at node, with indentation for children
    private static void printTree(FibonacciHeap.HeapNode node, int depth, String prefix) {
        if (node == null) return;
        // Print this node
        System.out.print(prefix + node.key);
        // Print children if any
        if (node.child != null) {
            System.out.print(" [");
            FibonacciHeap.HeapNode child = node.child;
            boolean first = true;
            do {
                if (!first) System.out.print(", ");
                printTree(child, depth + 1, "");
                child = child.next;
                first = false;
            } while (child != node.child);
            System.out.print("]");
        }
    }
}
