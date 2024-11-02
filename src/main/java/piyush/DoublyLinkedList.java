package piyush;

public class DoublyLinkedList {

	ListNodeD head;
	ListNodeD tail;
	int size;
	
	DoublyLinkedList(){
		head = new ListNodeD();
		tail = new ListNodeD();
		head.next = tail;
		head.prev = null;
		tail.prev = head;
		tail.next = null;
	}
	
	public void add(ListNodeD node) {
		head.next.prev = node;
		node.next = head.next;
		node.prev = head;
		head.next = node;
		size++;
		
	}
	
	public void remove(ListNodeD node) {
		node.prev.next = node.next;
		node.next.prev = node.prev;
		size--;
	}
}
