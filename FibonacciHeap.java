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
	private int totalLinksCount = 0;
	private int totalCutsCount = 0;

	/**
	 *si
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
		this.totalLinksCount = 0;
		this.totalCutsCount  = 0; 
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
	        insertIntoRootList(node);
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
	
		//doesnt handle the case where it doesnt have anything to delete, btw . i dont think thats an issue tho

	public int deleteMin()
	{
		if (min == null) {
			System.err.println("Error: Cannot delete from an empty heap.");
		}
		if (min == min.next) {
			rootList = null;
		} else {
			if (rootList == min) rootList = min.next;
			deleteNodeFromListLen2_orMore(min);
		}

		// Add all min's children to the root list
		if (min.child != null) {
			HeapNode child = min.child;
			HeapNode start = child;
			do {
				child.parent = null;
				child = child.next;
			} while (child != start);

			// Merge child list into root list
			if (rootList == null) {
				rootList = start;
			} else {
				HeapNode rootPrev = rootList.prev;
				HeapNode childPrev = start.prev;
				rootPrev.next = start;
				start.prev = rootPrev;
				childPrev.next = rootList;
				rootList.prev = childPrev;
			}
		}

		// Consolidate
		ExpandingArray buckets = new ExpandingArray();
		HeapNode current = rootList;

		// If the root list is empty, nothings left
		if (current == null) {
			min = null;
			size--;
			return 0;
		}

		int localLinks = 0;
		HeapNode start = current;
		do {
			HeapNode next = current.next;
			localLinks += linkIntoBuckets(buckets, current);
			current = next;
		} while (current != start);
		totalLinksCount += localLinks;
		updateRootListFromBuckets(buckets);
		size--;
		return localLinks; 

	}

	public static void deleteNodeFromListLen2_orMore(HeapNode node) {
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
     * Iteratively links trees of the same degree in the root list, similar to Ron Goldman's code.
     * Returns the number of links performed.
     */
    private int linkIntoBuckets(ExpandingArray buckets, HeapNode node) {
        int links = 0;
        node.parent = null;
        // Isolate node
        node.next = node;
        node.prev = node;
        if (buckets.size() <= node.rank) {
            buckets.pad_until(node.rank);
        }
        while (buckets.get(node.rank) != null) {
            HeapNode other = buckets.get(node.rank);
            // Always keep the smaller key as the root
            if (node.key > other.key) {
                HeapNode temp = node;
                node = other;
                other = temp;
            }
            // Link other as a child of node
            node.addChild(other);
            buckets.set(node.rank - 1, null);
            links++;
            if (buckets.size() <= node.rank) {
                buckets.pad_until(node.rank);
            }
        }
        buckets.set(node.rank, node);
        return links;
    }

	private void updateRootListFromBuckets(ExpandingArray buckets) {
		HeapNode newRootList = null;
		HeapNode last = null;
		HeapNode newMin = null;
		for (int i = 0; i < buckets.size(); i++) {
			HeapNode node = buckets.get(i);
			if (node == null) continue;
			if (newRootList == null) {
				newRootList = node;
				last = node;
				node.next = node;
				node.prev = node;
			} else {
				// Insert node after last
				node.prev = last;
				node.next = newRootList;
				last.next = node;
				newRootList.prev = node;
				last = node;
			}
			if (newMin == null || node.key < newMin.key) {
				newMin = node;
			}
		}
		this.rootList = newRootList;
		this.min = newMin;
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
		x.key -= diff;

		if (x.parent == null || x.key >= x.parent.key) {

			if (x.key < min.key) {
				min = x;
			}
			return 0;
		}
		//otherwise we need to cut x
		int cuts = 0;

		cutNodeFromItsParent(x);
		cuts++;
		x.parent.rank--;
		insertIntoRootList(x);

		// Cascading cuts
		HeapNode current = x.parent;
		x.parent = null;

		while (current.parent != null) {
			current.lostCount++;

			if (current.lostCount < c) {
				break;
			}
			HeapNode parent = current.parent;

			cutNodeFromItsParent(current);
			cuts++;
			parent.rank--;

			current.parent = null;
			insertIntoRootList(current);
			current.lostCount = 0;
			current = parent;
		}

		if (x.key < min.key) {
			min = x;
		}

		totalCutsCount += cuts;
		return cuts;
	}

	private void insertIntoRootList(HeapNode current) {
		
		if (rootList == null) {
			rootList = current;
			current.next = current;
			current.prev = current;
			return;
		}

		current.next = rootList;
		current.prev = rootList.prev;
		rootList.prev.next = current;
		rootList.prev = current;
	}

    private void cutNodeFromItsParent(HeapNode x) {
        HeapNode p = x.parent;
        if (p.child == x) {
            if (x.next != x) p.child = x.next;
            else p.child = null;
        }
        // unlink from siblings
        x.prev.next = x.next;
        x.next.prev = x.prev;
        // reset x to a solo root
        x.next = x.prev = x;
        x.parent = null;
    }

	/**
	 * 
	 * Delete the x from the heap.
	 * Return the number of links.
	 *
	 */
	public int delete(HeapNode x) {
		if (x == null) {
			return 0;
		}
		int diff;
		if (min != null) {
			diff = x.key - min.key;
			diff = diff + 1;
		} else {
			diff = x.key + 1;
		}
		decreaseKey(x, diff);
		int links = deleteMin();
		return links;
	}


	/**
	 * 
	 * Return the total number of links.
	 * 
	 */
	public int totalLinks()
	{
    	return totalLinksCount;
	}


	/**
	 * 
	 * Return the total number of cuts.
	 * 
	 */
	public int totalCuts()
	{
    	return totalCutsCount;
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

	public int getC() {
        return this.c;
    }
}