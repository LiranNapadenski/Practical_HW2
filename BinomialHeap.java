import org.w3c.dom.Node;

//username1: roibirnbaum
//id1: 207834706
//name1: Roi Birnbaum
//username2: lirann
//id2: 328456645
//name2: Liran Napadenski
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
	
	
	public BinomialHeap() {
		this.size = 0;
		this.last = null;
		this.last = null;
	}
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
		if (size == 0){
			this.min = item.node;
			this.last = item.node;
			this.size++;
			return item;
		}
		if (this.size % 2 == 0){
			if (this.min.item.key > key){
				this.min = item.node;
			}
			item.node.next = this.last.next;
			item.node.prev = this.last;
			
			this.last.next = item.node;
			item.node.next.prev = item.node;
			this.size++;
		}
		else{
			this.meld(New_BinomialHeap);
		}
		return item;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin()
	{
		if (this.size == 0){
			return;
		}
		HeapNode MinNode = this.disconnect_single_tree(this.min);//the node and its subtree disconnected from heap
		BinomialHeap Heap_To_Meld= new BinomialHeap(MinNode.child , null ,(int)(Math.pow(2, MinNode.rank))-1);//creates an heap with the subtrees of min node as Nodes
		if (MinNode.child != null)
			MinNode.child.parent = null;
		MinNode.child = null;
		
		//this.Update_Min();//updates the minimum for this
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
			if (Node.parent.item.key != -1 && Node.parent.item.key > Node.item.key) {//if parent is larger then node switch
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
		this.decreaseKey(item,item.key+1);//the node is now head of a tree therfore it can be deleted like DeleteMin
		HeapNode DNode = item.node;//the node i need to delete
		this.min=DNode;
		this.deleteMin();
		this.Update_Min();
		return;
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2)
	{
		if(heap2.empty() || heap2==null ) {//if heap size is 0 or Null
			return;
		}
		
		if(this.empty()) {// if this is empty and heap2 not  
			this.size=heap2.size;
			this.min=heap2.min;
			this.last=heap2.last;
			return;
		}

		//int len_arr = Math.max((int)(Math.log(this.size) / Math.log(2)), (int)(Math.log(heap2.size) / Math.log(2))) + 2;//there could be k+1 different trees when k is the highest rank for both of the heaps
		int len_arr = (int)(Math.log(this.size + heap2.size) / Math.log(2)) + 2;
		HeapNode[] Array_Of_Nodes = new HeapNode[len_arr] ;
		HeapNode Tmp_For_Insert = this.last;
		do {//inserting all the trees in this to an array were A[k] is the tree with rank k
			Array_Of_Nodes[Tmp_For_Insert.rank] = Tmp_For_Insert;
			Tmp_For_Insert = Tmp_For_Insert.next;
		}
		while(Tmp_For_Insert!=this.last);
		
		while (!heap2.empty()) {//if heap2 not empty because every run disconnects the first node
			HeapNode Node_To_Meld=heap2.disconnect_single_tree(heap2.last.next); // the node to Meld
			if (Node_To_Meld.item.key < this.min.item.key) {//updates this.min if needed
				this.min=Node_To_Meld;
			}
			this.size = this.size + (int)(Math.pow(2, Node_To_Meld.rank));//updates the size
			while(Array_Of_Nodes[Node_To_Meld.rank] != null) {//if the heap(array) has a node with the same rank melds between them and empty the place the in the array
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
		this.Update_Min();
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
	 * Disconnects a given tree from a given root and return 
	 * @post this is no longer functional heap!
	 * @return
	 */
	public HeapNode disconnect_single_tree(HeapNode Node) {
		if (this.size ==1) {//if the heap has only 1 tree
			this.last=null;
			this.size=0;
			this.min=null;
			return Node;
		}

		//Node has at least one sibling
		if (this.min == Node) 
			this.min = Node.next; //is it ok??? heap wont be functional anyway
		if (this.last == Node)
			this.last = Node.next; //needed for meld
		Node.prev.next = Node.next;
		Node.next.prev = Node.prev;
		Node.prev = Node;
		Node.next = Node;
		this.size = this.size - (int)(Math.pow(2, Node.rank));
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
		int heap_size = this.size;
		int trees_cnt = 0;
		while (heap_size != 0){
			if (heap_size % 2 == 1)
				trees_cnt += 1;
			heap_size = heap_size / 2;
		}
		return trees_cnt; // should be replaced by student code
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
		
		
		public HeapNode() {
			this.item = null;
			this.child = null;
			this.parent=null;
			this.next = null;
			this.prev = null;
			this.rank = 0;
		}
		
		/**
		 * constructor
		 */
		public HeapNode(int key ,String info ) {
			this.item= new HeapItem(key , info , this);
			this.child = null;
			this.parent = null;
			this.next = this;
			this.prev=this;
			this.rank = 0;
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
			
			HeapNode New_Head; //the head will be the pionter returnd
			HeapNode New_Child;

			if (this.item.key <= other.item.key) {//the head will be node with the smaller key
				New_Head=this;
				New_Child=other;
			}
			else {
				New_Head=other;
				New_Child=this;
			}

			HeapNode PrevNode= New_Head.prev;
			HeapNode NextNode= New_Head.next;

			if (PrevNode == New_Child){
					PrevNode = PrevNode.prev; //skip this prev, take prev to prev (can be New Head itself or other sibling)
				}
			if (NextNode == New_Child){
				NextNode = NextNode.next; //skip this next, take next to next (can be New Head itself or other sibling)
			}

			//disconnect siblings pointers from new child
			New_Child.prev.next = New_Child.next;
			New_Child.next.prev = New_Child.prev;

			if (New_Head.child != null){
				//connecting the head of the tree to all the other nodes in his level.
				
				New_Child.prev=New_Head.child.prev;
				New_Head.child.prev.next=New_Child;

				New_Child.next=New_Head.child;
				New_Head.child.prev = New_Child;
			}

			else{
				New_Child.prev = New_Child; //point to itself
				New_Child.next = New_Child;
			}

			//new parent for the new child
			New_Child.parent=New_Head;
			//setting the New_Child as child to be first
			New_Head.child=New_Child;

			//updating the rank
			New_Head.rank++;
			
			//returning the tree to the list
			PrevNode.next=New_Head;
			NextNode.prev=New_Head;
			New_Head.prev=PrevNode;
			New_Head.next=NextNode;

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
