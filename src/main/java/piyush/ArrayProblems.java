package piyush;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
    
    public static int[] rearrangeArray(int[] nums) {
    	/*
    	 * 2149. Rearrange Array Elements by Sign
    	 * 1. order of elements must retain the order they are in actual array.
    	 * 2. rearranged array must start with positive value.
    	 * 3. there will be same number of positive and negative integers.
    	 * 
    	 * Brute force : create two lists, P and N to store all positive and negative elements respectively.
    	 * iterate on nums, push element in repective helper lists.
    	 * again iterate on helper lists to store back items in required order.
    	 * Time = O(n) + O(n) | Space = O(n)
    	 * 
    	 * Better : we dont need to do modifications inplace. hence recreate a result array, while iterating on 
    	 * nums. this will reduce an iteration on nums, compared to brute force.
    	 * 
    	 * Time = O(n) | Space = O(n)
    	 * */
    	
        int[] result = new int[nums.length];
        int pos = 0;
        int neg = 1;
        for(int index = 0; index<nums.length; index++){
            if(nums[index] > 0){
                result[pos] = nums[index];
                pos+=2;
            }
            else{
                result[neg] = nums[index];
                neg+=2;
            }
        }
        return result;
    }
    
    public static void nextPermutation(int[] nums) {
    	
    	/*
    	 * 31. Next Permutation.
    	 * For example, for arr = [1,2,3], the following are all the permutations of arr: 
    	 * [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
    	 * 
    	 * for a given array, give its next lexicographical arrangement. return first arrangement
    	 * if there is no next arrangement possible.
    	 * 
    	 * Brute force : try all lexicoraphical arrangements, sort them in order and then iterate on 
    	 * sorted list to find the required array.
    	 * An array with n elements can be arranged in n factorial ways. n! = (n*(n-1)*...2*1) = 6
    	 * finding our next permute will require to iterate on all permutations.
    	 * 
    	 * Time = O(n!*n) | Space = O(n!)
    	 * 
    	 * Optimized : for lexicographically correct arrangement, i.e last arrangement there will be 
    	 * strictly increasing trend from backwards. E.g [5,4,3,2,1] it can not be further arranged.
    	 * 
    	 * 
    	 * if there is a violation at any place, then that will be the place where next greater value must come.
    	 * 
    	 * */
    	
        int bp = -1;
        //find the violation in array.
        for(int index = nums.length-2; index >=0; index--){
            if(nums[index] < nums[index+1]){
                bp = index;
                break;
            }
        }
        if(bp == -1){
        	//no violation found, that means there is no next permutation exist.
            int left = bp+1;
            int right = nums.length-1;
            reverseArray(nums, left, right);
            return;
        }
        //swap with immdediate largest in right part.
        for(int index = nums.length-1; index > bp; index--){
            if(bp > -1 && nums[index] > nums[bp]){
                swap(nums, index, bp);
                break;
            }
        }
        //reverse nums after bp.
        int left = bp+1;
        int right = nums.length-1;
       reverseArray(nums, left, right);
    }
    
    public static void reverseArray(int[] nums, int left, int right) {
    	 while(left < right){
             swap(nums, left, right);
             left++;
             right--;
         }
    }
    
    public static int longestConsecutive(int[] nums) {
    	/*
    	 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
    	 * 
    	 * Input: nums = [100,4,200,1,3,2]
    	 * Output: 4 
    	 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
    	 * 
    	 * Brute Force : For every element in nums, traverse whole array and keep a count of streak that
    	 * makes. while maintaining max streak. return max streak at the end.
    	 * Time = O(n2)
    	 * Space = O(1)
    	 * 
    	 * Efficient approach : keep a set of unique elements of array, while iterating on set, check if 
    	 * previous element of current element exist in set, do not start a streak if yes.
    	 * if previous does not exists, then start a streak and count while maintaining maxStreak.
    	 * 
    	 * Time = O(n) : to put all in set. + O(n) : to iterate on each in set. + O(n) to find streak. = O(3n)
    	 * Space = O(n)
    	 * 
    	 * */
    	
        if(nums.length == 0)
            return 0;
        HashSet<Integer> collection = new HashSet<Integer>();
        int maxStreak = 1;
        for(int index = 0; index < nums.length; index++){
            collection.add(nums[index]);
        }
        for(Integer item : collection) {
        	if(!collection.contains(item-1))
        	{
        		int streak = 1;
        		Integer target = item + 1;
        		while(collection.contains(target)) {
        			streak++;
        			target++;
        		}
        		if(streak > maxStreak) {
        			maxStreak = streak;
        		}
        	}
        }
        return maxStreak;
    }
    
    public static void setZeroes(int[][] matrix) {
    	
    	/*
    	 * Given an m x n integer matrix matrix, if an element is 0, set its entire row and column to 0's
    	 * 
    	 * Brute force : for each item in matrix, if value is 0, then iterate on whole row and column,
    	 * to mark elements of that row/column as -1.
    	 * In this way no modified item will cause any inconsistency to non zeros entries.
    	 * Again iterate on whole matrix to mark -1 items as 0.
    	 * 
    	 * Time = O(m*n) * O(m+n) ~ O(n3)
    	 * Space = O(m+n)
    	 * 
    	 * Better : keep flag arrays, to note those rows and columns which shall become 0.
    	 * populate flag arrays while iterating on matrix, such that if any 0 is encountered, flag arrays have 
    	 * yes/no to update matrix in the end.
    	 * 
    	 * Time = O(m*n) + O(m*n)
    	 * Space = O(m+n)
    	 * */
    	
    	 int rows = matrix.length;
         int columns= matrix[0].length;
         int[] rowStatus = new int[rows];
         int[] colStatus = new int[columns];

         for(int row = 0; row < rows; row++){
             for(int col = 0; col < columns; col++){
                 if(matrix[row][col] == 0){
                     rowStatus[row] = 1;
                     colStatus[col] = 1;
                 }
             }
         }
          for(int row = 0; row < rows; row++){
             for(int col = 0; col < columns; col++){
                 if(rowStatus[row] == 1 || colStatus[col] == 1){
                     matrix[row][col] = 0;
                 }
             }
         }
    }
    
    public static List<Integer> majorityElementII(int[] nums) {
        List<Integer> result = new ArrayList<>();
        int candidate1 = Integer.MIN_VALUE;
        int candidate2 = Integer.MIN_VALUE;
        int votes1 = 0;
        int votes2 = 0;

        for(int i=0; i<nums.length; i++){
            if(votes1 == 0 && nums[i]!=candidate2){
                candidate1 = nums[i];
                votes1 = 1;
            }
            else if(votes2 == 0 && nums[i]!=candidate1){
                candidate2 = nums[i];
                votes2 = 1;
            }
            else if(nums[i] == candidate1)
                votes1++;
            else if(nums[i] == candidate2)
                votes2++;
            else{
                votes1--; 
                votes2--;
            }
        }

        int minimumVotes = (nums.length/3) + 1;
        int finalVotes1 = 0;
        int finalVotes2 = 0;
        for(int i=0; i<nums.length; i++){
            if(nums[i] == candidate1) finalVotes1++;
            if(nums[i] == candidate2) finalVotes2++;
        }
        if(finalVotes1 >= minimumVotes)
            result.add(candidate1);
        if(finalVotes2 >= minimumVotes)
            result.add(candidate2);
        
        return result;
    }
}
