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
	private final String plusClass ="class language.arith.PlusOperator";
	private final String subClass ="class language.arith.SubOperator";
	private final String multClass ="class language.arith.MultOperator";
	private final String divClass ="class language.arith.DivOperator";
	private final String ExponentClass ="class language.arith.ExponentOperator";

	public int importance(Operator<Integer> operator) 
	{
		if(operator == null) 
		{
			return 0;
		}
		else if(operator.getClass().toString() == plusClass || operator.getClass().toString() == subClass) 
		{
			return 1;
		}
		else if(operator.getClass().toString() == multClass || operator.getClass().toString() == divClass) 
		{
			return 2;
		}
		else if(operator.getClass().toString() == ExponentClass) 
		{
			return 3;
		}
		//there should always be an operator number returned
		throw new IllegalStateException("That shouldn't have happened.");
	}

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
				//If the current operation takes higher priority than the previous, put it on the stack
				if(importance(operation) > importance(operatorStack.top())) 
				{
					operatorStack.push(operation);
				}
				//TODO Probably shouldn't pop two elements from the stack, instead should read the next element if there is one,
				//and if there isn't another one, then it should throw an exception
				else if(operation.getNumberOfArguments() == 2) 
				{
					Operand<Integer> op1 = operandStack.pop();
					Operand<Integer> op0 = operandStack.pop();
					operation.setOperand(0, op0);
					operation.setOperand(1, op1);
					result = operation.performOperation();
				}
				else 
				{
					Operand<Integer> op0 = operandStack.pop();
					operation.setOperand(0, op0);
					result = operation.performOperation();
				}
				operandStack.push(result);
				System.out.println(result.getValue());
				break;
			default:
				throw new IllegalPostFixExpressionException("An error has occurred");
				//If we get here, something went terribly wrong
			}
		}
		//If there is more than 1 element left in stack, The expression did not evaluate properly
		if(operandStack.size() > 1) 
		{
			throw new IllegalPostFixExpressionException("The postfix expression may have been entered incorrectly");
		}
		return operandStack.pop().getValue();
	}


}
