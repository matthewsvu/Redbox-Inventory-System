/*
 *Name: Matthew Vu
 *Net ID: MSV180000 
 */

public class DVD implements Comparable<DVD> {
	// private member variables
	private String title;
	private int available;
	private int rented;
	// default constructor
	public DVD() {
		setTitle("");
		setAvailable(-1);
		setRented(-1);
	}
	// overloaded constructor
	public DVD(String t, int a, int r) {
		setTitle(t);
		setAvailable(a);
		setRented(r);
	}
	// mutators and accessors
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public int getRented() {
		return rented;
	}
	public void setRented(int rented) {
		this.rented = rented;
	}
	@Override // compares the title of this DVD with another DVD
	public int compareTo(DVD payload) {
		int val = 0;
        if (payload instanceof DVD)
        {
            val = title.compareTo(payload.title);
        }
        
        return val;
	}
	@Override // print the contents of DVD
	public String toString() {
		return String.format(getTitle() + "\t" + getAvailable() + "\t" + getRented());
	}
	
}
