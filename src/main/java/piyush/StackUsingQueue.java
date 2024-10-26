package piyush;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class StackUsingQueue {

	private Queue<Integer> queue = new LinkedList<>();
	private static Stack<Integer> stk = new Stack<Integer>();
	
	
	public void push(int val) {
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
	
	public static int[] nextGreaterElement(int[] nums) {
		/*
		 * The next greater element of some element x in an array is the first greater 
		 * element that is to the right of x in the same array.
		 * 
		 * nums = [1,3,4,2]
		 * res  = [3,4,-1,-1]
		 * 
		 * Brute force : for each item in nums, iterate on items to its right, find first element 
		 * that is eligible as next greater, store in result. if not found store -1;
		 * 
		 * Time = O(n2)
		 * Space = O(n)
		 * 
		 * Optimal : using monotonic stack. which will hold only a pattern of values.
		 * 
		 * Time = O(2n)
		 * Space = O(n)
		 * 
		 * */
        int[] aux = new int[nums.length];
        for(int i=nums.length-1; i>=0; i--){
            while(!stk.isEmpty() && stk.peek() < nums[i]){
                stk.pop();
            }
            if(stk.isEmpty()){
                aux[i] = -1;
            }
            else{
                aux[i] = stk.peek();
            }
            stk.push(nums[i]);
        }
        return aux;
    }
	
}
