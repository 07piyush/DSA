package piyush;

import java.util.LinkedList;
import java.util.Queue;

public class StackUsingQueue {

	private Queue<Integer> queue = new LinkedList<>();
	
	public void push(Integer val) {
		queue.add(val);
		for(int i=0; i<queue.size(); i++) {
			queue.add(queue.remove());
		}
	}
	
	public void pop() {
		queue.remove();
	}
	
	public int top() {
		return queue.peek();
	}
	
	public boolean isEmpty() {
		return queue.size() == 0;
	}
	
}
