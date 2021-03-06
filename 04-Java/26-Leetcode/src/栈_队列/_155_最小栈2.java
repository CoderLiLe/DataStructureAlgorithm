package 栈_队列;

public class _155_最小栈2 {
	private Node head;
	
	public _155_最小栈2() {
		head = new Node(0, Integer.MAX_VALUE, null);
    }
    
    public void push(int x) {
     	head = new Node(x, Math.min(x, head.min), head);
    }
    
    public void pop() {
    	head = head.next;
    }
    
    public int top() {
    	return head.val;
    }
    
    public int getMin() {
    	return head.min;
    }
    
    private static class Node {
    	public int val;
    	public int min;
    	public Node next;
		public Node(int val, int min, Node next) {
			this.val = val;
			this.min = min;
			this.next = next;
		}
    }
}
