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
		
		/*
		 * convert 1->2->3->4->5 to 1->3->5->2->4
		 * 
		 * 
		 * 
		 * */
		
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
	
	public ListNode removeNthFromEnd(ListNode head, int n) {
		/*
		 * Brute force : in one pass calculate size on list.
		 * using formula nth node from end is Size-n+1 from front,
		 * take a pointer to one prev and remove links.
		 * 
		 * time = O(2n)
		 * 
		 * Optimal : using a window of size n, consider boundary conditions.
		 * take previous pointer and tail pointer as window. remove links from
		 * previous pointer.
		 * 
		 * Time = O(n)
		 * Space = O(1)
		 * 
		 * */
		
        if(head.next == null)
            return null;
        ListNode tail = head;
        ListNode onePrev = head;
        for(int i=0; i<n; i++){
            tail = tail.next;
        }
        if(tail == null){
            return head.next;
        }
        while(tail.next != null){
            tail = tail.next;
            onePrev = onePrev.next;
        }
        onePrev.next = onePrev.next.next;
        return head;
    }
	
	public ListNode deleteMiddle(ListNode head) {
		
		/*
		 * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
		 * For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.
		 * 
		 * Brute force : two pass algo, to find total count and navigate to node one before middle.
		 * Time = O(2n)
		 * 
		 * Optimal : use slow and fast pointer to reach end of list, since we need to keep previous one before
		 * the middle node, create a window of 1 in slow and fast.
		 * 
		 * Time = O(n)
		 * 
		 * */
		
        if(head.next == null)
            return null;
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return head;
    }
	
	public static ListNode sortList(ListNode head) {
		
		/*
		 * Sort a singly linkedlist. 
		 * Used Merge sort technique. [divide and merge]
		 * 1. divide list into two.
		 * 2. sort left, sort right.
		 * 3. merge left list and right list.
		 * 4. return new head.
		 * 
		 * Time = O(nlong)
		 * Space = O(1)
		 * 
		 * */
		
        if(head == null || head.next == null)
            return head;
        ListNode midPrev = getPrevOfMid(head);
        ListNode mid = midPrev.next;
        midPrev.next = null;
        ListNode sortedA = sortList(head);
        ListNode sortedB = sortList(mid);
        head = mergeSortedLists(sortedA, sortedB);
        return head;
    }
	
    public static ListNode mergeSortedLists(ListNode headA, ListNode headB){
        if(headA==null)
            return headB;
        else if(headB==null)
            return headA;

        ListNode resultH = null;
        ListNode resultCurr = null;
        ListNode curA = headA;
        ListNode curB = headB;
        while(curA!=null && curB!=null){
            if(curA.val < curB.val){
                if(resultH==null){
                    resultH = curA;
                    resultCurr = resultH;
                }
                else{
                    resultCurr.next = curA;
                    resultCurr = resultCurr.next;
                }
                curA = curA.next;
            }
            else{
                if(resultH==null){
                    resultH = curB;
                    resultCurr = resultH;
                }
                else{
                    resultCurr.next = curB;
                    resultCurr = resultCurr.next;
                }
                curB = curB.next;
            }
        }
        while(curA!=null){
            resultCurr.next = curA;
            resultCurr = resultCurr.next;
            curA=curA.next;
        }
        while(curB!=null){
            resultCurr.next = curB;
            resultCurr = resultCurr.next;
            curB=curB.next;
        }
        return resultH;
    }

    private static ListNode getPrevOfMid(ListNode head){
        if(head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head.next;
        while(fast.next!=null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
    	
    	/*
    	 * Given the heads of two singly linked-lists headA and headB, 
    	 * return the node at which the two lists intersect. If the two linked lists have no intersection at all, 
    	 * return null.
    	 * 
    	 * */
    	
    	
        ListNode currA = headA;
        ListNode currB = headB;
        while(currA != null && currB != null){
            if(currA == currB){
                return currA;
            }
            currA = currA.next;
            currB = currB.next;
            if(currA == null)
                currA = headA;
            else if(currB == null)
                currB = headB;
        }
        while(currA != null && currB != null){
            if(currA == currB){
                return currA;
            }
            currA = currA.next;
            currB = currB.next;
        }
        return null;
    }
	
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    	
    	/*
    	 * given two non-empty linked lists representing two non-negative integers. 
    	 * The digits are stored in reverse order, and each of their nodes contains a single digit.
    	 * Add the two numbers and return the sum as a linked list.
    	 * 
    	 * E.g. 
    	 * Input: l1 = [2,4,3], l2 = [5,6,4]
    	 * Output: [7,0,8]
    	 * Explanation: 342 + 465 = 807.
    	 * 
    	 * */
    	
        ListNode dummyNode = new ListNode(-1,null);
        int carry = 0;
        ListNode resCurr = dummyNode;
        ListNode currA = l1;
        ListNode currB = l2;

        while(currA!=null && currB!=null){
            int sum = currA.val + currB.val + carry;
            carry = sum/10;
            int nodeValue = sum%10;
            resCurr.next = new ListNode(nodeValue, null);
            resCurr = resCurr.next;
            currA = currA.next;
            currB = currB.next;
        }

        while(currA!=null){
            int sum=currA.val + carry;
            carry = sum/10;
            int nodeValue = sum%10;
            resCurr.next = new ListNode(nodeValue, null);
            resCurr = resCurr.next;
            currA = currA.next;
        }
        while(currB!=null){
            int sum=currB.val + carry;
            carry = sum/10;
            int nodeValue = sum%10;
            resCurr.next = new ListNode(nodeValue, null);
            resCurr = resCurr.next;
            currB = currB.next;
        }
        if(carry > 0){
            resCurr.next = new ListNode(carry, null);
        }

        return dummyNode.next;
    }
}
