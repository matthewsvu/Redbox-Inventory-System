/*
 *Name: Matthew Vu
 *Net ID: MSV180000 
 */
import java.util.*;
import java.io.*;

public class Main {
	
	public static void executeInstructions(BSTree<DVD> tree, String instruction, String[] elements) {
		// replace all the quotations
		switch(instruction) { 
			case "add":
				Node<DVD> addItem = new Node<DVD> (new DVD(elements[0].replaceAll("\"", ""), Integer.parseInt(elements[1]), 0));
				if(tree.search(addItem) != null) { // add copies of an existing file
					// search for the item to be added in the tree and get it's contents
					Node<DVD> oldItem = tree.search(addItem);
					// add the old item amount and the new item amount together
					int newAvailable = addItem.getPayload().getAvailable() + oldItem.getPayload().getAvailable(); 
					
					addItem.getPayload().setAvailable(newAvailable); // set the new available amount to the item we'll add to the tree
					addItem.getPayload().setRented(oldItem.getPayload().getRented()); // set the rented the amount the old item has rented
					tree.search(addItem).setPayload(addItem.getPayload()); // search the tree and add the updated object to the node in the tree
				}
				else { // if the item is not found in tree add the item into the tree
					tree.insert(addItem.getPayload());
				}
				break;
			case "remove": // 
				Node<DVD> removeItem = new Node<DVD>(new DVD(elements[0].replaceAll("\"", ""), Integer.parseInt(elements[1]), 0));
				Node<DVD> oldItem = tree.search(removeItem); // finds the node that has the title we're looking for
				int difference = oldItem.getPayload().getAvailable() - removeItem.getPayload().getAvailable(); // olditem avaialble - new item available
				
				if(oldItem.getPayload().getRented() == 0 && difference == 0) { // remove the node from tree
					tree.delete(removeItem);
				}
				else if(difference >= 0){
					tree.search(removeItem).getPayload().setAvailable(difference);
				}
				break;
			case "rent":
				Node<DVD> rentItem = new Node<DVD>(new DVD(elements[0].replaceAll("\"", ""), 0, 0));
				Node<DVD> oldRent = tree.search(rentItem);
				if(oldRent != null && oldRent.getPayload().getAvailable() > 0) { // only occurs when current available amount > 0
						// create int variables for updated amount
						int newAvailable = tree.search(rentItem).getPayload().getAvailable() - 1;
						int newRented = tree.search(rentItem).getPayload().getRented() + 1;
						// set new values of available and rented to the object
						tree.search(rentItem).getPayload().setAvailable(newAvailable);
						tree.search(rentItem).getPayload().setRented(newRented);
				}
				break;
			case "return":
				Node<DVD> returnItem = new Node<DVD>(new DVD(elements[0].replaceAll("\"", ""), 0, 0));
				Node<DVD> oldReturn = tree.search(returnItem);
				if(oldReturn != null && oldReturn.getPayload().getRented() > 0) { // only occurs when current rented amount > 0
					// create int variables for updated amount
					int newAvailable = tree.search(returnItem).getPayload().getAvailable() + 1;
					int newRented = tree.search(returnItem).getPayload().getRented() - 1;
					// set new values of available and rented to the object
					tree.search(returnItem).getPayload().setAvailable(newAvailable);
					tree.search(returnItem).getPayload().setRented(newRented);
				}
				break;
			default:
				break;
		}
	}
	public static void parseTransactionLog(BSTree<DVD> tree, String filename) {
		try {
			// for transaction file and for output file
			Scanner file = new Scanner(new File(filename));
			PrintWriter output = new PrintWriter(new File("error.log"));
			// continue parsing into there is no lines left
			while(file.hasNextLine()) {
				String line = file.nextLine(); // get the next line from the file
				String errorLine = line; // to print when encounter error in line
				int space = line.indexOf(' '); // get the first index of a space
				String instruction = line.substring(0, space);  // get the first instruction from the line
				
				line = line.substring(space+1, line.length()); // set line to itself minus the first instruction 
				
				String[] elements = line.split(","); // split the string by space and comma into an array
				
				if(!validateInput(instruction, elements)) { // validate the inputs
					output.println(errorLine);
				}
				else {
					executeInstructions(tree, instruction, elements);
				}
			}
			output.close();
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	// validate the input
	public static boolean validateInput(String instruction, String[] elements) {
		if(elements.length > 2) { // There shouldn't be more than this many elements
			return false;
		}
		try {
			switch(instruction) {
				case "add": // the length of elements must be 2
					if(elements.length != 2) {
						return false;
					}
					Integer.parseInt(elements[1]); // checks if the string in index 1 is an integer
					break;
				case "remove": // length of elements must be 2
					if(elements.length != 2) {
						return false;
					}
					Integer.parseInt(elements[1]); // checks for 2nd element being an integer
					break;
				case "rent":
					if(elements.length != 1) { // elements must be length 1
						return false;
					} // must have valid quotation marks
					if(elements[0].charAt(0) != '\"' || elements[0].charAt(elements[0].length() - 1) != '\"') { 
						return false;
					}
					break;
				case "return":
					if(elements.length != 1) { // elements must be length 1
						return false;
					}
					if(elements[0].charAt(0) != '\"' || elements[0].charAt(elements[0].length() - 1) != '\"') {
						return false;
					}
					break;
				default: // when the instruction is not either of the above 
					return false;																																															
			}
		}
		catch(NumberFormatException e) { // when the integer at the end of add and remove isn't an integer
			e.getStackTrace();
			return false;
		}
		return true;
	}
	public static void seedTree(BSTree<DVD> tree, String filename) {
		try {
			Scanner s = new Scanner(new File(filename)); // scanner for inventory file
			while(s.hasNextLine()) {
				String[] line = s.nextLine().split(","); // split line by comma into 3 strings and ints
				DVD payload = new DVD(line[0].replaceAll("\"", ""), Integer.parseInt(line[1]), Integer.parseInt(line[2])); // construct a DVD
				tree.insert(payload); // insert the Node into tree
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("There was no inventory file.");
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		String filename = "";
		Scanner s = new Scanner(System.in);
		
		try {
		// read inventory file 
		System.out.println("Please enter the inventory file: ");
		filename = s.nextLine();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error wrong file or file not found");
		}
		
		// create the binary search tree from inventory file
		BSTree<DVD> tree = new BSTree<>();
		seedTree(tree, filename); // seed the BSTree
		
		try {
		System.out.println("Please enter the transaction file: ");
		filename = s.nextLine();
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error wrong file or file not found");
		}
		parseTransactionLog(tree, filename); // run through the transactions and process them
		System.out.println("Title" + "\t" + "Available" + "\t" + "Rented");
		tree.traverseInorder(tree.getRoot(), tree); // print the tree inorder
		s.close();
	}

}
