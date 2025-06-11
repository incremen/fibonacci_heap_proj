// Simple test for FibonacciHeap insertions and root list traversal

import java.util.ArrayList;

public class FibonacciHeapInsertTest {
    public static void main(String[] args) {
        FibonacciHeap.HeapNode root = new FibonacciHeap.HeapNode();

        ArrayList<FibonacciHeap.HeapNode> nodes = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            FibonacciHeap.HeapNode node = makeNode(i);
            nodes.add(node);
            }
        
        root = nodes.get(0);
        // Set up children 1, 2, 3, 4 as a circular doubly linked list (do NOT include root)
        root.child = nodes.get(1);
        nodes.get(1).next = nodes.get(2); nodes.get(2).prev = nodes.get(1);
        nodes.get(2).next = nodes.get(3); nodes.get(3).prev = nodes.get(2);
        nodes.get(3).next = nodes.get(4); nodes.get(4).prev = nodes.get(3);
        nodes.get(4).next = nodes.get(1); nodes.get(1).prev = nodes.get(4);
        // Set up a child for node 1 (node 5), as a circular list of one
        nodes.get(1).child = nodes.get(5);
        nodes.get(5).next = nodes.get(5); nodes.get(5).prev = nodes.get(5);

        // Set up a child for node 3 (node 6), as a circular list of one
        nodes.get(3).child = nodes.get(6);
        nodes.get(6).next = nodes.get(6); nodes.get(6).prev = nodes.get(6);

        System.out.println("Tree structure:");
        GeneralTreePrinter.printTree(root);

        // Create a smaller tree with root 7 and children 8 and 9 (as brothers)
        FibonacciHeap.HeapNode smallRoot = nodes.get(7);
        smallRoot.child = nodes.get(8);
        nodes.get(8).next = nodes.get(9); nodes.get(9).prev = nodes.get(8);
        nodes.get(9).next = nodes.get(8); nodes.get(8).prev = nodes.get(9);

        System.out.println("Smaller tree structure:");
        GeneralTreePrinter.printTree(smallRoot);

        //now we make both trees brothers and see what happens

        // Create a new parent node (not reusing root)
        FibonacciHeap.HeapNode bigParent = makeNode(10);
        bigParent.child = root;
        // Make root and smallRoot siblings (circular doubly linked list)
        root.next = smallRoot; smallRoot.prev = root;
        smallRoot.next = root; root.prev = smallRoot;

        System.out.println("Combined tree structure:");
        GeneralTreePrinter.printTree(bigParent);

    }
    private static FibonacciHeap.HeapNode makeNode(int key) {
        FibonacciHeap.HeapNode node = new FibonacciHeap.HeapNode();
        node.key = key;
        node.info = Integer.toString(key);
        return node;
    }
}
