package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

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
    	
    	/*
    	 * the list values of individual level should have the order as per requirement.
    	 * either reverse it later or take care of the order at the time of insertion.
    	 * 
    	 * */
    	List<List<Integer>> res = new ArrayList<>();
    	Deque<TreeNode> que = new LinkedList<>();
    	boolean reverse = false;
    	if(root == null)
    		return res;
    	que.addFirst(root);
    	
    	while(!que.isEmpty()) {
    		int count = que.size();
            LinkedList<Integer> elements = new LinkedList<>();
    		for(int i=0; i<count; i++) {
    			TreeNode node = que.pollFirst();
    			if(reverse) {
    				elements.addFirst(node.val);
    			}
    			else {
        			elements.add(node.val);	
    			}
    			if(node.left != null) que.addLast(node.left);
    			if(node.right != null) que.addLast(node.right);
    		}
    		reverse=!reverse;
    		
    		res.add(elements);
    	}
    	return res;
    }
    Map<Integer, List<Integer>> register = new TreeMap<>();
    public List<List<Integer>> verticalTraversal(TreeNode root) {
    	
    	//incorrect implementation.
    	//need to do level order traversal.
    	
        List<List<Integer>> res = new ArrayList<>();
        if(root == null)
            return res;
        vT(root, 0);
        for(Map.Entry<Integer, List<Integer>> entry : register.entrySet()){
            res.add(entry.getValue());
        }
        
        return res;
    }
    
    private void vT(TreeNode root, int vlevel) {
    	if(root == null)
    		return;
    	List<Integer> list = register.getOrDefault(vlevel, new LinkedList<>());
    	list.add(root.val);
    	vT(root.left, vlevel-1);
    	vT(root.right, vlevel+1);
    }
    
    class Pair{
        public TreeNode node;
        public Integer vid;
        
        Pair(TreeNode n, Integer i){
            node = n;
            vid = i;
        }
    }
    public ArrayList<Integer> topView(TreeNode root) {
    	/*
    	 * Solution : traverse tree by level.
    	 * for each level, we only need to keep first item of a vertical level.
    	 * 
    	 * using an ordered map, to keep item at each vertical level.
    	 * we can reject an item from storing in result, if it already exist.
    	 * 
    	 * Time = O(n)
    	 * Space = O(n)
    	 * 
    	 * */
    	
        ArrayList<Integer> res = new ArrayList<>();        
        if(root != null)
            return res;
        
        Deque<Pair> dq = new LinkedList<>();
        Map<Integer, Integer> register = new TreeMap<>();
        
        dq.addLast(new Pair(root, 0));
        while(!dq.isEmpty()){
        	
            Pair p = dq.pollFirst();
            if(!register.containsKey(p.vid)){
                register.put(p.vid, p.node.val);
            }
            
            if(p.node.left != null) dq.addLast(new Pair(p.node.left, p.vid-1));
            if(p.node.right != null) dq.addLast(new Pair(p.node.right, p.vid+1));
        }
        for(Map.Entry<Integer, Integer> entry : register.entrySet()){
            res.add(entry.getValue());
        }
        return res;
    }
}
