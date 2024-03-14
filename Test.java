import BinomialHeap.HeapItem;

public class Test {
	static void insertKeyArray(BinomialHeap heap, int[] keys) {
        for (int key : keys) {
            heap.insert(key, String.valueOf(key));
        }
    }
	
	public static void test_delete(){
		BinomialHeap heap2 = new BinomialHeap();
		insertKeyArray(heap2, new int[]{50, 60, 15, 35, 20, 15, 0, 1, 0, 31, 40, 43});
		heap2.printHeap();
		System.out.println("delete1 " + heap2.min.child.child.next.next.item.key);
		heap2.delete(heap2.min.child.child.next.next.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());
		heap2.printHeap();
		System.out.println("delete2 " + heap2.min.next.item.key);
		heap2.delete(heap2.min.next.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());
		
		System.out.println("delete3 " + heap2.min.next.item.key);
		heap2.delete(heap2.min.next.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());

		System.out.println("delete4 " + heap2.min.item.key);
		heap2.delete(heap2.min.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());

		
		System.out.println("delete5 " + heap2.min.item.getKey());
		heap2.delete(heap2.min.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());

		System.out.println("delete6 " + heap2.last.item.getKey());
		heap2.delete(heap2.last.item);
		System.out.println("new size: " + heap2.size());
		System.out.println("new min: " + heap2.findMin().getKey());
		HeapGraph.draw(heap2);
		heap2.printHeap();
	}

	public static void test_delete_min(){
		BinomialHeap heap2 = new BinomialHeap();
		insertKeyArray(heap2, new int[]{50, 60, 15, 35, 20, 31, 40, 43});
		//HeapGraph.draw(heap2);
		heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		//HeapGraph.draw(heap2);
		heap2.deleteMin();
		//heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		//heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.printHeap();
		heap2.deleteMin();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		heap2.printHeap();
		System.out.println("min: " + heap2.findMin().key);
		System.out.println("size: " + heap2.size());
		heap2.deleteMin();
		heap2.printHeap();
		System.out.println("size: " + heap2.size());
		HeapGraph.draw(heap2);
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
		insertKeyArray(heap2, new int[]{7, 6, 6, 1, 5, 4, 0});
		BinomialHeap heap3 = new BinomialHeap();
		insertKeyArray(heap3, new int[]{7, 45, 50, 55, 60, 65, 100, 12, 140});
		System.out.println(heap2.numTrees());
		System.out.println(heap3.size());
		System.out.println(heap3.numTrees());
		//HeapGraph.draw(heap2);
		//HeapGraph.draw(heap3);
		heap2.meld(heap3);
		HeapGraph.draw(heap2);
		heap2.printHeap();
		System.out.println("size: " + heap2.size());
		System.out.println("Min: " + heap2.findMin().getKey());
		System.out.println("tree num :" + heap2.numTrees());
	}
	
	public static void main(String[] args) {
		//here you create the heap
		//test_insert();
		test_meld();
		//test_delete_min();
		//test_delete();
	}
}