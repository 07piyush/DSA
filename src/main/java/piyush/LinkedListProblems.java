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
	
}
