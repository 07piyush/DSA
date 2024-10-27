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

	public int[] nextGreaterElements2(int[] nums) {

		/*
		 * Given a circular integer array nums 
		 * (i.e., the next element of nums[nums.length - 1] is nums[0]), 
		 * return the next greater number for every element in nums.
		 *  
		 *
		 **/
		
		
		int[] res = new int[nums.length];
		Stack<Integer> stk = new Stack<Integer>();

		for(int i=2*nums.length-1; i>=0; i--){
			while(!stk.isEmpty() && stk.peek()<=nums[i%nums.length]){
				stk.pop();
			}
			if(stk.isEmpty()){
				res[i%nums.length] = -1;
			}
			else{
				res[i%nums.length] = stk.peek();
			}
			stk.push(nums[i%nums.length]);
		}
		return res;
	}
	
	public int trap(int[] height) {
		
		/*
		 * Given n non-negative integers representing an elevation map where the width of each bar is 1, 
		 * compute how much water it can trap after raining.
		 * 
		 * Input: height = [0,1,0,2,1,0,1,3,2,1,2,1]
		 * Output: 6
		 * 
		 * Brute force : for each bar go to left and go to right, find minimum size bar which is greater than 
		 * current bar. calculate water trapped in them by reducing size of current bar.
		 * 
		 * Time = O(n2)
		 * 
		 * Better : use perfixmax to keep track of maximum bar size to the left of a bar.
		 * and suffix max to keep track of mximum bar size to the right of a bar. Now calculate trapped
		 * water by reducing size of current bar.
		 * 
		 * Time = O(3n)
		 * Space = O(2n) : reduced in next approach.
		 * 
		 * */
		
        int[] prefixMax = new int[height.length];
        int[] suffixMax = new int[height.length];
        int totalStore = 0;
        preparePrefixMax(height, prefixMax);
        prepareSufficMax(height, suffixMax);
        for(int currBar = 0; currBar < height.length; currBar++){
            totalStore = totalStore + min(prefixMax[currBar], suffixMax[currBar]) - height[currBar];
        }
        return totalStore;
    }

    public int min(int a, int b){
        return a<b?a:b;
    }

    public void preparePrefixMax(int[] height, int[] prefixMax){
        prefixMax[0] = height[0];
        for(int i = 1; i<height.length; i++){
            prefixMax[i] = height[i]>prefixMax[i-1]?height[i]:prefixMax[i-1];
        }
    }
     public void prepareSufficMax(int[] height, int[] suffixMax){
        suffixMax[height.length-1] = height[height.length-1];
        for(int i = height.length-2; i>=0; i--){
            suffixMax[i] = height[i]>suffixMax[i+1]?height[i]:suffixMax[i+1];
        }
    }

}
