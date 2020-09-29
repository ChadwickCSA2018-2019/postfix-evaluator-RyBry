package evaluator.arith;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import language.Operand;
import language.Operator;
import parser.arith.ArithPostFixParser;
import stack.StackInterface;
import stack.LinkedStack;

public class ArithInFixOperator implements PostFixEvaluator<Integer>{

	private final StackInterface<Operand<Integer>> operandStack;
	private final StackInterface<Operator<Integer>> operatorStack;

	/**
	 * Constructs an {@link ArithPostFixEvaluator}.
	 */
	public ArithInFixOperator() {
		this.operandStack = new LinkedStack<Operand<Integer>>(); 
		this.operatorStack = new LinkedStack<Operator<Integer>>(); 
	}

	/**
	 * Evaluates a postfix expression.
	 * @return the result 
	 * NOTE: This does not work with the negate expression due to it's Postfix intrinsic nature. Multiplying by -1 will work though!
	 */
	@Override
	public Integer evaluate(String expr) {
		ArithPostFixParser parser = new ArithPostFixParser(expr);
		while (parser.hasNext()) {
			Operand<Integer> op0;
			Operand<Integer> op1;
			Operator<Integer> operation = null;
			Operand<Integer> result = null;
			switch (parser.nextType()) { 
			case OPERAND:
				Operand<Integer> scannedOperand = parser.nextOperand();
				operandStack.push(scannedOperand);
				//If there is nothing left in the expression, evaluate what is in the stacks
				if(!parser.hasNext()) 
				{
					//If there is only one element left in the stack, return it before end of loop to prevent popping from an empty stack.
					if(operandStack.size() == 1) 
					{
						return operandStack.pop().getValue();
					}
					//If there is more than 1 element left with no other operations, the expression did not evaluate properly
					else if(operandStack.size() > 1 && !parser.hasNext() && operatorStack.isEmpty()) 
					{
						throw new IllegalPostFixExpressionException("The InFix expression may have been entered incorrectly");
					}
					else 
					{
						operation = operatorStack.pop();
						op1 = operandStack.pop();
						op0 = operandStack.pop();
						operation.setOperand(0, op0);
						operation.setOperand(1, op1);
						result = operation.performOperation();
						operandStack.push(result);
					}
				}
				break;
			case OPERATOR:
				Operator<Integer> scannedOperator = parser.nextOperator();
				//If the current operation takes higher priority than the previous, put it on the stack
				if(operatorStack.isEmpty() || scannedOperator.getImportance() > operatorStack.top().getImportance()) 
				{
					operatorStack.push(scannedOperator);
				}
				else 
				{
					operation = operatorStack.pop();
					op1 = operandStack.pop();
					op0 = operandStack.pop();
					operation.setOperand(0, op0);
					operation.setOperand(1, op1);
					result = operation.performOperation();
					operandStack.push(result);
					//Then push the other operation onto the stack so it isn't lost in computer space :D
					operatorStack.push(scannedOperator);
				}
				break;
			default:
				//If we get here, something went terribly wrong
				throw new IllegalPostFixExpressionException("An error has occurred");
			}
		}
		return operandStack.pop().getValue();
	}
}
