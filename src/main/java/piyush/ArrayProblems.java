package piyush;

public class ArrayProblems {

	public static void sortColors(int[] nums) {
		
		/* PROBELM :
		Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.

		We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.

		You must solve this problem without using the library's sort function.

		Example 1:

		Input: nums = [2,0,2,1,1,0]
		Output: [0,0,1,1,2,2]
		Example 2:

		Input: nums = [2,0,1]
		Output: [0,1,2]
		
		 * Optimal Solution : 
		 * using three pointers, assume array have all zeros before a pointer low, have all twos after a pointer high.
		 * and mid pointer must assume that values from mid to high are unsorted.
		 * now for each value of mid till high, put 0 at low and increment low and mid. put 2 at high, decrement high.
		 * if encountered 1, then by logic, everything before mid is already sorted, then mid was expeted to have 1. just increment mid.
		 * Time = O(n) Space = O(1).
		 * 
		 * */
        int startOfOnes = 0;
        int startOfUnsorted = 0; //mid
        int endOfUnsorted = nums.length-1;

        while(startOfUnsorted <= endOfUnsorted){
            if(nums[startOfUnsorted] == 0){
                swap(nums, startOfUnsorted, startOfOnes);
                startOfOnes++;
                startOfUnsorted++;
            }
            else if(nums[startOfUnsorted] == 1){
                startOfUnsorted++;
            }
            else{
                swap(nums, startOfUnsorted, endOfUnsorted);
                endOfUnsorted--;
            }
        }
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public static int majorityElement(int[] nums) {
    	/*
    	 * PROBLEM : Given an array nums of size n, return the majority element. 
    	 * The majority element is the element that appears more than ⌊n / 2⌋ times. 
    	 * You may assume that the majority element always exists in the array.
    	 * 
    	 * SOLUTION : 
    	 * 1. Brute force : for each element count its occurence. O(n2).
    	 * 2. Better : iterate on each element, use map to keep count of each element. iterate on map to find result.
    	 * Space = O(n) Time = O(n)
    	 * 
    	 * 3. Enhanced. : Moore's Voting algorithm. while traversing, Score current element reward +1 if it is encountered
    	 * and -1 if any other element is encountered. 
    	 * 
    	 * Intuition : it is assumed that there always exist a majority element.
    	 * an element can be eligible to qualify as majority only after complete traversal the elements's score is positive.
    	 * 
    	 * */
        int elem = nums[0];
        int count = 1;
        for(int index = 1; index <nums.length; index++){
            if(nums[index] == elem){
                count++;
            }
            else{
                count--;
            }
            if(count == 0){
                elem = nums[index];
                count++;
            }
        }
        return elem;
    }
    
    public static int maximumSubArraySum(int[] nums) {
    	
    	/*
    	 * Given an integer array nums, find the subarray with the largest sum, and return its sum.
    	 * 
    	 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4] 
    	 * Output: 6
    	 * Explanation: The subarray [4,-1,2,1] has the largest sum 6.
    	 * 
    	 * Brute Force : try all sub array combinations, maintain maximum sum. : 
    	 * Time = O(n3) 
    	 * 
    	 * Optimal 1 : Kadane's algorithm : starting with maximumSum = minimum possible value. 
    	 * traverse the array, for each item maintain a currentSum, if currentSum is greater than maximumSum
    	 * then choose currentSum as maximumSum. while we are maintaining currentSum by adding array elements.
    	 * we will reset currentSum to zero and start over the hunt of finding maximum sum when currentSum goes less than 0,
    	 * while also maintaining maximumSum. 
    	 * 
    	 * Optimal 2: Divide and conquer. 
    	 * */

        int maximumSum = Integer.MIN_VALUE;
        int currentSum = 0;
        for(int index = 0; index < nums.length; index++){
            currentSum = currentSum + nums[index];
            if(currentSum > maximumSum)
                {
                    maximumSum = currentSum;
                }
            if(currentSum < 0){
                currentSum = 0;
            }
        }
        return maximumSum;
    }
    
    public static int buyAndSellStock(int[] prices) {
    	
    	/*
    	 * You are given an array prices where prices[i] is the price of a given stock on the ith day.
    	 * You want to maximize your profit by choosing a single day to buy one stock and choosing a different day 
    	 * in the future to sell that stock. Return the maximum profit you can achieve from this transaction. 
    	 * If you cannot achieve any profit, return 0.
    	 * 
    	 * Input: prices = [7,1,5,3,6,4]
    	 * Output: 5
    	 * 
    	 * Brute Force : try every permutations of buying price and selling price, while maintaining 
    	 * max profit. Time = O(n2)
    	 * 
    	 * Optimal : We need to find the minimum possible buying price from left.
    	 * starting with first element, we can only sell at second value of prices.
    	 * any following element in prices 1. will give either better buying price or
    	 * 2. better profit.
    	 * 
    	 * hence iterate, if element is lesser than minimumBuy yet, choose for buying.
    	 * while if element is giving better profit, choose as maxProfit.
    	 * 
    	 * */
    	
    	 int maxProfit = 0;
         int minimumBuy = prices[0];
         for(int sellAt = 1; sellAt < prices.length; sellAt++){
             int currentProfit = prices[sellAt] - minimumBuy;
             if(currentProfit > maxProfit){
                 maxProfit = currentProfit;
             }
             if(prices[sellAt] < minimumBuy){
                 minimumBuy = prices[sellAt];
             }
         }
     return maxProfit;
    }
}
