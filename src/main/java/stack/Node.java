package stack;

/**
 * Nodes are the organizational structure that stacks will be based on.
 * Each node holds a reference to an element from an expression
 * @author ryanb
 *
 * @param <T>
 * Node will either hold reference to Operands or Operators.
 */
public class Node<T> {

  /**
   * Each node contains a reference for the next node in the list.
   */
  private Node<T> next;
  /**
   * Each node keeps track of the elememnt that is linked to it.
   */
  private T element;

  // Empty constructor to initialize Node
  public Node() {
    next = null;
    element = null;
  }

  // Attaches element to reference Node upon construction
  public Node(T elem) {
    next = null;
    element = elem;
  }

  // Links the next node in the list to current node
  public void setNext(Node<T> nextNode) {
    next = nextNode;
  }

  // Attaches the element to its reference Node
  public void setElement(T elem) {
    element = elem;
  }

  // Returns the element referenced by this node
  public T getElement() {
    return element;
  }

  // returns the next node in the Linked List
  public Node<T> getNext() {
    return next;
  }
}
