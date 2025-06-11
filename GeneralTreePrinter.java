// Utility to print a clear, pretty representation of a general tree (not just binary)
// for a single FibonacciHeap.HeapNode (i.e., a tree in the heap)
import java.util.*;

public class GeneralTreePrinter {
    /**
     * Prints a pretty representation of a tree rooted at the given node.
     * Each node can have any number of children (not just binary trees).
     * @param root The root node of the tree to print
     */
    public static void printTree(FibonacciHeap.HeapNode root) {
        List<String> lines = treeRepr(root);
        for (String line : lines) {
            System.out.println(line);
        }
    }

    // Main entry: returns a list of strings for the pretty-printed tree
    private static List<String> treeRepr(FibonacciHeap.HeapNode node) {
        if (node == null) return new ArrayList<>(List.of("#"));
        String rootStr = nodeString(node);
        List<List<String>> childRows = getChildRows(node);
        if (childRows.isEmpty()) {
            return new ArrayList<>(List.of(rootStr));
        }
        int[] widths = getWidths(childRows);
        int maxHeight = getMaxHeight(childRows);
        padChildRows(childRows, maxHeight);
        int totalWidth = Arrays.stream(widths).sum() + (childRows.size() - 1) * 3;
        int rootPos = totalWidth / 2 - rootStr.length() / 2;
        String rootLine = getRootLine(rootStr, rootPos);
        String connector = getConnector(childRows);
        List<String> result = new ArrayList<>();
        result.add(rootLine);
        result.add(connector);
        for (int row = 0; row < maxHeight; row++) {
            result.add(getChildRowLine(childRows, row));
        }
        return result;
    }

    private static String nodeString(FibonacciHeap.HeapNode node) {
        return Integer.toString(node.key);
    }

    private static List<List<String>> getChildRows(FibonacciHeap.HeapNode node) {
        List<List<String>> childRows = new ArrayList<>();
        if (node.child != null) {
            FibonacciHeap.HeapNode child = node.child;
            do {
                childRows.add(treeRepr(child));
                child = child.next;
            } while (child != node.child);
        }
        return childRows;
    }

    private static int[] getWidths(List<List<String>> childRows) {
        int[] widths = new int[childRows.size()];
        for (int i = 0; i < childRows.size(); i++) {
            widths[i] = childRows.get(i).get(0).length();
        }
        return widths;
    }

    private static int getMaxHeight(List<List<String>> childRows) {
        int maxHeight = 0;
        for (List<String> row : childRows) {
            maxHeight = Math.max(maxHeight, row.size());
        }
        return maxHeight;
    }

    private static void padChildRows(List<List<String>> childRows, int maxHeight) {
        for (List<String> row : childRows) {
            while (row.size() < maxHeight) {
                row.add(" ".repeat(row.get(0).length()));
            }
        }
    }

    private static String getRootLine(String rootStr, int rootPos) {
        return " ".repeat(Math.max(0, rootPos)) + rootStr;
    }

    private static String getConnector(List<List<String>> childRows) {
        StringBuilder connector = new StringBuilder();
        for (int i = 0; i < childRows.size(); i++) {
            if (i > 0) connector.append("   ");
            connector.append("|");
        }
        return connector.toString();
    }

    private static String getChildRowLine(List<List<String>> childRows, int row) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < childRows.size(); i++) {
            if (i > 0) line.append("   ");
            line.append(childRows.get(i).get(row));
        }
        return line.toString();
    }
}
