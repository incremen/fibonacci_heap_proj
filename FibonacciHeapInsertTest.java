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
        // Set up children 1, 2, 3 as a circular doubly linked list
        root.child = nodes.get(1);
        nodes.get(1).next = nodes.get(2); nodes.get(2).prev = nodes.get(1);
        nodes.get(2).next = nodes.get(3); nodes.get(3).prev = nodes.get(2);
        nodes.get(3).next = nodes.get(1); nodes.get(1).prev = nodes.get(3);
        // Set up a child for node 1 (node 4), as a circular list of one
        nodes.get(1).child = nodes.get(4);
        nodes.get(4).next = nodes.get(4); nodes.get(4).prev = nodes.get(4);
    
        System.out.println("Tree structure:");
        GeneralTreePrinter.printTree(root);
    }
    private static FibonacciHeap.HeapNode makeNode(int key) {
        FibonacciHeap.HeapNode node = new FibonacciHeap.HeapNode();
        node.key = key;
        node.info = Integer.toString(key);
        return node;
    }
}
