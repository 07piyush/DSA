package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {

	int maximum = 0;
	List<List<Integer>> levelOrderedTree;
	Queue<TreeNode> que;
	
	public List<List<Integer>> levelOrder(TreeNode root) {
		/*
		 * Level Order Traversal.
		 * 
		 * */
		levelOrderedTree = new ArrayList<>();
		if(root == null)
			return levelOrderedTree;
		que = new LinkedList<>();
		que.add(root);
		getLevelOrder(root);
		return levelOrderedTree;
    }
	
	private void getLevelOrder(TreeNode root) {
		while(que.size() > 0) {
			ArrayList<Integer> list = new ArrayList<>();
			int itemsAtCurrLevel = que.size();
			for(int i=0; i<itemsAtCurrLevel; i++) {
				TreeNode node = que.poll();
				list.add(node.val);
                if(null != node.left)
    				que.add(node.left);
                if(null != node.right)
				    que.add(node.right);
			}
            levelOrderedTree.add(list);
		}
	}
	
	public int diameterOfBinaryTree(TreeNode root) {
		/*
		 * Given the root of a binary tree, return the length of the diameter of the tree.
		 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. 
		 * This path may or may not pass through the root.
		 * The length of a path between two nodes is represented by the number of edges between them.
		 * 
		 * */
        maxHeight(root);
        return maximum;
    }

    private int maxHeight(TreeNode root){
        if(root == null)
            return 0;
        int left = maxHeight(root.left);
        int right = maxHeight(root.right);
        maximum = Math.max(maximum, left+right);
        return Math.max(left, right) + 1;
    }
    
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
    	List<List<Integer>> res = new ArrayList<>();
    	Deque<TreeNode> que = new LinkedList<>();
    	boolean reverse = false;
    	if(root == null)
    		return res;
    	que.addFirst(root);
    	
    	while(!que.isEmpty()) {
    		List<Integer> elements = new ArrayList<>();
    		int count = que.size();
    		for(int i=0; i<count; i++) {
    			TreeNode node = que.pollFirst();
    			elements.add(node.val);
    			if(node.left != null) que.addLast(node.left);
    			if(node.right != null) que.addLast(node.right);
    		}
    		if(reverse) {
    			Collections.reverse(elements);
    			reverse = false;
    		}
    		else {
    			reverse = true;
    		}    			
    		res.add(elements);
    	}
    	
    	
    	return res;
    }
}
