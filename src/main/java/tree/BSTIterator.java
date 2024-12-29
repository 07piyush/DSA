package tree;

import java.util.Stack;

public class BSTIterator {
	
	private Stack<TreeNode> stk;

	public BSTIterator(TreeNode root) {
		stk = new Stack<>();
		//initialize the stack with left most (lowest value).
		TreeNode ptr = root;
		while(ptr!=null) {
			stk.push(ptr);
			ptr = ptr.left;
		}
		
	}
	
	public int next() {
		//top element in stack will give next element in in-order traversal for current tree.
		//This is iterator class hence every-time next is done, iterator's state will change.
		TreeNode node = stk.pop();
		int value = node.val;
		if(node.right != null) {
			stk.push(node.right);
			node = node.left;
			while(node!=null) {
				stk.push(node);
				node = node.left;
			}
		}
		return value;
	}
	
	public boolean hasNext() {		
		return !stk.isEmpty();
	}
}
