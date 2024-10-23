package piyush;

public class LinkedListProblems {

	public static ListNode middleNode(ListNode head) {
		
		/*
		 * Middle of the Linked List
		 * 
		 * */
		
        if(head.next == null){
            return head;
        }
        ListNode slow = head;
        ListNode fast = head;
        while(fast !=null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
	
	public ListNode detectCycle(ListNode head) {
		/*
		 * Find starting point of the loop in linked list.
		 * return null if loop does not exist.
		 * 
		 * Brute force : keep map of to save nodes while visiting them.
		 * 
		 * Optimal : slow and fast pointer.
		 * Time = O(n) space = O(n)
		 * 
		 * */
        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next !=null){
            slow = slow.next;
            fast = fast.next.next;
            if(slow==fast){
                slow = head;
                while(slow != fast){
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }
	
}
