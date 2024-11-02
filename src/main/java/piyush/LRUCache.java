package piyush;

import java.util.HashMap;

public class LRUCache {

    ListNodeD head;
	ListNodeD tail;
	HashMap<Integer, ListNodeD> cache;
	int capacity;

    public LRUCache(int capacity) {
        head = new ListNodeD();
		tail = new ListNodeD();
		head.next = tail;
		tail.prev = head;
		cache = new HashMap<>();
		this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)) {
			return -1;
		}
        ListNodeD item = cache.get(key);
        remove(item);
        insert(item);
        return item.value;
    }
    
    public void put(int key, int value) {
         if(cache.containsKey(key)) {
        	ListNodeD item = cache.get(key);
        	item.value = value;
        	remove(item);
        	insert(item);
        }
        else if(cache.size() < capacity){
        	ListNodeD item = new ListNodeD();
        	item.key = key;
        	item.value = value;
        	insert(item);
        	cache.put(key, item);
        }
        else {
        	ListNodeD item = tail.prev;
        	cache.remove(item.key);
        	item.key = key;
        	item.value = value;
        	remove(item);
        	insert(item);
        	cache.put(key, item);
        }
    }

    private void remove(ListNodeD node) {
		ListNodeD prevNode = node.prev;
		ListNodeD nextNode = node.next;
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
	}
	
	private void insert(ListNodeD node) {
		node.next = head.next;
		head.next.prev = node;
		head.next = node;
        node.prev = head;
	}
}
