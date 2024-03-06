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
	 * with all the given data , if min is null find min by himself
	 */
	public BinomialHeap(HeapNode last,HeapNode min , int size) {
		if (last==null || size==0) {//Creates an empty tree
			this.size=0;
			this.last=null;
			this.min=null;
		}
		else {
			this.size=size;
			this.last=last;
			this.min=min;
			if (min==null) {//if min wasnt given update min
				this.Update_Min();
			}
		}
	}
	
	public HeapNode getLast() {
		return this.last;
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
		return item;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		HeapNode MinNode = this.min;//the node to disconnect
		this.Disconnect(MinNode);//disconnects
		HeapNode Child_Of_Min = MinNode.child;
		Child_Of_Min.parent=null;
		BinomialHeap Heap_To_Meld= new BinomialHeap(Child_Of_Min , null , MinNode.rank);//creates an heap with the subtrees of min node as Nodes
		this.Update_Min();//updates the minimum for this
		this.meld(Heap_To_Meld);//Meld this with the new heap
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
	 * updates the minimum if min is unknown
	 */
	public void Update_Min() {
		if(this.last==null) {//if the tree is empty
			this.min=null;
			return;
		}
		HeapNode New_Min=this.last;
		HeapNode CurrentNode=this.last;
		do {
			if(CurrentNode.item.key<New_Min.item.key) {
				New_Min=CurrentNode;
			}
			CurrentNode=CurrentNode.next;
		}while(CurrentNode!=this.last);
		this.min=New_Min;
		return;
	}
	
	/**
	 * heapfys up a given node
	 */
	
	public void Heapfy_Up(HeapNode Node) {
		while(Node.parent!=null) {
			if (Node.parent.item.key>=Node.item.key) {//if parent is larger then node switch
				Node=Node.Switch_Nodes(Node.parent);//switches
			}
			else {//if its okey there is no reason to go up
				break;
			}
		}
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
		item.key=item.key-diff;
		HeapNode Node_Of_Item=item.node;
		this.Heapfy_Up(Node_Of_Item);
		return;
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		this.decreaseKey(item,item.key);//the node is now head of a tree therfore it can be deleted like DeleteMin
		HeapNode DNode = item.node;//the node i need to delete
		this.min=DNode;
		this.deleteMin();
		return;
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
				Node_To_Meld=Array_Of_Nodes[NodeToMeld_Rank].Link(Node_To_Meld);//melds the node and returns the melded node
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
	 * @pre:!this.Empty()
	 */
	public HeapNode Disconnect_First() {
		HeapNode FirstNode=this.last.next;
		this.Disconnect(FirstNode);
		return FirstNode;
	}
	
	/**
	 * Disconnects a given node from a tree and return the node
	 * @return
	 */
	public HeapNode Disconnect(HeapNode Node) {
		if (this.size ==1) {//if the heap has only 1 tree
			this.last=null;
			this.size=0;
			this.min=null;
			return Node;
		}
		HeapNode PrevFirst = Node.prev;
		HeapNode NextFirst = Node.next;
		//disconnected the node
		PrevFirst.next=NextFirst;
		NextFirst.prev=PrevFirst;
		this.size--;//updates the size
		return Node;
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
		public HeapNode Link(HeapNode other) {
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
		
		/**
		 * get another node and switch the physical place of this with other and returns this in its new placement
		 * 
		 */
		public HeapNode Switch_Nodes(HeapNode OtherNode) {
			HeapItem OtherItem=OtherNode.item;
			OtherNode.item=this.item;
			OtherNode.item.node=OtherNode;
			this.item=OtherItem;
			this.item.node=this;
			return OtherNode;
		}
		//for testing
		public HeapItem getItem() {
			return this.item;
		}
		public HeapNode getNext() {
			return this.next;
		}
		public int getRank() {
			return this.rank;
		}
		public HeapNode getChild() {
			return this.child;
		}
		public void setChild(HeapNode child) {
			this.child=child;
		}
		public void setNext(HeapNode next) {
			this.next=next;
		}
		public void setRank(int rank) {
			this.rank=rank;
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
		//for testing
		public int getKey() {
			return this.key;
		}
	}

}
