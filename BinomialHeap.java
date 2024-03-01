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
	 * constructor with key and info
	 */
	public BinomialHeap(int key, String info) {
		HeapNode Node = new HeapNode(key, info);
		this.last= Node;
		this.min = Node;
		this.size=1;
	}
	

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{
		BinomialHeap New_BinomialHeap = new BinomialHeap(key , info);
		HeapItem item = New_BinomialHeap.last.item;
		this.meld(New_BinomialHeap);
		return item; // should be replaced by student code
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
		return this.min.item; // should be replaced by student code
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
		if(heap2.empty() || heap2==null ) {//if heap size is 0 or None
			return;
		}
		
		if(this.empty()) {// if this is empty and heap2 not  
			this.size=heap2.size;
			this.min=heap2.min;
			this.last=heap2.last;
			return;
		}
		
		HeapNode[] Array_Of_Nodes = new HeapNode[Math.max(this.last.rank, heap2.last.rank)+2] ; //there could be k+1 different trees when k is the highest rank for both of the heaps
		HeapNode Tmp_For_Insert=this.last;
		do {//inserting all the trees in this to an array were A[k] is the tree with rank k
			Array_Of_Nodes[Tmp_For_Insert.rank]=Tmp_For_Insert;
			Tmp_For_Insert=Tmp_For_Insert.next;
		}
		while(Tmp_For_Insert!=this.last);
		
		while (!heap2.empty()) {//if heap2 not empty because every run disconnects the first node
			HeapNode Node_To_Meld=heap2.Disconnect_First(); // the node to Meld
			if (Node_To_Meld.item.key<this.min.item.key) {//updates this.min if needed
				this.min=Node_To_Meld;
			}
			this.size++;//updates the size
			while(Array_Of_Nodes[Node_To_Meld.rank]!=null) {//if the heap(array) has a node with the same rank melds between them and empty the place the in the array
				int NodeToMeld_Rank = Node_To_Meld.rank;
				Node_To_Meld=Array_Of_Nodes[NodeToMeld_Rank].Meld_Tow_Node(Node_To_Meld);//melds the node and returns the melded node
				Array_Of_Nodes[NodeToMeld_Rank]=null;//now its null
				}
			Array_Of_Nodes[Node_To_Meld.rank]=Node_To_Meld;//the node doesnt have who to meld with and just inserted in its place in the array
		}
		//converts the array back to heap
		if(Array_Of_Nodes[Array_Of_Nodes.length-1]==null) {//set this.last , has to be the last or before the last in the list
			this.last=Array_Of_Nodes[Array_Of_Nodes.length-2];
		}
		else {
			this.last=Array_Of_Nodes[Array_Of_Nodes.length-1];
		}
		BinomialHeap.Connect_Node_Array(Array_Of_Nodes);
		return; // should be replaced by student code   		
	}
	
	
	/**
	 * gets an Array of heapnodes and connects all of them by order
	 * 
	 */
	public static void Connect_Node_Array(HeapNode[] Array) {
		HeapNode PrevNode=null;
		HeapNode FirstNode=null;
		for (HeapNode Node : Array) {//connecting all the node
			if (Node !=null) {//connects only if node is not None
				if(PrevNode==null) {//if its the first node
					PrevNode=Node;
					FirstNode=Node;
					continue;
				}
				PrevNode.next=Node;
				Node.prev=PrevNode;
				PrevNode=Node;
			}
		}
		//perv now is the last node - connects first and last
		PrevNode.next=FirstNode;
		FirstNode.prev=PrevNode;
	}
	
	/**
	 * disconnects the first node(tree) from heap and returns the node
	 */
	public HeapNode Disconnect_First() {
		if (this.size ==1) {//if the heap has only 1 tree
			HeapNode FirstNode=this.last;
			this.last=null;
			this.size=0;
			this.min=null;
			return FirstNode;
		}
		HeapNode FirstNode=this.last.next;
		HeapNode PrevFirst = FirstNode.prev;
		HeapNode NextFirst = FirstNode.next;
		//disconnected the node
		PrevFirst.next=NextFirst;
		NextFirst.prev=PrevFirst;
		this.size--;//updates the size
		return FirstNode;
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return this.size()==0; // should be replaced by student code
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
		public HeapNode prev;
		public HeapNode parent;
		public int rank;
		
		
		/**
		 * constructor
		 */
		public HeapNode(int key ,String info ) {
			this.item= new HeapItem(key , info , this);
			this.child=null;
			this.parent=null;
			this.next=this;
			this.prev=this;
			this.rank=0;
		}
		
		/**
		 * connects 2 nodes of the same rank , keeps this in the same place in the heap
		 * and returns a pionter to the new node
		 * @pre this.rank==other.rank
		 * @ret@type= HeapNode
		 */
		public HeapNode Meld_Tow_Node(HeapNode other) {
			if (other == null) {
				return this;
			}
			HeapNode PrevNode= this.prev;
			HeapNode NextNode= this.next;
			HeapNode New_Head; //the head will be the pionter returnd
			HeapNode New_Child;
			if (this.item.key>=other.item.key) {//the head will be node with the smaller key
				New_Head=this;
				New_Child=other;
			}
			else {
				New_Head=other;
				New_Child=this;
			}
			if(New_Head.rank!=0) {//if the rank isnt 0
				//connecting the head of the tree to all the other nodes in his level.
				New_Child.prev=New_Head.child.prev;
				New_Head.child.prev.next=New_Child;
				New_Child.next=New_Head.child;
				New_Head.child.prev=New_Child;
			}
			//setting the New_Child as child
			New_Child.parent=New_Head;
			New_Head.child=New_Child;
			//returning the tree to the list
			PrevNode.next=New_Head;
			New_Head.prev=PrevNode;
			NextNode.prev=New_Head;
			New_Head.next=NextNode;
			//updating the rank
			New_Head.rank++;
			return New_Head;
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
		
		/**
		 * constructor
		 */
		public HeapItem(int key ,String info , HeapNode node) {
			this.key=key;
			this.info=info;
			this.node=node;
		}
	}

}
