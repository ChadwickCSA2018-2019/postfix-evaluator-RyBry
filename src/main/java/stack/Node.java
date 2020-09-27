package stack;

public class Node<T> {

	private Node<T> next;
	private T element;
	
	//Empty constructor just to initialize Node
	public Node()
	{
		next = null;
		element = null;
	}
	
	//Attaches the element to its reference Node upon object creation
	public Node(T elem) 
	{
		next = null;
		element = elem;
	}
	
	//Links the next node in the list to current node
	public void setNext(Node<T> nextNode) 
	{
		next = nextNode;
	}
	
	//Attaches the element to its reference Node
	public void setElement(T elem) 
	{
		element = elem;
	}
	
	//Returns the element referenced by this node
	public T getElement() 
	{
		return element;
	}
	
	//returns the next node in the Linked List
	public Node<T> getNext()
	{
		return next;
	}
}