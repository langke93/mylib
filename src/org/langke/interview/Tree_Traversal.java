package org.langke.interview;
/**
 * 二叉树的遍历
 * 建立二叉树
 * 前序遍历
 * 中序遍历
 * 后序遍历
 * 二叉树的深度
 * http://wangdbjsj091.blog.163.com/blog/static/1363971212011623108574/
 */
public class Tree_Traversal {

	  private int data;// 数据节点
	  private Tree_Traversal left;// 左子树
	  private Tree_Traversal right;// 右子树

	  public Tree_Traversal(int data) {
	   this.data = data;
	   this.left = null;
	   this.right = null;
	  }

	  /**
	   * 创建二叉树，返回根结点
	   * 
	   * @param input
	   * @return
	   */
	  public static Tree_Traversal createTree(int[] input) {
	   Tree_Traversal root = null;
	   Tree_Traversal temp = null;
	   for (int i = 0; i < input.length; i++) {
	    // 创建根节点
	    if (root == null) {
	     root = temp = new Tree_Traversal(input[i]);
	    } else {
	     // 回到根结点
	     temp = root;
	     // 添加节点
	     while (temp.data != input[i]) {
	      if (input[i] <= temp.data) {
	       if (temp.left != null) {
	        temp = temp.left;
	       } else {
	        temp.left = new Tree_Traversal(input[i]);
	       }
	      } else {
	       if (temp.right != null) {
	        temp = temp.right;
	       } else {
	        temp.right = new Tree_Traversal(input[i]);
	       }
	      }
	     }
	    }
	   }
	   return root;
	  }

	  /**
	   * 前序遍历
	   * 
	   * @param tree
	   */
	  public static void preOrder(Tree_Traversal tree) {
	   if (tree != null) {
	    System.out.print(tree.data + " ");
	    preOrder(tree.left);
	    preOrder(tree.right);
	   }
	  }

	  /**
	   * 中序遍历
	   * 
	   * @param tree
	   */
	  public static void midOrder(Tree_Traversal tree) {
	   if (tree != null) {
	    midOrder(tree.left);
	    System.out.print(tree.data + " ");
	    midOrder(tree.right);
	   }
	  }

	  /**
	   * 后序遍历
	   * 
	   * @param tree
	   */
	  public static void posOrder(Tree_Traversal tree) {
	   if (tree != null) {
	    posOrder(tree.left);
	    posOrder(tree.right);
	    System.out.print(tree.data + " ");
	   }
	  }
	  
	  /**
	   * 求二叉树的深度
	   * 
	   * @param tree
	   */
	  public static int length(Tree_Traversal tree){
	   int depth1;
	   int depth2;
	   if(tree == null) return 0;
	   //左子树的深度
	   depth1 = length(tree.left);
	   //右子树的深度
	   depth2 = length(tree.right);
	   if(depth1>depth2)
	    return depth1+1;
	   else
	    return depth2+1;
	  }


	  /**
	   * @param args
	   */
	  public static void main(String[] args) {
	   int[] input = { 4, 2, 6, 1, 3, 5, 7,8,10 };
	   //int[] input = { 4, 2, 5, 1, 3};
	   Tree_Traversal tree = createTree(input);
	   System.out.print("前序遍历：");
	   preOrder(tree);
	   System.out.print("\n二叉树深度为："+length(tree));
	   
	   System.out.print("\n中序遍历：");
	   midOrder(tree);
	   System.out.print("\n后序遍历：");
	   posOrder(tree);
	  }
	 }
