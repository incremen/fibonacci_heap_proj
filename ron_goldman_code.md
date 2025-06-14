# Fibonacci Heap: deleteMin and Consolidate Example

```java
public int deleteMin() {
    if (min == null) return 0;
    int linksBefore = totalLinks;

    // Promote all children of min to the root list
    if (min.child != null) {
        // Count number of children promoted
        int childCount = 0;
        HeapNode child = min.child;
        do {
            child.parent = null;
            child.deletedChildren = 0;
            child = child.next;
            childCount++;
        } while (child != min.child);
        mergeListsToRoot(min.child);
        min.child = null;
        numTrees += childCount; // All children become new trees in the root list
    }

    // Remove min from root list
    min.prev.next = min.next;
    min.next.prev = min.prev;
    size--;
    numTrees--;
    if (min == min.next) {
        min = null;
        numTrees = 0;
    } else {
        min = min.next;
        consolidate();
    }
    return totalLinks - linksBefore;
} 

private void consolidate() {
    if (size < 2) {
        return;
    }
    ArrayList<HeapNode> buckets = new ArrayList<>();
    HeapNode current = min;
    if (current == null) {
        return;
    }
    do {
        HeapNode next = current.next;
        current.parent = null;
        current.next = current;
        current.prev = current;
        while (buckets.size() <= current.rank) {
            buckets.add(null);
        }
        while (buckets.get(current.rank) != null) {
            HeapNode other = buckets.get(current.rank);
            if (current.key > other.key) {
                HeapNode temp = current;
                current = other;
                other = temp;
            }
            link(current, other);
            buckets.set(current.rank - 1, null);
            while (buckets.size() <= current.rank) {
                buckets.add(null);
            }
        }
        buckets.set(current.rank, current);
        current = next;
    } while (current != min);

    // Convert ArrayList to array for fromBuckets
    fromBuckets(buckets.toArray(new HeapNode[0]));
}

private void fromBuckets(HeapNode[] buckets) {
    // Rebuild the min pointer and root list from the buckets
    min = null; // Initialize min to null
    this.numTrees = 0; // Reset the number of trees
    for (HeapNode node : buckets) {
        if (node != null) {
            this.numTrees++; // Increment the number of trees
            if (min == null) {
                min = node; // Update min if necessary
                node.next = node; // Initialize next pointer
                node.prev = node; // Initialize previous pointer
            } else {
                node.next = min.next; // Link to the next node
                node.prev = min; // Link to the previous node
                min.next.prev = node; // Update the previous pointer of the next node
                min.next = node; // Update the next pointer of min
            }
            if (node.key < min.key) {
                min = node; // Update min if the current node has a smaller key
            }
        }
    }
}
```
