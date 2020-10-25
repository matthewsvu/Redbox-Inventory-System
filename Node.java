/*
 *Name: Matthew Vu
 *Net ID: MSV180000 
 */

public class Node<D extends Comparable<D>> implements Comparable<D>{
	// member variables
	private Node<D> Left;
	private Node<D> Right;
	private D Payload;
	
	// default constructor
	public Node() {
		this.Left = null;
		this.Right = null;
		this.Payload = null;
	}
	// overloaded constructor
	public Node(D payload) {
		this.Left = null;
		this.Right = null;
		this.Payload = payload;
	}
	// mutators and accessors
	public D getPayload() {
		return this.Payload;
	}
	public void setPayload(D payload) {
		this.Payload = payload;
	}
	public Node<D> getRight() {
		return Right;
	}
	public void setRight(Node<D> right) {
		this.Right = right;
	}
	public Node<D> getLeft() {
		return Left;
	}
	public void setLeft(Node<D> left) {
		this.Left = left;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(D o) { // comparison 
		int val = 0;
        if (o instanceof Node)
        {
            val = Payload.compareTo(((Node<D>)o).Payload);
        }
        return val;
	}
	
	
}
