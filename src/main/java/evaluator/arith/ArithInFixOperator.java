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
	private final StackInterface<Operand<Integer>> operatorStack;
	private final String plusClass ="class language.arith.PlusOperator";
	private final String subClass ="class language.arith.SubOperator";
	private final String multClass ="class language.arith.MultOperator";
	private final String divClass ="class language.arith.DivOperator";

	public int importance(Operator<Integer> operator) 
	{
		if(operator.getClass().toString() == plusClass || operator.getClass().toString() == subClass) 
		{
			return 1;
		}
		else if(operator.getClass().toString() == multClass || operator.getClass().toString() == divClass) 
		{
			return 2;
		}
		return 5;
	}
	
	/**
	 * Constructs an {@link ArithPostFixEvaluator}.
	 */
	public ArithInFixOperator() {
		this.operandStack = new LinkedStack<Operand<Integer>>(); 
		this.operatorStack = new LinkedStack<Operand<Integer>>(); 
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
				operandStack.push(parser.nextOperand());
				break;
			case OPERATOR:
				Operand<Integer> result = null;
				Operator<Integer> operation = parser.nextOperator();
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
