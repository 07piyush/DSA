package piyush;

import java.util.HashMap;

public class LFUCache {
	
	/*
	 * Idea : Cache of certain capacity, will cache data till capacity and will remove 
	 * those data from cache that were least frequently used. since there can be multiple items that are 
	 * least frequently used, then remove those item which are Least Frequently Least Recently Used.
	 * 
	 * Sub tasks :
	 * need a list, that can keep items in certain order. 
	 * need to keep track of frequency, an item is accessed.
	 * need to maintain LRU for each frequency to remove it in case of tie. 
	 * 
	 * lets have map of key and node in a map, to get an item in constant time. (hashmap) or logN (head/tree)
	 * when ever a get(key) is performed, 
	 * 		1. We need to know previous freq. of that item , and now increase it. that means node should have freq.
	 * 
	 * when ever put is done, 
	 * 		1. key to be put, either already been cached, only values must be updated. or new item is introduced.
	 * 		2. we need to know if cache has the capacity if its a new entry.
	 * 		3. remove LFLRU item and put new item with freq of 1.
	 */

	   	HashMap<Integer, DoublyLinkedList> freqMap;
		HashMap<Integer, ListNodeD> cache;
		int capacity;
		int currentMinFreq;
		
		public LFUCache(int capacity) {
			this.capacity = capacity;
			currentMinFreq = 0;
	        cache = new HashMap<>();
	        freqMap = new HashMap();
		}
		
		public int get(int key) {
	     if(!cache.containsKey(key)) {
	    	 return -1;
	     }
	     //1. get node from cache.
	     ListNodeD node = cache.get(key);
	     updateNode(node);
	     return node.value;
	    }
		
		private void updateNode(ListNodeD node) {
			/*
			 * when ever an item is accessed to update value or get value the value.
			 * We need to put that node according to protocol of LFU.
			 * so remove it from old freq.
			 * update the freq. and put it in bucket of new freq.
			 * 
			 * */
			int freq = node.freq;
		     //remove it from current freq of freqMap.
		     DoublyLinkedList list = freqMap.get(freq);
		     list.remove(node);
		     
		     //if node was last item in current frequency then update currentMinimum frequency.
		     if(currentMinFreq == freq && list.size == 0) {
		    	 currentMinFreq++;
		     }
		     node.freq++;
		     DoublyLinkedList targetList = freqMap.getOrDefault(node.freq, new DoublyLinkedList());
		     targetList.add(node);
		     freqMap.put(node.freq, targetList); 
		}
	    
	    public void put(int key, int value) {
	    	//1. if key exists in cache, then update node.
	    	//2. if key is new, then use getordefault to put with freq 1.update minimum freq. 2. add in cache
	    	if(cache.containsKey(key)) {
	    		ListNodeD node = cache.get(key);
	    		node.value = value;
	    		updateNode(node);
	    	}
	    	else {
	    		//its a new entry to be cached.
	    		//1. if cache capacity is reached, remove LFULRU in case of multiple LFU nodes.
	    		if(cache.size() == capacity) {
	    			//get minimum freq list.
	    			DoublyLinkedList minList = freqMap.get(currentMinFreq);
	    			ListNodeD nodeForRemoval = minList.tail.prev;
	    			minList.remove(nodeForRemoval);
	    			cache.remove(nodeForRemoval.key);
	    		}
	    		//create new node, then put it in a list with freq of 1.
	    		ListNodeD newNode = new ListNodeD();
				newNode.key = key;
				newNode.value = value;
				newNode.freq = 1;
				DoublyLinkedList targetList = freqMap.getOrDefault(1, new DoublyLinkedList());
				targetList.add(newNode);
				currentMinFreq = 1;
				cache.put(key, newNode);
	            freqMap.put(1, targetList);
	    	}
	    }
}
