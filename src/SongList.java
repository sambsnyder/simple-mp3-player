
public class SongList {
	
	// Instance variables
	private SongNode head, tail;

	// Constructor 
	public SongList() {
		head = null;
		tail = null;
	}

	// Prints the nodes of the linked list
	public void printNodes() {
		SongNode curr = head;
		while (curr != null) {
			System.out.print(curr.getSongName() + ", ");
			curr = curr.next();
		}
		System.out.println();
	}

	// Gets the head of the linked list
	public SongNode getHead() {
		return head;
	}

	// Inserts a new node at the back of the linked list
	public void append(String title, String artist, String filename) {
		SongNode newNode = new SongNode(title, artist, filename);
		if (tail != null) {
			tail.setNext(newNode);
			tail = newNode;
		} else { // when the list is empty
			head = tail = newNode;
		}
	}

	// Inserts a new node at the front of the linked list 
	public void insertAtFront(String title, String artist, String filename) {
		SongNode newLink = new SongNode(title, artist, filename);
		if (head != null) {
			newLink.setNext(head);
		} else { // list is empty
			tail = newLink;
		}
		head = newLink; // needs to be done in any case
	}

	// removes the node given the song name
	public void remove(String name) {
		SongNode tmpNode = head;
		SongNode prevNode = null;
		while (tmpNode != null) {
			if (tmpNode.getSongName().equals(name)) {
				if (tmpNode == head) {
					head = head.next();
				} else {
					prevNode.setNext(tmpNode.next());
				}
			} else {
				prevNode = tmpNode;
			}
			tmpNode = tmpNode.next();
		}
	}

	// Finds and returns the smallest node in the linked list
	public String findSmall() {
		SongNode current = head;
		String smallest = head.getSongName();
		SongNode possSmall = current.next();
		// While loop finds the smallest value of the song name in the linked
		// list
		while (current.next() != null && current != null) {
			String possibleSmall = possSmall.getSongName();
			if (possibleSmall.compareToIgnoreCase(smallest) < 0) {
				smallest = possibleSmall;

			}
			possSmall = possSmall.next();
			current = current.next();
		}
		return smallest;
	}

	// Returns the node given the song name in the parameter
	public SongNode getNode(String songname) {
		SongNode curr = head;
		while (curr != null) {
			if (curr.getSongName().equals(songname)) {
				return curr;
			}
			curr = curr.next();
		}
		return curr;
	}

	// Sorts the list by taking what is in the temporary linked list this is
	// called on and appends the nodes to the linked list in the parameter
	public void sortList(SongList list) {
		SongNode curr = head;
		while (curr != null) {
			SongNode smallest = getNode(findSmall());
			list.append(smallest.getSongName(), smallest.getArtist(), smallest.getFilename());
			curr = curr.next();
			remove(smallest.getSongName());
		}
	}

}
