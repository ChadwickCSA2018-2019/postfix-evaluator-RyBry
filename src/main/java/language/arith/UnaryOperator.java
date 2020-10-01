package language.arith;

import language.Operand;
import language.Operator;

/**
 * An operator type that performs an operation on a single operand.
 * @author ryanb
 *
 * @param <T> the type of the {@link Operand} being evaluated
 */
public abstract class UnaryOperator<T> implements Operator<T> {

  /**
   * Holds the single operand to be evaluated.
   */
  private Operand<T> op0;

  @Override
  public int getNumberOfArguments() {
    return 1;
  }

  @Override
  public void setOperand(int i, Operand<T> operand) {
    if (operand == null) {
      throw new NullPointerException("Could not set null operand.");
    }
    if (i != 0) {
      throw new IllegalArgumentException("Unary operator only accepts operand 0 " + "but recieved " + i + ".");
    }
    if (i == 0) {
      if (op0 != null) {
        throw new IllegalStateException("Position " + i + " has been previously set.");
      }
      op0 = operand;
    }
  }

  /**
   * Returns the first operand.
   * @return the first operand
   */
  public Operand<T> getOp0() {
    return op0;
  }

}
