
public class Test {
	static void insertKeyArray(BinomialHeap heap, int[] keys) {
        for (int key : keys) {
            heap.insert(key, String.valueOf(key));
        }
    }
	
	public static void main(String[] args) {
		//here you create the heap
		 BinomialHeap heap = new BinomialHeap();
	     insertKeyArray(heap, new int[]{15, 35, 20, 31, 40, 43, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 100, 120});
		 //heap.printHeap();
	    // BinomialHeap.HeapNode root = heap.last;
		//here you draw
		HeapGraph.draw(heap);
	}
}