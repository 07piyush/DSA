package tree;

public class BinaryTree {

	int maximum = 0;
	
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
    
}
