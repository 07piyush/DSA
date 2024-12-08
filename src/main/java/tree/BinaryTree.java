package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
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


	public List<List<Integer>> verticalTraversal(TreeNode root) {

		/*
		 * Solution : traversing in levels, keep note of a nodes vertical distance and horizontal distance
		 * from root. when going down horizontal distance will increase, going left of a node vertical distance
		 * will decrease, and going right of a node vertical distance will increase. 
		 * 
		 * keeping this as core idea, we can keep each node in a queue for each level. (level order traversal)
		 * 
		 * Since we need to maintain the order left to right in resultant array, treemap will help keep track
		 * of each node in a vertical level, while also maintaining the order (left to right) (-2 to 0 to 2).
		 * 
		 * we also want for each vertical level, the nodes must be in order of their horizontal level,
		 * so again a treemap will keep horizontal distance from root, ie 0 for root, 1 for next level etc. 
		 * hence in case of two nodes of different horizontal level and same vertical level the one encountered first
		 * will be available first due to TreeMap. : Below example 1 should come first and 5 later. as 1 is at hd=0
		 * 						1		
		 * 					   / \
		 *					  2   3
		 *				     / \
		 *				    4   5
		 *
		 * PriorityQueue is simply keeping values of individual nodes sorted.
		 * 
		 * Performance analysis : if all the nodes
		 *  
		 * */

		List<List<Integer>> res = new ArrayList<>();
		if(root == null)
			return res;
		Deque<Triplet> dq = new LinkedList<>();
		Map<Integer, Map<Integer, PriorityQueue<Integer>>> register = new TreeMap<>();

		Triplet rootTriplet = new Triplet(root, 0, 0);
		dq.add(rootTriplet);

		while(!dq.isEmpty()) {
			Triplet triplet = dq.pollFirst();
			TreeNode node = triplet.node;
			int vd = triplet.vd;
			int hd = triplet.hd;

			if(!register.containsKey(vd))
				register.put(vd, new TreeMap<>());

			if(!register.get(vd).containsKey(hd))
				register.get(vd).put(hd, new PriorityQueue<>());

			register.get(vd).get(hd).add(node.val);

			if(null != node.left){
				Triplet leftTriplet = new Triplet(node.left, vd-1, hd+1);
				dq.add(leftTriplet);
			} 
			if(null != node.right){
				Triplet rightTriplet = new Triplet(node.right, vd+1, hd+1);
				dq.add(rightTriplet);
			} 
		}

		for(Map.Entry<Integer, Map<Integer, PriorityQueue<Integer>>> entry : register.entrySet()) {
			List<Integer> internalRes = new ArrayList<>();

			Map<Integer, PriorityQueue<Integer>> value = entry.getValue();
			for(Map.Entry<Integer, PriorityQueue<Integer>> internalEntry : value.entrySet()) {
				while(!internalEntry.getValue().isEmpty()) {
					internalRes.add(internalEntry.getValue().poll());
				}
			}
			res.add(internalRes);
		}

		return res;
	}

	class Triplet{
		public TreeNode node;
		public Integer vd;
		public Integer hd;

		Triplet(TreeNode n, Integer vd, Integer hd){
			node = n;
			this.vd = vd;
			this.hd = hd;
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

	class Pair{
		public TreeNode node;
		public int vid;

		public Pair(TreeNode node, int vid) {
			this.node = node;
			this.vid = vid;
		}
	}
}
