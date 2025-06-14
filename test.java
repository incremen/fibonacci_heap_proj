public class test {
    public static void main(String[] args) {
        FibonacciHeap heap = new FibonacciHeap(2);
        heap.insert(10, "a");
        heap.deleteMin();
    }
}