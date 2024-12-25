package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
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
			if(!register.containsKey(p.distance)){
				register.put(p.distance, p.node.val);
			}

			if(p.node.left != null) dq.addLast(new Pair(p.node.left, p.distance-1));
			if(p.node.right != null) dq.addLast(new Pair(p.node.right, p.distance+1));
		}
		for(Map.Entry<Integer, Integer> entry : register.entrySet()){
			res.add(entry.getValue());
		}
		return res;
	}

	class Pair{
		public TreeNode node;
		public int distance;

		public Pair(TreeNode node, int vid) {
			this.node = node;
			this.distance = vid;
		}
	}
	

	public ArrayList <Integer> bottomView(TreeNode root)
	{
		/*
		 * Given a binary tree, return an array where elements represent the bottom view of the binary tree from 
		 * left to right.
		 * 
		 * Conclusions from Problem statement :
		 * 1. returned values in order of nodes from left to right (vertical distance).
		 * 2. returned values would have only values that occur latest from top to bottom.
		 * 3. two nodes may belong to same vertical distance, keep only the later one.
		 * 
		 * tree map will automatically keep the vertical order of vertical distances.
		 * 
		 * since we are iterating form top to bottom in terms of horizontal distance, i.e 0, 1, 2....
		 * hence we can simply replace items while traversing top to bottom, to only keep latest (node that will hide
		 * node just above it horizontally).
		 * 
		 * if two nodes belong to same vertical distance, we only need to keep the one that comes later.
		 * hence just replacing value at any vertical distance will automatically follow required conditions.
		 * 
		 * */
		ArrayList<Integer> result = new ArrayList<>();
		if(root == null)
			return result;

		Map<Integer, Integer> register = new TreeMap<>();
		Deque<Pair> dq = new LinkedList<>();

		dq.add(new Pair(root, 0));
		while(!dq.isEmpty()){
			Pair p = dq.pollFirst();
			register.put(p.distance, p.node.val);
			if(null != p.node.left) dq.addLast(new Pair(p.node.left, p.distance-1));
			if(null != p.node.right) dq.addLast(new Pair(p.node.right, p.distance+1));
		}
		for(int value : register.values()){
			result.add(value);
		}

		return result;
	}
	
	public List<Integer> rightSideViewIterative(TreeNode root) {
		/*
		 * Given the root of a binary tree, imagine yourself standing on the right side of it, 
		 * return the values of the nodes you can see ordered from top to bottom.
		 * 
		 * 1. Using Level order traversal.
		 * 	
		 * for a complete binary tree, at a particular time queue will store almost half of elements.
		 * so space = O(n)
		 * 		time= O(n)
		 * 
		 * */
        List<Integer> res = new ArrayList<>();
        if(root == null)
            return res;
        Deque<Pair> dq = new LinkedList<>();
        Map<Integer, Integer> register = new TreeMap();
        dq.add(new Pair(root, 0));
        while(!dq.isEmpty()){
            Pair p = dq.pollFirst();
            register.put(p.distance, p.node.val);
            if(null != p.node.left) dq.addLast(new Pair(p.node.left, p.distance+1));
            if(null != p.node.right) dq.addLast(new Pair(p.node.right, p.distance+1));
        }
        for(Integer value : register.values()){
            res.add(value);
        }
        return res;
    }

	public List<Integer> rightSideViewRecursive(TreeNode root) {
		/*
		 * using reverse Pre order traversal (Root, Right, left) 
		 * this can be solved with less space complexity and better readability.
		 * 
		 * result will have exactly h elements, where h is height of tree.
		 * hence idea is to put an element in result only first time, this can be achieved by 
		 * size of result, and putting right most element first. if height h has been visited, then size
		 * of result will be h. Since we want right view of tree, first visit right node of current node
		 * recursively.
		 * 
		 * */
        List<Integer> res = new ArrayList<>();
        if(root == null)
            return res;
        res.add(root.val);
        getRightView(root, 0, res);
        return res;
    }

    private void getRightView(TreeNode node, int height, List<Integer> res){
    	/*
    	 * 
    	 * */
        if(node == null)
            return;
        if(height+1 > res.size())
            res.add(node.val);
        getRightView(node.right, height+1, res);
        getRightView(node.left, height+1, res);
    }
    
    public boolean isSymmetric(TreeNode root) {
    	/*
    	 * Idea : a node's left child will be equal to node's right child.
    	 * i.e. RootLeftRight == RootRightLeft.
    	 * 
    	 * 
    	 * */
        if(root == null)
            return true;
        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode leftRoot, TreeNode rightRoot){
        if(leftRoot == null || rightRoot == null)
            return leftRoot == rightRoot;
        if(leftRoot.val != rightRoot.val)
            return false;
        
        return (isSymmetricHelper(leftRoot.left, rightRoot.right) && isSymmetricHelper(leftRoot.right, rightRoot.left));

    }
    
    class Node
    {
        int data;
        Node left;
        Node right;

        Node(int data)
        {
            this.data = data;
            left = null;
            right = null;
        }
    }
    public static ArrayList<ArrayList<Integer>> Paths(Node root) {
        // code here
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root == null)
            return res;
        
        ArrayList<Integer> cache = new ArrayList<>();
        pathsHelper(root, res, cache);
        return res;
    }
    
    private static void pathsHelper(Node root, ArrayList<ArrayList<Integer>> res, ArrayList<Integer> cache){
        if(root == null)
            return;
        
        cache.add(root.data);
        
        if(root.left == null && root.right == null){
            res.add(new ArrayList<Integer>(cache));
        }    
        else{
            pathsHelper(root.left, res, cache);
            pathsHelper(root.right, res, cache);
        }    
        
        cache.remove(cache.size()-1);
    }
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    	/*
    	 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
    	 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two 
    	 * nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be 
    	 * a descendant of itself).”
    	 * 
    	 * Brute force : while traversing tree, idea is to store each node in 2 linear data structures, array/stack
    	 * until p and q is found. then traversing on linear structure to find last common node which will be
    	 * their common ancestor.
    	 * 
    	 * Optimal : while traversing the tree, idea is to return the node if it is p or q. if from left and right
    	 * null is not recieved that means from left and right we have found the targets. 
    	 * 
    	 * */
        if(root == null)
            return null;
        if(root == p)
            return p;
        if(root == q)
            return q;
        TreeNode leftNode = lowestCommonAncestor(root.left, p, q);
        TreeNode rightNode = lowestCommonAncestor(root.right, p, q);
        if(leftNode != null && rightNode != null)
            return root;
        if(leftNode != null)
            return leftNode;
        
        return rightNode;
    }
    
    public int widthOfBinaryTree(TreeNode root) {
    	/*
    	 * LeetCode - 662. Maximum Width of Binary Tree
    	 * 
    	 * bruteForce : for each level, assign an index to node. root being at index 0. every left child must be
    	 * at index = rootIndex*2+1 and child at right will be rootIndex*2+2. in this way we have indexed the position
    	 * of each node as if it is a flat array. now when this is completed, the left most index of last level of 
    	 * tree and right most index of last level of tree will give us a range which is our answer. 
    	 * 
    	 * problem with above solution is, in case of skewed tree, when we calculate index of a node then after a while
    	 * the index value will overflow size of int. Since we are only interested in a range of nodes at a level,
    	 * optimal idea is to set the index of left and right such that it starts from 0.
    	 * 
    	 * Optimal : perform a level order traversal to visit nodes of each level, starting from root we need to
    	 * keep node and its index in the queue. width will 1 for root, and index will be 0. 
    	 * now while putting each node in the queue using index of first(left is minimum) put left and right in the que
    	 * 
    	 *  time = O(n)
    	 *  space = O(n)
    	 * 
    	 * */
        Deque<Pair> dq = new LinkedList<>();
        int width = 1;
        Pair p = new Pair(root, 0);
        dq.add(p);
        while(!dq.isEmpty()){
            int size = dq.size();
            int rightSize = 0;
            int leftSize = 0;
            for(int nodes=0; nodes<size; nodes++){
                Pair currP = dq.pollFirst();
                if(nodes == 0)
                    rightSize = currP.distance;
                leftSize = currP.distance;
                if(currP.node.left != null) dq.addLast(new Pair(currP.node.left, (((currP.distance-rightSize)*2)+1)));
                if(currP.node.right != null) dq.addLast(new Pair(currP.node.right, (((currP.distance-rightSize)*2)+2)));
                int currWidth = leftSize-rightSize+1;
                width = width>currWidth?width:currWidth;
            }
        }

        return width;
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
    	/*
    	 * 1. prepare node value to its parent map. this map will be used to find 
    	 * 
    	 * */
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> dq = new LinkedList<>();
        Map<Integer, TreeNode> parentRegister = new HashMap<>();
        if(root == null)
        	return res;
        dq.add(root);
        while(!dq.isEmpty()) {
        	TreeNode node = dq.pollFirst();
        	if(node.left != null) {
        		parentRegister.put(node.left.val, node);
        		dq.addLast(node.left);
        	}
        	if(node.right != null) {
        		parentRegister.put(node.right.val, node);
        		dq.addLast(node.right);
        	}
        }
        
        //from target node with currDistance = 0, move outwards and put all neighbors in queue,
        //to further move in tree. when currDistance reach k, stop traversing outward and just return items in queue.
        
        Set<TreeNode> visited = new HashSet<>();
        dq.clear();
        int currDistance = 0;
        dq.add(target);
        visited.add(target);
        while(!dq.isEmpty()) {
        	//1. for all the elements in current size, push all unvisited neighbors in queu also mark as visited
        	int size = dq.size();
        	if(currDistance == k) break;
        	currDistance++;
        	for(int i=0; i<size; i++) {
        		TreeNode node = dq.pollFirst();
        		if(null!=node.left && !visited.contains(node.left)) {
        			dq.add(node.left);
        			visited.add(node.left);
        		}
        		if(null!= node.right && !visited.contains(node.right)) {
        			dq.add(node.right);
        			visited.add(node.right);
        		}
        		if(null!=parentRegister.get(node.val) && !visited.contains(parentRegister.get(node.val))) {
        			dq.add(parentRegister.get(node.val));
        			visited.add(parentRegister.get(node.val));
        		}
        	}
        }
        while(!dq.isEmpty()) {
        	res.add(dq.pollFirst().val);
        }
        return res;
    }
    
    public int countNodes(TreeNode root) {
    	/*
    	 * Given the root of a complete binary tree, return the number of the nodes in the tree.
    	 * Design an algorithm that runs in less than O(n) time complexity.
    	 * 
    	 * brute force : to traverse the tree in O(n) and count the number of nodes.
    	 * 
    	 * Optimal : since the tree is complete binary tree, hence if height of leftmost node of tree and height
    	 * at which right most node exists is same then we can directly use formula to find total nodes in tree.
    	 * 
    	 * if heights are different, that means at last level nodes exists from left to right.
    	 * idea is to find number of nodes in left tree and right tree recursively. 
    	 * 
    	 * */
        if(root == null)
            return 0;
        int lh = heightLeft(root.left);
        int rh = heightRight(root.right);
        if(lh == rh)
            return (int)Math.pow(2, rh+1)-1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int heightLeft(TreeNode root){
        TreeNode cur = root;
        int h = 0;
        while(cur!=null){
            h++;
            cur = cur.left;
        }
        return h;
    }
    private int heightRight(TreeNode root){
        TreeNode cur = root;
        int h = 0;
        while(cur!=null){
            h++;
            cur = cur.right;
        }
        return h;
    }
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
    	/*
    	 * Construct Binary Tree from Preorder and Inorder Traversal.
    	 * 
    	 * intuition :
    	 * 
    	 * 			   0, 1, 2, 3, 4, 5, 6
    	 * 			   __left___ __right__
    	 * inOrder : [          X         ]
    	 * 
    	 * 			   0, 1, 2, 3, 4, 5, 6
    	 * PreOrder : [X                  ]
    	 * 				 __left___ __right__
    	 * 
    	 * first element X of preOrder is always the root, find X in inOrder. all the elements to the left
    	 * of X belong to right sub tree of current root and all left of X belong to left sub tree.
    	 * 
    	 * Everything to left of X in inOrder can become new inOrder array for left sub tree, and to right of X in 
    	 * inOrder can become new inOrder for right sub tree.
    	 * 
    	 * number of elements to the left of X in inOrder is the number of elements in preOrder after X will 
    	 * belong to left subtree. hence from index of (X +1 to leftCount from inOrder) will become new preOrder
    	 * for left subtree, and everything after that will be new preOrder for right subtree.
    	 * 
    	 * since it is required to find X in inOrder, its better to hash all values of inOrder for constant time
    	 * retrieval.
    	 * 
    	 * */
        Map<Integer, Integer> inRegister = new HashMap<>();
        
        for(int i=0; i<inorder.length; i++){
            inRegister.put(inorder[i], i);
        }
        
        return buildTree(preorder, inorder, 0, preorder.length-1, 0, inorder.length-1, inRegister);
    }

    TreeNode buildTree(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd, Map<Integer, Integer> inRegister){
        if(inStart > inEnd || preStart > preEnd)
            return null;
        
        TreeNode node = new TreeNode(preorder[preStart]);
        //index of root in inOrder array, fetched using map.
        int inRoot = inRegister.get(preorder[preStart]);
        //total number of items to the left of root.
        int count = inRoot - inStart;

        node.left = buildTree(preorder, inorder, preStart+1, preStart+count, inStart, inRoot-1, inRegister);
        node.right = buildTree(preorder, inorder, preStart+count+1, preEnd, inRoot+1, inEnd, inRegister);
        return node;
    }
    
    public TreeNode buildTreeFromPostOrder(int[] inorder, int[] postorder) 
    {    	
        Map<Integer, Integer> inRegister = new HashMap<>();
        for(int i=0; i<inorder.length; i++){
            inRegister.put(inorder[i], i);
        }
        
        return buildTreeFromPostOrder(postorder, inorder, 0, postorder.length-1, 0, inorder.length-1, inRegister);
    }
    TreeNode buildTreeFromPostOrder(int[] postorder, int[] inorder, int poStart, int poEnd, int inStart, int inEnd, Map<Integer, Integer> inRegister){
        if(poStart > poEnd || inStart > inEnd) return null;

        TreeNode node = new TreeNode(postorder[poEnd]);
        int inRoot = inRegister.get(postorder[poEnd]);
        int count = inRoot - inStart;

        node.left = buildTreeFromPostOrder(postorder, inorder, poStart, poStart+count-1, inStart, inRoot-1, inRegister);
        node.right = buildTreeFromPostOrder(postorder, inorder, poStart+count, poEnd-1, inRoot+1, inEnd, inRegister);
        return node;
    }
    
    List<Integer> inOrderTraverse(TreeNode root){
    	/*
    	 * Morris Traversal : in conventional iterative or recursive approach to traverse tree as in order, 
    	 * there is O(n) additional space required.
    	 * 
    	 * Idea is to temporarily link last node (right most of left sub tree as per DFS) of a tree to the root.
    	 * In this way, every time, there is no left to go then after processing root, we move to right (probably last)
    	 * and this right will lead us to root. 
    	 * 
    	 * for each node, if left is null : do nothing (no temporary linking required) process root, 
    	 * move right if exists. the right again becomes root.
    	 * 
    	 * if left is not null : then stay on current node (root) and find rightmost node of left subtree and link 
    	 * its left/right to current node. There will be two scenarios in this : 
    	 * 		1. during above activity, a loop is detected (we get back to root) that means we had already processed left
    	 * subtree and linking already exist. so delete that link and move to right.
    	 * 		2. no loop is detected, so link last node to current node.
    	 * 
    	 * 
    	 * */
    	
    	List<Integer> result = new ArrayList<>();
    	TreeNode curr = root;

        while(curr != null){
            if(curr.left == null){
            	result.add(curr.val);
                curr = curr.right;
            }
            else{
                //1. find last node.
                TreeNode last = curr.left;
                while(last.right != null && last.right != curr)
                    last = last.right;

                if(last.right == curr){
                	//Since looking for last node has lead us to current node, that means we have already processed
                	//left sub tree. so we are back to root of LRoR, hence add root to result and move right.
                    last.right = null;
                    result.add(curr.val);
                    curr = curr.right;
                }
                else{
                    last.right = curr;
                    curr = curr.left;
                }
            }
        }
    	return result;
    }
    
    public TreeNode deleteNode(TreeNode root, int key) {
    	/*
    	 * Case 1: root is null
    	 * Case 2: target node is leaf node. simply make target node as null.
    	 * Case 3: left of target node is null. return right of target node.
    	 * Case 4: right of target is null. return left of target node.
    	 * Case 5: neither left nor right of target node is null. 
    	 * 			solution1: pick smallest from right sub tree to put at target nodes val and delete smallest node.
    	 * 			solution2: pick largetst from left sub tree to put at target nodes' val and delete largest node.
    	 * 
    	 * */
        if(root == null) return root;

        if(key < root.val) root.left = deleteNode(root.left, key);
        if(key > root.val) root.right = deleteNode(root.right, key);
        if(key == root.val){
            if(root.left == null && root.right == null){
                root = null;
            }
            else if(root.left == null)  root = root.right;
            else if(root.right == null) root = root.left;
            else if(root.left != null && root.right != null){
                //1.find smallest in right subtree.
                TreeNode smallest = smallest(root.right);
                root.val = smallest.val;
                root.right = deleteNode(root.right, smallest.val);
            }
        }
        return root;
    }

    private TreeNode smallest(TreeNode root){
        TreeNode node = root;
        while(node.left != null) node = node.left;
        return node;
    }
    
    private int current = 0;
    private TreeNode res = null;
    
    public int kthSmallest(TreeNode root, int k) {
    	/*
    	 * inorder traversal of bst is sorted array.
    	 * by using global holder of a result node and counter to current node,
    	 * return result node value when k nodes are visited.
    	 * 
    	 * */
        if(root == null)
            return -1;
        if(res == null)
            res = root;
        kthSmallest(root.left, k);
        current++;
        if(current == k)
            res = root;
        kthSmallest(root.right, k);
        return res.val;
    }
    
    public boolean isValidBST(TreeNode root) {
    	/*
    	 * mistakes : 1. if used range int max and int min, then every tree of having only one node
    	 * of 2147483647 or -2147483647 will always say tree is invalid.
    	 * 
    	 * */
        if(root == null)
            return true;

        return isValidBSTHelp(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean isValidBSTHelp(TreeNode root, long from, long to){
        if(root == null)
            return true;
        if(root.val <= from || root.val >= to) return false;

        return isValidBSTHelp(root.left, from, root.val) && isValidBSTHelp(root.right, root.val, to);
    }
}
