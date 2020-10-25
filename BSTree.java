/*
 *Name: Matthew Vu
 *Net ID: MSV180000 
 */

public class BSTree<D extends Comparable<D>> {
	// member variable
	private Node<D> Root;
	
	// default constructor
	public BSTree() {
		Root = null;
	}
	// overloaded constructor
	public BSTree(Node<D> root) {
		this.Root = root;
	}
	// called from main
	public void insert(D child) {
		if(getRoot() == null) { // if the root is null, set the root to the given parameter
			setRoot(new Node<D>(child));
		}
		else {
		 insertRec(Root, new Node<D>(child)); // recurse through the tree
		}
	}
	// insert method
	private void insertRec(Node<D> parent, Node<D> child) {
		// recursive solution
		if(child.getPayload().compareTo(parent.getPayload()) < 0) { // when the child node is less alphabetically then the parent left ptr is set to child
			if(parent.getLeft() == null) { // checks for it being null meaning we can set child as it's left
				parent.setLeft(child);
			}
			else {
				insertRec(parent.getLeft(), child); // continue recursively traversing down
			}
		}
		else { // child >= parent 
			if(parent.getRight() == null) { // greater alphabetically, set parent right ptr to child
				parent.setRight(child);
			}
			else {
				insertRec(parent.getRight(), child); // recursive through the right tree
			}
		}
	}
	// delete a DVD from the BST
	public void delete(Node<D> child)  {
		child = search(child); // search for the child in tree
		Node<D> parent = getParent(getRoot(), child); // find the parent of the child
		deleteRec(getRoot(), parent, child); // recursively traverse tree 
	}
	// recursive call for delete 
	private boolean deleteRec(Node<D> root, Node<D> parent, Node<D> child) {
		if(child == null) { // return nothing
			return false;
		}
		// recursive solution
		// case 1 internal node with 2 children
		if(child.getLeft() != null && child.getRight() != null) {
			// find the successor and it's parent
			Node<D> succNode = child.getRight();
			Node<D> succParent = child;
			
			while(succNode.getLeft() != null) {
				succParent = succNode;
				succNode =  succNode.getLeft();
			}
			// copy val from succ node
			child.setPayload(succNode.getPayload());
			// recursively remove succesor
			deleteRec(getRoot(), succParent, succNode);
		}
		else if(child == getRoot()) { // case 2 root node with 1 or 0 children nodes
			if(child.getLeft() != null) {
				setRoot(child.getLeft());
			}
			else {
				setRoot(child.getRight());
			}
		} // case 3 internal node with only a left child
		else if(child.getLeft() != null) {
			// replace child with child's left child
			if(parent.getLeft().getPayload().compareTo(child.getPayload()) == 0) {
				parent.setLeft(child.getLeft());
			}
			else {
				parent.setRight(child.getLeft());
			}
		}
		else { // case 4 intenral with a right child or a leaf
			// replace child with child's right child node
			if(parent.getLeft().getPayload().compareTo(child.getPayload()) == 0 ) {
				parent.setLeft(child.getRight());
			}
			else {
				parent.setRight(child.getRight());
			}
		}
		return true;
	}
	// call for to find the parent node
	private Node<D> getParent(Node<D> tree, Node<D> node) {
		return getParentRec(tree, node);
	}
	// recursive call for parent node finding
	private Node<D> getParentRec(Node<D> subtree, Node<D> node) {
		if(subtree == null) { // return null when the subtree root is null
			return null;
		}
		if(subtree.getLeft() == node || subtree.getRight() == node) { // if the left or right nodes are the same as node
			return subtree;
		}
		if(node.getPayload().compareTo(subtree.getPayload()) < 0) { // go down left side of tree
			return getParentRec(subtree.getLeft(), node);
		}
		return getParentRec(subtree.getRight(), node); // go down the right
	}
	// recursive call for searching tree
	private Node<D> searchRec(Node<D> find, Node<D> current) {
		int val = find.getPayload().compareTo(current.getPayload()); // compares the target payload with the current payload node
		if(current != null) {
			if(val == 0) {
				return current; // desired node was found
			}
			else if(val < 0) { // checks if alphabetically less than key
				return searchRec(find, current.getLeft());
			}
			else if(val > 0) { // checks if alphabetically > than key
				return searchRec(find, current.getRight());
			}
		}
		return null; // return null if nothing is found
	}
	// searches for DVD in bst
	public Node<D> search(Node<D> find) {
		Node<D> current = getRoot();
		return searchRec(find, current);
	}
	// prints tree inorder
	public void traverseInorder(Node<D> node, BSTree<D> tree) {
		if(node == null ) {
			return;
		}
		traverseInorder(node.getLeft(), tree);
		System.out.println(node.getPayload().toString()); 
		traverseInorder(node.getRight(), tree);
	}
		// mutator and accessor
	public void setRoot(Node<D> root) {
		this.Root = root;
	}
	public Node<D> getRoot() {
		return Root;
	}
 }
