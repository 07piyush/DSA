package heaps;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class kthLargestSmallest {
	/*
	 * Heap : by definition always has largest/smallest element at the top. So for MinHeap top will be smallest element.
	 * and for MaxHeap, top will be largest element. 
	 * 
	 * A heap of size k will contain only k element, anything else will be popped.
	 * 
	 * Sorting at best performs at O( n*logn).
	 * To find kth largest/smallest, we can sort an array and look at K-th position. but problem is, in this way we
	 * are sorting whole array, while we dont really care about those numbers which are greater/smaller than k-th.
	 * 
	 * Hence to find K-th minimum in an array, use max heap of size k. 
	 * why? : max heap will always have largest at top, heap of size k means small to large items are there in heap,
	 * since heap size is fixed, so any greater element will be rejected, and at the end of array traversal heap will
	 * have kth smallest at top.
	 * 
	 * since for any problem with input size N, when answer lies at top K size then untill K size is reached
	 * we will add an element in heap if element is smaller than top of heap, and maintain protocol of heap,
	 * we will sort the heap (heapify). 
	 * 
	 * So time complexity reduces from nlogn to nlogK.
	 * 
	 * 
	 * */

	public int findKthLargest(int[] nums, int k) {
		/*
		 * Given an integer array nums and an integer k, return the kth largest element in the array.
		 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
		 * 
		 * Brute force : 1. create duplicate of nums. sort nums2, find unique kth item from right.
		 * Time = N (duplicating) + NlogN (sorting) + N (finding) | Space = N
		 * 2. create an array with unique elements of nums, sort it, return size-k-1 element.
		 * Time = N(duplicating) + Nlogn.  |  Space = N
		 * 
		 * Optimal : using minHeap. Heap always stores unique values, it would never keep duplicate items.
		 * 
		 * */
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		for(int num : nums){
			minHeap.add(num);
			if(minHeap.size() > k)
				minHeap.poll();
		}
		return minHeap.peek();
	}

	public int[] topKFrequent(int[] nums, int k) {
		/*
		 * Given an integer array nums and an integer k, 
		 * return the k most frequent elements. You may return the answer in any order.
		 * 
		 * Input: nums = [1,1,1,2,2,3], k = 2 
		 * Output: [1,2]
		 * 
		 * */
		HashMap<Integer, Integer> map = new HashMap<>();
		int[] result = new int[k];

		PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
				(a,b) -> a.getValue() - b.getValue()
				);
		for(int i=0; i < nums.length; i++) {
			map.put(nums[i], map.getOrDefault(nums[i],0)+1);
		}

		for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
			minHeap.add(entry);
			if(minHeap.size() > k) {
				minHeap.poll();
			}
		}
		int resIndex = 0;
		while(minHeap.size() > 0) {
			result[resIndex++] = minHeap.poll().getKey();
		}

		return result;
	}

	public String kthLargestNumber(String[] nums, int k) {
		/*
		 * You are given an array of strings nums and an integer k. Each string in nums represents an integer without leading zeros.
		 * Return the string that represents the kth largest integer in nums. 
		 * Note: Duplicate numbers should be counted distinctly. For example, if nums is ["1","2","2"], "2" is the 
		 * first largest integer, "2" is the second-largest integer, and "1" is the third-largest integer.
		 * 
		 * Example 1: Input: nums = ["3","6","7","10"], k = 4
		 * Output: "3"
		 * Explanation: The numbers in nums sorted in non-decreasing order are ["3","6","7","10"].
		 * The 4th largest integer in nums is "3".
		 * 
		 * Custom comparator : since value of individual string representation of a number can be of any size
		 * using Long or BigInt will not handle cases with numbers out of their limit, also will have overhead of
		 * object creation.
		 * 
		 * using custom comparator to use properties of string representation of number. 
		 * 1. larger string length larger the number.
		 * 2. for same string lengths, two lexicographical ordered string are ordered by order of individual char.
		 * and lexicographically individual character '1', '2', '3', ...'9' is same order as 1, 2, 3...9. 
		 * 
		 * */
		PriorityQueue<String> minHeap = new PriorityQueue<>(
				(a,b) -> {
					if(a.length() != b.length()){
						return Integer.compare(a.length(), b.length());
					}
					return a.compareTo(b);
				}
				);
		for(String str : nums){
			minHeap.add(str);
			if(minHeap.size() > k){
				minHeap.poll();
			}
		}
		return minHeap.peek();
	}

	public int[][] kClosest(int[][] points, int k) {
		/*
		 * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, 
		 * return the k closest points to the origin (0, 0).
		 * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
		 * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
		 * 
		 * Solution : we need to find distance of each point from origin.
		 * distance squared of two points = (x2-x1) + (y2-y1);
		 * 
		 * for each point using max heap (maximum distance at top), remove all those after comes after k.
		 * 
		 * */
		PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
				(a,b) -> b.getValue()-a.getValue()
				);

		HashMap<Integer, Integer> distances = new HashMap<>();
		int totalPoints = points.length;
		for(int point=0; point<totalPoints; point++){
			int distSqrd = points[point][0]*points[point][0] + points[point][1]*points[point][1];
			distances.put(point, distSqrd);
		}
		for(Map.Entry<Integer, Integer> dist : distances.entrySet()){
			maxHeap.add(dist);
			if(maxHeap.size() > k){
				maxHeap.poll();
			}
		}
		int[][] result = new int[maxHeap.size()][2];
		int currIndex = 0;
		while(maxHeap.size() > 0){
			Map.Entry<Integer, Integer> distance = maxHeap.poll();
			result[currIndex][0] = points[distance.getKey()][0];
			result[currIndex][1] = points[distance.getKey()][1];
			currIndex++;
		}
		return result;
	}
	
	 public boolean isPossibleDivide(int[] nums, int k) {
		 /*
		  * This solution is not heap based.
		  * 
		  * Given an array of integers nums and a positive integer k, check whether it is possible to divide this array 
		  * into sets of k consecutive numbers. Return true if it is possible. Otherwise, return false.
		  * 
		  * Input: nums = [1,2,3,3,4,4,5,6], k = 4 Output: true
		  * Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].
		  * 
		  * using a ordered map which is treeMap, we can keep track of sorted nums along with their frequency.
		  * for each unique num in freqmap, from small to large there must exist 
		  * same frequency of nums+1, nums+2...nums+k.
		  * 
		  * return false if not.
		  * 
		  * */
	        Map<Integer, Integer> freq = new TreeMap<>();

	        for(int i=0; i<nums.length; i++)
	            freq.put(nums[i], freq.getOrDefault(nums[i], 0)+1);

	        for(int num : freq.keySet()){
	            int count = freq.get(num);
	            if(count > 0){
	                
	                for(int partOfSubset = num+1; partOfSubset < num + k; partOfSubset++){
	                    if(freq.containsKey(partOfSubset) && 
	                        freq.get(partOfSubset) >= count){
	                            int currFreq = freq.get(partOfSubset);
	                            freq.put(partOfSubset, currFreq-count);
	                        }
	                        else
	                            return false;
	                }
	            }
	        }
	        return true;
	    }
}
