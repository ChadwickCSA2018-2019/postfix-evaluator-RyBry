package evaluator.arith;

import static org.junit.Assert.assertEquals;

import evaluator.IllegalPostFixExpressionException;
import evaluator.PostFixEvaluator;
import evaluator.arith.ArithInFixOperator;

import org.junit.Before;
import org.junit.Test;

public class ArthInFixEvaluatorTest {

  private ArithInFixOperator evaluator;

  @Before
  public void setup() {
    evaluator = new ArithInFixOperator();
  }

  @Test(timeout = 5000)
  public void testEvaluateSimple() {
    Integer result = evaluator.evaluate("1");
    assertEquals(new Integer(1), result);
  }

  @Test(timeout = 5000)
  public void testEvaluatePlus() {
    Integer result = evaluator.evaluate("1 + 2");
    assertEquals(new Integer(3), result);

    result = evaluator.evaluate("1 + 2 + 3");
    assertEquals(new Integer(6), result);

    result = evaluator.evaluate("10000 + 1000 + 100 + 10 + 1");
    assertEquals(new Integer(11111), result);
  }

  @Test(timeout = 5000)
  public void testEvaluateSub() {
    Integer result = evaluator.evaluate("1 - 2");
    assertEquals(new Integer(-1), result);

    result = evaluator.evaluate("2 - 3 - 1");
    assertEquals(new Integer(-2), result);

    result = evaluator.evaluate("220 - 320 - 43 - 7");
    assertEquals(new Integer(-150), result);
  }

  @Test(timeout = 5000)
  public void testEvaluateMult() {
    Integer result = evaluator.evaluate("1 * 2");
    assertEquals(new Integer(2), result);

    result = evaluator.evaluate("1 * 2 * 3");
    assertEquals(new Integer(6), result);

    result = evaluator.evaluate("1 * 2 * 3 * -4");
    assertEquals(new Integer(-24), result);
  }

  @Test(timeout = 5000, expected = IllegalPostFixExpressionException.class)
  public void testInvalidExpression() {
    evaluator.evaluate("1 2");
  }

  @Test(timeout = 5000)
  public void testEvaluateExponent() {
    Integer result = evaluator.evaluate("5 ^ 3");
    assertEquals(new Integer(125), result);

    result = evaluator.evaluate("12 ^ 0");
    assertEquals(new Integer(1), result);

    result = evaluator.evaluate("18 ^ 1");
    assertEquals(new Integer(18), result);
  }
}
