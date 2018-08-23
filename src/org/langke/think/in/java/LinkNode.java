package org.langke.think.in.java;

public class LinkNode {
	/**
	 * 测试函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		NumberOffLink link = new NumberOffLink();
		Node head = link.creatLink(12);
		Node tempH = link.NumberOffMethod(head);
	}

}

class Node {
	public Node() {
	}

	public int data; // 结点数据

	public Node next; // 指向下一结点的指针

	Node(int data) {
		this.data = data;
	}

	/**
	 * 获取节点数据
	 * 
	 * @param null
	 * @return int
	 */
	public int getNodeData() {
		return this.data;
	}

}

/**
 * 报数链表类
 * 
 * @author zrp
 * 
 */
class NumberOffLink {
	public Node headNode; // 头结点

	private int size; // 链表长度

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 构造函数,无参构造函数
	 * 
	 * @param n,node
	 */
	public NumberOffLink() {
	}

	/**
	 * 有参构造函数，创建链表
	 * 
	 * @param 人数account
	 */

	public NumberOffLink(int nodeCount) {
		if (nodeCount >= 1) {
			headNode = new Node(1); // 新建结点
			Node p = headNode;
			headNode.next = null;
			for (int i = 2; i < nodeCount; i++) {
				Node node = new Node(i);
				p.next = node;
				node = p;
			}

			this.size = nodeCount;
		} else {
			System.out.println("输入的人数有误!");
		}
	}

	/**
	 * 创建链表函数
	 */
	public Node creatLink(int nodeCount) {
		Node p;
		if (nodeCount >= 1) {
			Node head = new Node(1); // 新建结点
			p = head;
			for (int i = 2; i <= nodeCount; i++) {
				Node node = new Node(i);
				p.next = node;
				p = node;
			}
			this.size = nodeCount;
			p.next = head; // 把链表变成循环链表
			return head;
		} else {
			System.out.println("输入的人数有误!");
			return null;
		}

	}

	/**
	 * @param numberOffObj
	 * @return 返回报数后的表头结点
	 */
	public Node NumberOffMethod(Node head) {
		int currSize = this.getSize();
		Node currNode = head;
		Node tempNode = null;
		Node tempMyHead = null;
		Node delPreNode = null;
		int tempSize = currSize;
		for (int i = 1; i < this.getSize(); i++) { // 趟数,每一次随着size的变化而不同
			while (true) { // 找出最后一个能被3整除的数
				if (tempSize % 3 == 0) {
					break;
				}
				tempSize--;
			}
			for (int j = 1; j <= tempSize; j++) {
				if (j % 3 == 0) {
					delPreNode.next = currNode.next;
					System.out.println(currNode.data);
					tempNode = currNode;
					currNode = delPreNode;
					this.size--;
					// tempSize = j;
				}
				delPreNode = currNode;
				currNode = currNode.next;
			}// 内循环
		}// 外循环
		while (this.size != 0) {
			System.out.println(currNode.data); // 打印剩余的数据
			delPreNode = currNode;
			delPreNode.next = currNode.next;
			currNode = delPreNode;
			currNode = currNode.next;
			this.size--;
		}
		return tempNode;

	}

	/**
	 * 判断链表是否为空
	 */
	private boolean isLinkEmpty(Node head) {
		if (head == null) {
			return true;
		} else {
			return false;
		}
	}

}