package stack;

/**
 * A {@link LinkedStack} is a stack that is implemented using a Linked List structure
 * to allow for unbounded size.
 *
 * @param <T> the elements stored in the stack
 */
public class LinkedStack<T> implements StackInterface<T> {

	private Node<T> topNode;
	private int count = 0;
	
  /**
   * {@inheritDoc}.
   */
  @Override
  public T pop() throws StackUnderflowException {
    if(count == 0) 
    {
    	throw new StackUnderflowException("No elements in stack");
    }
    T result = topNode.getElement();
    topNode = topNode.getNext();
    count --;
    return result;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public T top() throws StackUnderflowException {
	  if(count == 0) 
	  {
		  throw new StackUnderflowException("No elements in stack");
	  }
	  return topNode.getElement();
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public boolean isEmpty() {
    if(count != 0) 
    {
    	return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}.
   */
  @Override
  public int size() {
    return count;
  }

  /**
   * {@inheritDoc}.
   * This works maybe because temp is only a temporary pointer, but the memory address is permanent?
   * so therefore as long as we keep one pointer we can refer to all of the memory locations?
   * 
   * If not, why does temp not instantly get deleted?
   */
  @Override
  public void push(T elem) {
	  // TODO Auto-generated method stub
	  Node<T> temp = new Node<T>(elem);
	  temp.setNext(topNode);
	  topNode = temp;
	  count ++;
  }

}
