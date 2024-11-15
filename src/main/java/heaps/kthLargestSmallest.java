package heaps;

import java.util.PriorityQueue;

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
}
