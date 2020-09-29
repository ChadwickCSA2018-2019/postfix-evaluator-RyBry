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
		//there should always be an operator precedence number returned
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
			Operand<Integer> op0;
			Operand<Integer> op1;
			Operator<Integer> operation = null;
			Operand<Integer> result = null;
			switch (parser.nextType()) { 
			case OPERAND:
				Operand<Integer> scannedOperand = parser.nextOperand();
				System.out.println("Pushing to stack operand: " + scannedOperand.getValue());
				operandStack.push(scannedOperand);
				//If there is nothing left in the expression, evaluate what is in the stacks
				if(!parser.hasNext()) 
				{
					System.out.println("No more elements, evaluating what is left");
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
					operation = operatorStack.pop();
					op1 = operandStack.pop();
					op0 = operandStack.pop();
					operation.setOperand(0, op0);
					operation.setOperand(1, op1);
					result = operation.performOperation();
					System.out.println("Pushing to stack the result: " + result.getValue());
					operandStack.push(result);
				}
				break;
			case OPERATOR:
				Operator<Integer> scannedOperator = parser.nextOperator();
				System.out.println("Found operator");
				//If the current operation takes higher priority than the previous, put it on the stack
				System.out.println("Checking importance");
				if(operatorStack.isEmpty() || scannedOperator.getImportance() > operatorStack.top().getImportance()) 
				{
					operatorStack.push(scannedOperator);
					System.out.println("Took higher importance; Pushing to Stack --- Breaking Loop");
				}
				//TODO Run this with a (1 + 1)
				else 
				{
					System.out.println("Took lower importance; Popping from Stack and evaluating");
					operation = operatorStack.pop();
					op1 = operandStack.pop();
					op0 = operandStack.pop();
					operation.setOperand(0, op0);
					operation.setOperand(1, op1);
					result = operation.performOperation();
					System.out.println("Pushing to stack the result: " + result.getValue());
					operandStack.push(result);
					//Then push the other operation onto the stack so it isn't lost in computer space :D
					operatorStack.push(scannedOperator);
				}
				break;
			default:
				throw new IllegalPostFixExpressionException("An error has occurred");
				//If we get here, something went terribly wrong
			}
		}
		System.out.println("****************************************");
		return operandStack.pop().getValue();
	}


}
