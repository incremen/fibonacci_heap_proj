public class test {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap(2);
        System.out.println("After creation:");
        printHeap(heap);

        heap.insert(10, "a");
        System.out.println("After insert(10, 'a'):");
        printHeap(heap);

        heap.insert(5, "b");
        System.out.println("After insert(5, 'b'):");
        printHeap(heap);

        heap.insert(20, "c");
        System.out.println("After insert(20, 'c'):");
        printHeap(heap);

        heap.insert(1, "d");
        System.out.println("After insert(1, 'd'):");
        printHeap(heap);

        printMin(heap);
        heap.deleteMin();
        System.out.println("After deleteMin():");
        printHeap(heap);

        printMin(heap);
        heap.deleteMin();
        System.out.println("After deleteMin():");
        printHeap(heap);

        printMin(heap);
        heap.deleteMin();
        System.out.println("After deleteMin():");
        printHeap(heap);

        printMin(heap);
        heap.deleteMin();
        System.out.println("After deleteMin():");
        printHeap(heap);

        printMin(heap);
    }

    static void printHeap(FibonacciHeap heap) {
        FibonacciHeap.HeapNode root = heap.getRootList();
        if (root == null) {
            System.out.println("Heap is empty.");
            return;
        }
        FibonacciHeap.HeapNode curr = root;
        int treeNum = 1;
        do {
            System.out.println("Tree " + treeNum + ":");
            printTree(curr, "  ");
            curr = curr.next;
            treeNum++;  
        } while (curr != root);

        System.out.println("\n\n");
    }

    static void printTree(FibonacciHeap.HeapNode node, String indent) {
        if (node == null) return;
        System.out.println(indent + "Node key=" + node.key + ", info=" + node.info + ", rank=" + node.rank);
        if (node.child != null) {
            FibonacciHeap.HeapNode child = node.child;
            do {
                printTree(child, indent + "  ");
                child = child.next;
            } while (child != node.child);
        }
    }

    static void printMin(FibonacciHeap heap) {
        FibonacciHeap.HeapNode min = heap.findMin();
        if (min == null) {
            System.out.println("Min: null");
        } else {
            System.out.println("Min: key=" + min.key + ", info=" + min.info);
        }
    }
}