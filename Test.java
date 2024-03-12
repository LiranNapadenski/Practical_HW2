
public class Test {
	static void insertKeyArray(BinomialHeap heap, int[] keys) {
        for (int key : keys) {
            heap.insert(key, String.valueOf(key));
        }
    }

	public static void test_insert(){
		BinomialHeap heap = new BinomialHeap();
		insertKeyArray(heap, new int[]{35, 20, 31, 40, 43, 45, 50, 55, 15, 60, 65, 70, 75, 80, 85, 90, 19, 100, 120});
		System.out.println(heap.last.child.next.next.getItem().key);
		//heap.printHeap();
	   // BinomialHeap.HeapNode root = heap.last;
	   //here you draw
	   HeapGraph.draw(heap);
	}


	public static void test_meld(){
		BinomialHeap heap2 = new BinomialHeap();
		insertKeyArray(heap2, new int[]{15, 35, 20, 31, 40, 43});
		BinomialHeap heap3 = new BinomialHeap();
		insertKeyArray(heap3, new int[]{7, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 100, 120});
		System.out.println(heap3.size());
		//HeapGraph.draw(heap2);
		//HeapGraph.draw(heap3);
		heap2.meld(heap3);
		//HeapGraph.draw(heap2);
		//heap2.printHeap();
		System.out.println(heap2.size());
	}
	
	public static void main(String[] args) {
		//here you create the heap
		//test_insert();
		test_meld();
	}
}