package internal.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IAssignment;
import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IDecimalLiteral;
import parser.IParse.IExpression;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.IIntegerLiteral;
import parser.IParse.IParseNode;
import parser.IParse.IParseNodeVisitor;
import parser.IParse.IParser;
import parser.IParse.IReturn;
import parser.IParse.ISimpleStatement;
import parser.IParse.ITerm;
import parser.IParse.IVariable;
import parser.IParse.IWhile;
import parser.IParseFactory;
import tokenizer.IToken;

public class ParseFactory implements IParseFactory {

@Override
public IBooleanExpression newBooleanExpression(final IExpression leftOperand,
      final IToken operator, final IExpression rightOperand) {

    return new BooleanExpression(leftOperand, operator, rightOperand);
}

private static abstract class AbstractNode implements IParseNode {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    private final int id;

    AbstractNode() {

      this.id = COUNTER.incrementAndGet();
    }

    @Override
    public boolean equals(final Object o) {

      if (!(o instanceof AbstractNode)) {
        return false;
      }
      // make sure we use Variable equality if the input object is a variable
      if (o instanceof Variable) {
        return o.equals(this);
      }
      // otherwise we use id-based equality
      return this.id == ((AbstractNode) o).id;
    }

    @Override
    public int hashCode() {

      return this.id;
    }

    @Override
    public int id() {

      return id;
    }

    @Override
    public String toString() {

      return String.format("nid=%d", id);
    }

    @Override
    public String varId() {

      return String.format("T_%d", id);
    }
  }

private abstract static class Expression extends AbstractNode {

    private final IExpression leftOperand;
    private final IToken operator;
    private final IExpression rightOperand;

    Expression(final IExpression leftOperand, final IToken operator, final IExpression rightOperand) {

      this.leftOperand = leftOperand;
      this.operator = operator;
      this.rightOperand = rightOperand;
    }

    public IExpression leftOperand() {

      return this.leftOperand;
    }

    public IToken operator() {

      return this.operator;
    }

    public IExpression rightOperand() {

      return this.rightOperand;
    }
  }


private static class BooleanExpression extends Expression implements IBooleanExpression {

    BooleanExpression(final IExpression leftOperand, final IToken operator,
        final IExpression rightOperand) {

      super(leftOperand, operator, rightOperand);
    }

    @Override
    public void accept(final IParseNodeVisitor visitor) {

      if (visitor.visit(this)) {
        leftOperand().accept(visitor);
        rightOperand().accept(visitor);
      }
    }
  }

// similarly, create a public newX method for each type of parse node X, 
// along with a private static class for X.
 

// rest of the code to be written by you
 
}
