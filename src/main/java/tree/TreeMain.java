package tree;

public class TreeMain {

	public static void main(String[] args) {
		TreeNode root = new TreeNode(1);
		root.left = new TreeNode(0);
		root.left.left = new TreeNode(-2);
		root.right = new TreeNode(4);
		root.right.left = new TreeNode(3);
		
		BinaryTree tree = new BinaryTree();
		tree.findTarget(root, 7);
	}

}
