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
     
     public int sumSubarrayMins(int[] arr) {
    	 
    	 /*
    	  * Given an array of integers arr, find the sum of min(b), where b ranges over every (contiguous) subarray of arr. 
    	  * Since the answer may be large, return the answer modulo 109 + 7.
    	  * 
    	  * Brute force :
    	  * 
    	  * 
    	  * Optimal : 
    	  * Analysis : 
    	  * 1. for each element of array, find its contribution in total sum.
    	  * its contribution will be the number subarrays that exists with that element being
    	  * the minimum number, multiplied the element itself. 
    	  * E.g. : contribution of 3 in array[4,3,5,7] will be  3*(no. of subArrays with 3 as min)
    	  * 
    	  * 2. to find contribution of each element. find all those elements to its left, and to its right
    	  * such that the element itself is the minimum item.
    	  * E.g. : in array[1,4,3,5,7,0] : with 3 being as minimum element in a subarray, total subarrays
    	  * possible are those that start with either 4 or 3, and ends with 3,5,7. 3 has to be part of subarray.
    	  * 
    	  * formula to find total such sub arrays = (itemsToleft +1) * (itemsToRight + 1)
    	  * note : itemsToleft are those with which sub array may start but end with current item.
    	  * itemToright are those with which sub arrays may end but start with current item, hence multiply for total.
    	  * 
    	  * formula to find its total contribution = (itemsToleft +1) * (itemsToRight + 1) * item.
    	  * 
    	  * 3. We need to have a track of index of next smaller element, and previous smaller element. 
    	  * total number of items to an element's left will be = (currIndex-IndexOfPSE)
    	  * total number of items to an element's right will be = (IndexOfNSE-currIndex)
    	  * 
    	  * algo : 
    	  * 1. total sum = 0.
    	  * 2. for each item do:
    	  * 	contribution = (currIndex-PSE[currIndex]) * (NSE[currIndex]-currIndex) * currItem
    	  * 	totalSum = totalSum + contribution
    	  * 
    	  */
    	 
    	 long totalSum = 0;
         int[] pse = prevSmallerElement(arr);
         int[] nse = nextSmallerElement(arr);
         int mod = 1000000007;
         for(int i=0; i<arr.length; i++){
             long leftCount = (i - pse[i]) % mod;
             long rightCount = (nse[i] - i) % mod;
             long icontri = (leftCount * rightCount * arr[i]) % mod;
             totalSum = (totalSum + icontri)%mod;
         }
         return (int)totalSum;
     }

     public int[] nextSmallerElement(int[] arr){
         Stack<Integer> stk = new Stack<Integer>();
         int[] nse = new int[arr.length];
         for(int i = arr.length-1; i >=0; i--){
             while(!stk.isEmpty() && arr[stk.peek()] >= arr[i])
                 stk.pop();
             
             nse[i] = stk.isEmpty()?arr.length:stk.peek();
             stk.push(i);
         }
         return nse;
     }

     public int[] prevSmallerElement(int[] arr){
         Stack<Integer> stk = new Stack<Integer>();
         int[] pse = new int[arr.length];
         for(int i = 0; i<arr.length; i++){
             while(!stk.isEmpty() && arr[stk.peek()] > arr[i])
                 stk.pop();
             
             pse[i] = stk.isEmpty()?-1:stk.peek();
             stk.push(i);
         }
         return pse;
     }

     public int[] asteroidCollision(int[] asteroids) {
         
    	 /*
    	  * 735. Asteroid Collision
    	  * 
    	  *  
    	  *
    	  */
         List<Integer> stk = new ArrayList<>();
         for(int i = 0; i<asteroids.length; i++){
             if(asteroids[i] > 0){
                 stk.add(asteroids[i]);
             }
             else{
                 while(!stk.isEmpty() && stk.get(stk.size()-1) > 0 && Math.abs(asteroids[i]) > stk.get(stk.size()-1)){
                     stk.remove(stk.size()-1);
                 }
                 if(!stk.isEmpty() && stk.get(stk.size()-1) == Math.abs(asteroids[i]))
                     stk.remove(stk.size()-1);
                 else if(stk.size()==0 || stk.get(stk.size()-1) < 0)
                     stk.add(asteroids[i]);
             }
         }
         return stk.stream().mapToInt(Integer::intValue).toArray();
     }
     
     public long subArrayRanges(int[] nums) {
    	 
    	 /*
    	  * You are given an integer array nums. 
    	  * The range of a subarray of nums is the difference between the largest and smallest element in the subarray.
    	  * Return the sum of all subarray ranges of nums.
    	  * A subarray is a contiguous non-empty sequence of elements within an array.
    	  * 
    	  * */
         long sumOfMinSubarraysSum = minSubarraySums(nums);
         long sumOfMaxSubarraysSum = maxSubarraySums(nums);

         return sumOfMaxSubarraysSum - sumOfMinSubarraysSum;
     }
     
     public long minSubarraySums(int[] nums){
         int[] pse = pse(nums);
         int[] nse = nse(nums);
         long total = 0;
         for(int i = 0; i<nums.length; i++){
             int left = i - pse[i];
             int right = nse[i] - i;
             long contri = (long) left * right * nums[i];
             total+=(long)contri;
         }
         return total;
     }

     public int[] nse(int[] nums){
         int[] nse = new int[nums.length];
         Stack<Integer> stk = new Stack<>();
         for(int i=nums.length-1; i>=0; i--){
             while(!stk.isEmpty() && nums[stk.peek()] >= nums[i]){
                 stk.pop();
             }
             nse[i] = stk.isEmpty()?nums.length:stk.peek();
             stk.push(i);
         }
         return nse;
     }

     public int[] pse(int[] nums){
         Stack<Integer> stk = new Stack<>();
         int[] pse = new int[nums.length];
         for(int i=0; i<nums.length; i++){
             while(!stk.isEmpty() && nums[stk.peek()] > nums[i]){
                 stk.pop();
             }
             pse[i] = stk.isEmpty()?-1:stk.peek();
             stk.push(i);
         }
         return pse;
     }


     public long maxSubarraySums(int[] nums){
         int[] pge = pge(nums);
         int[] nge = nge(nums);
         long total = 0;
         for(int i = 0; i<nums.length; i++){
             int left = i - pge[i];
             int right = nge[i] - i;
             long contri = (long) left * right * nums[i];
             total+= (long) contri;
         }
         return total;
     }

     public int[] pge(int[] nums){
         Stack<Integer> stk = new Stack<>();
         int[] pge = new int[nums.length];
         for(int i=0; i<nums.length; i++){
             while(!stk.isEmpty() && nums[stk.peek()] < nums[i]){
                 stk.pop();
             }
             pge[i] = stk.isEmpty()?-1:stk.peek();
             stk.push(i);
         }
         return pge;
     }

     public int[] nge(int[] nums){
         int[] nge = new int[nums.length];
         Stack<Integer> stk = new Stack<>();
         for(int i=nums.length-1; i>=0; i--){
             while(!stk.isEmpty() && nums[stk.peek()] <= nums[i]){
                 stk.pop();
             }
             nge[i] = stk.isEmpty()?nums.length:stk.peek();
             stk.push(i);
         }
         return nge;
     }
     
     public String removeKdigits(String num, int k) {
    	 /*
    	  * Given string num representing a non-negative integer num, and an integer k, 
    	  * return the smallest possible integer after removing k digits from num.
    	  * 
    	  * Example 1: Input: num = "1432219", k = 3
    	  * Output: "1219"
    	  * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
    	  * 
    	  * Intution : a number is smallest if all numbers from left to right are in increasing order.
    	  * to remove k digits such that final number is smallest, we need to find those sub arrays
    	  * that have strictly increasing order of digits. 
    	  * 
    	  * Brute force : find size of String. Find all possible sub arrays. mark a sub array as result
    	  * if it has strictly increasing digits.
    	  * 
    	  * Optimal : traverse from left to right, keep a digit in resultant string if it larger than prev.
    	  * Edge cases : 
    	  * 1. when after removing k items, leading digits are 0. 
    	  * 2. when array is already minimum, so k values from right must be removed.
    	  * 3. when k = size of array.
    	  * 
    	  * */
    	 
         if(k == num.length())
             return "0";
             
         StringBuilder stk = new StringBuilder();
         for(int i=0; i<num.length(); i++){
             while(!stk.isEmpty() && num.charAt(i) < top(stk) && k!=0){
                 k--;
                 pop(stk);
             }
             stk.append(num.charAt(i));
         }
         
         while(!stk.isEmpty() && stk.charAt(0) == '0')
             stk.deleteCharAt(0);
         
         while(!stk.isEmpty() && k!=0){
             pop(stk);
             k--;
         }

         if(stk.isEmpty()){
             stk.append('0');
         }
             
         return stk.toString();
     }

     public char top(StringBuilder stk){
         return stk.charAt(stk.length()-1);
     }
     public void pop(StringBuilder stk){
          stk.deleteCharAt(stk.length()-1);
     }
     
     public int largestRectangleArea(int[] heights) {
    	 /*
    	  *Given an array of integers heights representing the histogram's bar height where the width of each bar is 1, 
    	  *return the area of the largest rectangle in the histogram.
    	  *
    	  *Input: heights = [2,1,5,6,2,3]
    	  *Output: 10
    	  * 
    	  * Brute force : 
    	  * find contribution of individual bar, compare with maximum. return.
    	  * contribution of individual bar = area of all the bars start from x to y multiplied its length.
    	  * 
    	  * Time = O(5n)
    	  * Space = O(4n)
    	  * 
    	  * */
         int[] pse = pse(heights);
         int[] nse = nse(heights);
         int largestArea = 0;
         for(int i=0; i<heights.length; i++){

             int contri = heights[i] * (nse[i] - pse[i] - 1);
             largestArea = contri>largestArea?contri:largestArea;
         }
         return largestArea;
     }
}
