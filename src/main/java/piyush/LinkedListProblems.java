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
	
	public ListNode reverse(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode newH = reverse(head.next);
        ListNode front = head.next;
        front.next = head;
        head.next = null;
        return newH;
    }
	
	public boolean isPalindrome(ListNode head) {
		/*
		 * Brute force : keep all the values in a stack while traversing linkedlist.
		 * again traverse linkedlist, if stack.pop on each iteration is not same as current node, return false.
		 * 
		 * Time = O(2) Space = O(n)
		 * 
		 * Optimal : get to the node before the middle of list, after middle reverse the list.
		 * iterate from head, to middle and at the same time also see from middle to end of list.
		 * compare the two, if same then reverse back second half of list to normal and return true/false.
		 * 
		 * Time = O(2n)
		 * Space - O(1)
		 * 
		 * */
		
        if(head == null || head.next == null)
            return true;
        ListNode slow = head;
        ListNode fast = head;
        while(fast.next != null && fast.next.next != null){ //O(n/2)
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tempHead = reverse(slow.next); //O(n/2)
        ListNode start = head;
        ListNode middle = tempHead;
        while(middle != null){ //O(n/2)
            if(middle.val != start.val){
                reverse(tempHead); //O(n/2) only once
                return false;
            }
            middle = middle.next;
            start = start.next;
        }
        reverse(tempHead);//O(n/2) only once
        return true;
    }
	
	public static ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode evenHead = head.next;
       ListNode even = head.next;
       ListNode odd = head;

       while(even != null && even.next!= null){
        odd.next = odd.next.next;
        odd = odd.next;
        even.next = even.next.next;
        even = even.next;
       }
        odd.next = evenHead;
        return head;
    }
	
}
