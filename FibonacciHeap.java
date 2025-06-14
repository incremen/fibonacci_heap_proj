import java.util.ArrayList;

/**
 * FibonacciHeap
 *
 * An implementation of Fibonacci heap over positive integers.
 *
 */
public class FibonacciHeap
{
	public HeapNode min;
	private HeapNode rootList; // pointer to the circular doubly linked list of roots
	private int size;
	private final int c;

	/**
	 *
	 * Constructor to initialize an empty heap.
	 * pre: c >= 2.
	 *
	 */
	public FibonacciHeap(int c)
	{
        this.c = c;
        this.min = null;
        this.rootList = null;
        this.size = 0;
    }

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapNode.
	 *
	 */
	public HeapNode insert(int key, String info) 
	{    
	    HeapNode node = createNewRootNode(key, info);
		
	    if (rootList == null) {
	        node.next = node;
	        node.prev = node;
	        rootList = node;
	        min = node;
	    } else {
	        // Insert node into the root list
	        node.next = rootList;
	        node.prev = rootList.prev;
	        rootList.prev.next = node;
	        rootList.prev = node;
	        if (key < min.key) {
	            min = node;
	        }
	    }
	    size++;
	    return node;
	}

	private HeapNode createNewRootNode(int key, String info) {
		HeapNode node = new HeapNode();
	    node.key = key;
	    node.info = info;
	    node.child = null;
	    node.parent = null;
	    node.rank = 0;
		node.lostCount = 0;
		return node;
	}

	public HeapNode insertKey(int key) {
        return insert(key, Integer.toString(key));
    }

	/**
	 * 
	 * Return the minimal HeapNode, null if empty.
	 *
	 */
	public HeapNode findMin()
	{
		return min; 
	}

	/**
	 * 
	 * Delete the minimal item.
	 * Return the number of links.
	 *
	 */
	public int deleteMin()
	{
		//first, delete min:
		deleteNodeFromList(min);
	
		//add all its children to the root list:
		HeapNode firstChild = min.child;
		HeapNode currentChild = firstChild;
		while (currentChild != firstChild)
			{
				rootList.addChild(currentChild);
				currentChild = currentChild.next;

			}

		
		ExpandingArray buckets = new ExpandingArray();
		HeapNode current = rootList;	
		if (current == null) {
			return 0; 
		}
		HeapNode newRootList = null;
		// Loop over the root list and add to each bucket as per degree
		int totalLinks = 0;
		do {
			HeapNode next = current.next;
			totalLinks += linkIntoBuckets(buckets, current);
			current = next;
		} while (current != rootList);

		return totalLinks; // should be replaced by student code

	}



	/**
	 * 
	 * pre: 0<diff<x.key
	 * 
	 * Decrease the key of x by diff and fix the heap.
	 * Return the number of cuts.
	 * 
	 */
	public int decreaseKey(HeapNode x, int diff) 
	{    
		return 46; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the x from the heap.
	 * Return the number of links.
	 *
	 */
	public int delete(HeapNode x) 
	{    
		return 46; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
		return 46; // should be replaced by student code
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
		return 46; // should be replaced by student code
	}


	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(FibonacciHeap heap2)
	{
		return; // should be replaced by student code   		
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return 46; // should be replaced by student code
	}


	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return 46; // should be replaced by student code
	}

	public HeapNode getRootList() {
		return this.rootList;
	}

	/**
	 * Class implementing a node in a Fibonacci Heap.
	 *  
	 */
	public static class HeapNode{
		public int key;
		public String info;
		public HeapNode child;
		public HeapNode next;
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		public int lostCount;

		public HeapNode() {

		}

		public void addChild(HeapNode childToAdd) {
			if (child == null) {
				child = childToAdd;
				child.prev = childToAdd;
				child.next = childToAdd;

			} else {
				// Insert child into the child list
				childToAdd.prev = child;
				childToAdd.next = child.next;

				child.next.prev = childToAdd;
				child.next = childToAdd;

			}
			childToAdd.parent = this;
			this.rank++;
		}
	}

	public static void deleteNodeFromList(HeapNode node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
	}

	public static class ExpandingArray {
        private ArrayList<HeapNode> array;

        public ExpandingArray() {
            this.array = new ArrayList<>();
        }

        public void set(int i, HeapNode value) {
            pad_until(i);
            array.set(i, value);
        }

        public void pad_until(int i) {
            while (array.size() <= i) {
                array.add(null);
            }
        }

        public HeapNode get(int i) {
            if (i >= array.size()) return null;
            return array.get(i);
        }

        public int size() {
            return array.size();
        }

        public void clearIndex(int i) {
            if (i < array.size()) {
                array.set(i, null);
            }
        }
    }

    /**
     * Recursively links a node into the buckets array, handling repeated collisions.
     * Returns the number of links performed.
     */
    private int linkIntoBuckets(ExpandingArray buckets, HeapNode node) {
        int degree = node.rank;
        buckets.pad_until(degree);
        HeapNode existing = buckets.get(degree);
        if (existing == null) {
            buckets.set(degree, node);
            return 0;
        } else {
            // Link the two trees
            HeapNode smallerRoot = (existing.key < node.key) ? existing : node;
            HeapNode largerRoot = (existing.key < node.key) ? node : existing;
            smallerRoot.addChild(largerRoot);
            deleteNodeFromList(largerRoot);
            buckets.clearIndex(degree);
            // Recursive call to handle further collisions
            return 1 + linkIntoBuckets(buckets, smallerRoot);
        }
    }
}



