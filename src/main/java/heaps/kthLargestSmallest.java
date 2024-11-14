package heaps;

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
	 * 
	 * 
	 * 
	 * */
}
