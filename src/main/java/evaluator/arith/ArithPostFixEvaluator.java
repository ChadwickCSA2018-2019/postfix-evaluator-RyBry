package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.StackInterface;
import stack.LinkedStack;


/**
 * An {@link ArithPostFixEvaluator} is a post fix evaluator over simple arithmetic expressions.
 *
 */
public class ArithPostFixEvaluator implements PostFixEvaluator<Integer> {

	private final StackInterface<Operand<Integer>> stack;

	/**
	 * Constructs an {@link ArithPostFixEvaluator}.
	 */
	public ArithPostFixEvaluator() {
		this.stack = new LinkedStack<Operand<Integer>>(); 
	}

	/**
	 * Evaluates a postfix expression.
	 * @return the result 
	 */
	@Override
	public Integer evaluate(String expr) {
		ArithPostFixParser parser = new ArithPostFixParser(expr);
		while (parser.hasNext()) {
			switch (parser.nextType()) { 
			case OPERAND:
				stack.push(parser.nextOperand());
				break;
			case OPERATOR:
				Operand<Integer> result = null;
				Operator<Integer> operation = parser.nextOperator();
				System.out.println("This is the operation: " + operation.getClass().toString());
				if(operation.getNumberOfArguments() == 2) 
				{
					Operand<Integer> op1 = stack.pop();
					Operand<Integer> op0 = stack.pop();
					operation.setOperand(0, op0);
					operation.setOperand(1, op1);
					result = operation.performOperation();
				}
				else 
				{
					Operand<Integer> op0 = stack.pop();
					operation.setOperand(0, op0);
					result = operation.performOperation();
				}
				stack.push(result);
				System.out.println(result.getValue());
				break;
			default:
				throw new IllegalPostFixExpressionException("An error has occurred");
				//If we get here, something went terribly wrong
			}
		}
		//If there is more than 1 element left in stack, The expression did not evaluate properly
		if(stack.size() > 1) 
		{
			throw new IllegalPostFixExpressionException("The postfix expression may have been entered incorrectly");
		}
		return stack.pop().getValue();
	}

}
