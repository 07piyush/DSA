package searchingAlgos;

public class Search {

	public int iterativeBinarysearch(int nums[], int target) {
		//return index of target in nums, -1 if not found
		int left = 0;
		int right = nums.length-1;

		while(left<=right) {

			int mid = (left+right)/2;

			if(nums[mid] == target)
				return mid;
			else if(target > nums[mid])
				left = mid +1;
			else
				right = mid-1;
		}
		return -1;
	}

	public int recursiveBinarySearch(int[] nums, int left, int right, int target){
		if(left > right)
			return -1;
		int mid = (left+right)/2;
		if(nums[mid] == target)
			return mid;
		if(target > nums[mid])
			return recursiveBinarySearch(nums, mid+1, right, target);
		else
			return recursiveBinarySearch(nums, left, mid-1, target);
	}

	public int lowestIndexOfTargetIterative(int[] nums, int target) {
		/*
		 * aka lower bound is the smallest index such that nums[index] >= target.
		 * aka first occurace of a number in a sorted array.
		 * 
		 * aka Find first position of an element in a sorted array.
		 * 
		 * */
		int firstOcc = -1;
		int left = 0;
		int right = nums.length-1;

		while(left<=right){
			int mid = (left+right)/2;
			if(nums[mid] == target){
				firstOcc = mid;
				right = mid-1;
			}
			else if(nums[mid] > target)
				right = mid-1;
			else
				left = mid+1;
		}

		return firstOcc;
	}

	public int highestIndexOfTargetIterative(int [] nums, int target) {

		/*
		 * aka last occurrence of an element in a sorted array.
		 * aka upper bound of a target in a sorted array.
		 * 
		 * aka Find last position of an element in a sorted array.
		 * 
		 * */

		int lastOcc = -1;
		int left = 0;
		int right = nums.length-1;
		while(left<=right){
			int mid = (left+right)/2;
			if(nums[mid] == target){
				lastOcc = mid;
				left = mid+1;
			}
			else if(nums[mid] > target)
				right = mid-1;
			else
				left = mid+1;
		}
		
		return lastOcc;
	}
	
	public int[] searchRange(int[] nums, int target) {
		/*
		 * Find first and last position of an element in a sorted array.
		 * 
		 * */
		
		int[] result = new int[2];
		
		result[0] = lowestIndexOfTargetIterative(nums, target);
		result[1] = highestIndexOfTargetIterative(nums, target);
		
		return result;
	}
	
	public int searchInRotatedSortedArrayWithUniqueElements(int[] nums, int target) {
		/*
		 * Search in Rotated Sorted Array.
		 * There is an integer array nums sorted in ascending order (with distinct values).
		 * array is rotated by random number.
		 * 
		 * Given the array nums after the possible rotation and an integer target, 
		 * return the index of target if it is in nums, or -1 if it is not in nums.
		 * 
		 * Example : nums = [4,5,6,7,0,1,2], target = 0 
		 * Output: 4
		 * 
		 * Brute force : linear search traverse whole array, return index when found the target.
		 * 
		 * Optimal : since array was originally sorted, so even when rotated, some part of array will still be sorted.
		 * Idea : when array is rotated, then by each rotation start index of sorted array get shifted in the direction
		 * of rotation.
		 * 
		 * so while we find the middle, every time. we know that either left or right part of middle will
		 * always be sorted. we go search target in wrong direction only when target is not in range of 
		 * sorted part of array.
		 * 
		 * */
		
        int left = 0;
        int right = nums.length-1;

        while(left <= right){
            int mid = (left+right)/2;

            if(nums[mid] == target){
                return mid;
            }
            //if left part is sorted
            if(nums[left] <= nums[mid]){
                if(nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
            else{
                if(nums[mid] < target && target <= nums[right])
                    left = mid +1;
                else
                    right = mid-1;
            }
        }

        return -1;
    }
	
	public boolean searchInRotatedSortedArrayWithDuplicateElements(int[] nums, int target) {
		/*
		 * extending to above problem, when array has duplicate elements, then 
		 * for a particular case, it is possible that we can not tell which part might be sorted.
		 * 
		 * E.g: [3,4,5,6,3,3,3,3,3] 
		 * here nums[0] = 3 : low
		 * 		nums[4] = 3 : mid
		 * 		nums[7] = 3 : right
		 * 
		 * Solution : move left, right by one, and narrow down the search space.
		 * 
		 * */
        int left = 0;
        int right = nums.length-1;

        while(left<=right){
            int mid = (left+right)/2;
            if(nums[mid] == target)
                return true;

            if(nums[left] == nums[mid] && nums[mid] == nums[right]){
                left++;
                right--;
                continue;
            }
            
            if(nums[left] <= nums[mid]){
                //array is sorted on left side.
                if(nums[left] <= target && target < nums[mid])
                    right = mid-1;
                else
                    left = mid+1;
            }
            else{
                //array is sorted on right side.
                if(target > nums[mid] && target <= nums[right])
                    left = mid+1;
                else
                    right = mid-1;
            }
        }
        return false;
    }
	

	public int findMin(int[] nums) {
		/*
		 * find minimum element in a sorted array, which is rotated arbitrary times.
		 * 
		 * Solution : if an array is rotated, then the side which is in sorted order may or may not have
		 * the minimum element, even if sorted part have minimum element, we can always check that in constant time
		 * as minimum in sorted is always leftmost. i.e nums[left].
		 * 
		 * so check minimum from sorted, and continue the search on unsorted part.
		 * 
		 * Time = O(log n)
		 * */
		
        int left = 0;
        int right = nums.length-1;
        int min = Integer.MAX_VALUE;
        while(left <= right){
            int mid = (left+right)/2;
            if(nums[mid] < min)
                min = nums[mid];
            if(nums[left] <= nums[mid]){
                min = nums[left]<min?nums[left]:min;
                left = mid+1;
            }
            else{
                min = nums[mid+1]<min?nums[mid+1]:min;
                right = mid-1;
            }
        }
        return min;
    }
	
	public int singleNonDuplicate(int[] nums) {
		/*
		 * You are given a sorted array consisting of only integers where every element appears exactly twice, 
		 * except for one element which appears exactly once. Return the single element that appears only once.
		 * 
		 * E.g. : [1,1,2,3,3,4,4,8,8]
		 * Output : 2
		 * 
		 * brute force : linear search, return the element that is same as its next.
		 * 
		 * Optimal : since the array is sorted, some how we can eliminate one of the half.
		 * 
		 * observation. 1. pair will be in combination of (odd, even) index, unless a single is encountered.
		 * once a single is encountered, pair will be found in (even, odd) indices.
		 * 
		 * hence when mid is standing at a point, either it is single himself.
		 * or single reside in left half or in right half. 
		 * if middle and left are same while mid is at odd index, or middle and right are same while mid is
		 * at even index. then all values to left of middle are pairs.
		 *  		 L			 M				R	
		 * 		 [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]			
		 * E.g : [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6]
		 * 		 [O, E, O, E, O, E, O, E, O, E, O, E
		 * */
        int left = 0;
        int right = nums.length-1;
        int result = -1;
        if(nums.length == 1)
            return nums[0];
        if(nums[0] != nums[1])
            return nums[0];
        if(nums[nums.length-1] != nums[nums.length-2])
            return nums[nums.length-1];
        left++;
        right--;
        while(left<=right){
            int mid = (left+right)/2;

            if(nums[mid] != nums[mid+1] && nums[mid-1] != nums[mid])
                return nums[mid];
            
            if(mid%2 == 0 && nums[mid+1] == nums[mid] || 
                mid%2==1 && nums[mid-1] == nums[mid])
                left = mid+1;
            else
                right = mid-1;
        }
        return result;
    }
	
	public int findPeakElement(int[] nums) {
		/*
		 * Find Peak Element 
		 * A peak element is an element that is strictly greater than its neighbors.
		 * 
		 * IMP NOTE : Constraints:
		 * 1 <= nums.length <= 1000 
		 * -231 <= nums[i] <= 231 - 1 
		 * nums[i] != nums[i + 1] for all valid i.
		 * 
		 * brute force : perform linear search, each element is either our answer or potential answer.
		 * let first element be our candidate. (since according to question, we can assume every thing before and after
		 * provided is -infinity)
		 * for each candidate check if next element is greater, if yes then candidate is our answer.
		 * if not then next element will become our candidate.
		 * 
		 * Time = O(n)
		 * 
		 * Optimal : if we find mid of array, it will satisfy any of three conditions :
		 * 1. mid is peak.
		 * 2. mid is part of increasing trend. : then peak lies in right part of mid.
		 * 3. mid is part of decreasing trend. : then peak lies in left part of mid.
		 * 
		 * 
		 * */
		
        if(nums.length == 1)
            return 0;
        if(nums[0] > nums[1])
            return 0;
        if(nums[nums.length-1] > nums[nums.length-2])
            return nums.length-1;
        int left = 1;
        int right = nums.length-2;

        while(left <= right){
            int mid = (left+right)/2;

            if(nums[mid-1] < nums[mid] && nums[mid] > nums[mid+1])
                return mid;
            
            if(nums[mid+1] < nums[mid])
                right = mid-1;
            else
                left = mid+1;
        }
        return -1;
    }
	
	public static int minEatingSpeed(int[] piles, int h) { 
		/*
		 * koko have piles of bananas, each index has a pile.
		 * she can eat k number of bananas in an hour.
		 * she has h hours in total, find minimum k, such that she eats k bananas each hour
		 * so that all bananas are finished in h hours.
		 * 
		 * Return the minimum integer k such that she can eat all the bananas within h hours.
		 * 
		 * E.g :Input: piles = [3,6,7,11], h = 8
		 * Output: 4
		 * 
		 * Explanation :when she can eat 1/2/3/4/5...10/11. bananas in an hour.
		 * to finish them in 4 hours if she eat 11 banana per hour. 
		 * she can not eat more than 11 (maximum possible) bananas in an hour.
		 * if she eats 2 bananas in an hour, she will require [2+3+4+6]=15 hours to finish but she has only 8.
		 * that means she must increase number of bananas per hour.
		 * 
		 *  so minimum can range in [1 : max(piles)].
		 *  
		 *  Brute force : for each hourlyCapacity[1:max(piles)] calculate time taken to finish, only return 
		 *  for first such capacity. else keep considering larger hourlyCapacity.
		 *  Time = O(max(piles)*n) == O(n2) if max is equal to size of array;
		 *  
		 * optimal : there is a hourlyCapacity, after which koko will be able to finish all bananas in h hours.
		 * but we are interested in minimum of that.
		 * 
		 * Eg. [1,2,3,4,5,6,7,8,9,10,11] : possible hourlyCapacity.
		 * 	   [x,x,x,Y,Y,Y,Y,Y,Y,Y ,Y ] : here that number is 4. so do a binary search for that.
		 * 
		 * 
		 * */
        int left = 1;
        int right = max(piles);

        while(left<=right){
            int mid = (left+right)/2;
            long hoursTaken = hoursTaken(piles, mid);
            if(hoursTaken <= h){
                right = mid-1;
            }
            else {
                left = mid+1;
            }
        }
        return left;
    }
    public static int hoursTaken(int[] piles, int capacity){
        int ans = 0;
        for(int i=0; i<piles.length; i++){
        	ans += Math.ceil((double)(piles[i]) / (double)(capacity));
        }
        return ans;
    }

    public static int max(int[] arr){
        int max = arr[0];
        for(int i=1; i<arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
}
