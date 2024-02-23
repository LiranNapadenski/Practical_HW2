/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
	public int size;
	public HeapNode last;
	public HeapNode min;
	

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{
		//plan: create new heap with 1 node containing this key and info and them meld
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		return; // should be replaced by student code

	}

	/**
	 * 
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin()
	{
		HeapItem pointer=this.min.item;
		return pointer; // should be replaced by student code
	} 

	/**
	 * 
	 * pre: 0 < diff < item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		//updates the minimum
		if(heap2.findMin().key<this.findMin().key) {
			this.min=heap2.min;
		}
		boolean first_time_in_first=true;// bool inorder not too repeat node in heaps2
		HeapNode Start_Heap2 = heap2.last.next;//the current node in heap2
		while(heap2.last.next !=Start_Heap2 || first_time_in_first) {//stops after the loops runned on the largset rank in heap2
			HeapNode Start_heap1 = this.last.next;//the current node in self - starts from first
			HeapNode PervNode=this.last;// perv to the current note
			HeapNode Tmp = Start_Heap2;// the current node in heap 2 is Tmp because can be melded and dont want to loose corrent pos
			boolean Didnt_Was_At_last=true;//this bool is inorder to prevent infinite loop when tmp.rank is higher then any node's rank in self
			//if already was at the last node in self then there is no need to return to first
			while(Tmp.rank>=Start_heap1.rank &&Didnt_Was_At_last ) {//go next until the rank of node in self is higher or got to the end
				if(Tmp.rank==Start_heap1.rank) {//if rank the same then meld
					Tmp=Start_heap1.Meld_nodes(Tmp);//meld and tmp.rank++ 
				}
				if(Start_heap1==this.last) {//finished with the last node in self
					Didnt_Was_At_last=false;
					this.last=Tmp;//updates this.last becuase was bigger or equal to last's rank
				}
				Start_heap1=Start_heap1.next;//go to next node in self
				PervNode=PervNode.next;
			}
			if(Tmp.rank<Start_heap1.rank) {//if the node's rank smaller then the lowest node rank in self
				HeapNode fisrt=this.last;
				Tmp.next=fisrt;
				this.last.next=Tmp;
			}
			PervNode.next=Tmp;//updates the position of the node
			Start_Heap2=Start_Heap2.next;//next node in heap2 to start the same procces
			first_time_in_first=false;// already melded the first node
		}
		
		return; // should be replaced by student code   		
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return 42; // should be replaced by student code
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return false; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		return 0; // should be replaced by student code
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;
		
		public int Get_key() {
			return this.item.key;
		}
		public HeapItem Get_Item() {
			return this.item;
		}
		/**
		 * get 2 nodes of the same rank and melds them , returns the melded node and keeps next as this.next
		 */
		
		public HeapNode Meld_nodes(HeapNode other) {
			//essentelly meld 2 nodes with the same rank and returns the melded one
			HeapNode Perv_next= this.next; //disconnecting the nodes from the heap
			this.next=this;
			other.next=other;
			HeapNode SmllaerNode; //the smaller node
			HeapNode LargerNode; // the larger node
			if(other.Get_key()>this.Get_key()) {
				LargerNode=other;
				SmllaerNode=this;
			}
			else {
				LargerNode=this;
				SmllaerNode=other;
			}
			SmllaerNode.child=LargerNode; //the smaller node is the parent
			LargerNode.parent=SmllaerNode;
			HeapNode Child_of_small=SmllaerNode.child;
			HeapNode Child_of_lrage=LargerNode;
			while(Child_of_small != null) {//updating the nexts of every level in the tree
				HeapNode Row_S=Child_of_small;//the node of SmllaerNode in this level
				HeapNode Row_L=Child_of_lrage;//the node of LargerNode in this level 
				while(Row_S.next !=Child_of_small) {//connects the smallest node (by rank) to the child of the other node
					Row_S=Row_S.next;
				}
				Row_S.next=Child_of_lrage;
				while(Row_L.next !=Child_of_lrage) {//same as last while
					Row_L=Row_L.next;
				}
				Row_L.next=Child_of_small;
				Child_of_small=Child_of_small.child;//goes down in level
				Child_of_lrage=Child_of_lrage.child;
				
			}
			SmllaerNode.rank++;//rank is increased by one
			SmllaerNode.next=Perv_next;//make sure it is in the same position
			
			return SmllaerNode;
			
		
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public class HeapItem{
		public HeapNode node;
		public int key;
		public String info;
		
		public HeapItem(int key ,String info, HeapNode node ) {//constructor
			this.key=key;
			this.info=info;
			this.node=node;
		}
	}

}
