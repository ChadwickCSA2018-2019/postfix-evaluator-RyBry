package language.arith;

import language.Operand;
import language.BinaryOperator;

/**
 * The {@link DivOperator} is an operator that performs division on two
 * integers.
 * @author jcollard, jddevaug
 *
 */
public class DivOperator extends BinaryOperator<Integer> {

	private Operand<Integer> op0;
	private Operand<Integer> op1;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Operand<Integer> performOperation() {
		Operand<Integer> op0 = this.op0;
		Operand<Integer> op1 = this.op1;
		if(op0 == null || op1 == null) 
		{
			throw new IllegalStateException("Could not perform operation prior to operands being set.");
		}
		Integer result = (op0.getValue() / op1.getValue());
		return new Operand<Integer>(result);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOperand(int i, Operand<Integer> operand) {
		if (operand == null) {
			throw new NullPointerException("Could not set null operand.");
		}
		if (i > 1) {
			throw new IllegalArgumentException("Binary operator only accepts operands 0 and 1 "
					+ "but recieved " + i + ".");
		}
		if (i == 0) {
			if (op0 != null) {
				throw new IllegalStateException("Position " + i + " has been previously set.");
			}
			op0 = operand;
		} else {
			if (op1 != null) {
				throw new IllegalStateException("Position " + i + " has been previously set.");
			}
			if(operand.getValue() == 0) 
			{
				throw new IllegalStateException("Cannot Divide by 0");
			}
			op1 = operand;
		}
	}

}
