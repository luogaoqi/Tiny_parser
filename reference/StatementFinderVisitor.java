package reference;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IAssignment;
import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.IParseNode;
import parser.IParse.IReturn;
import parser.IParse.ITerm;
import parser.IParse.IWhile;
import parser.ParseNodeVisitor;

public class StatementFinderVisitor extends ParseNodeVisitor {

  private IParseNode statement;
  private IParseNode lastStatement;
  private final IParseNode target;

  public StatementFinderVisitor(final IParseNode target) {

    this.target = target;
  }

  public String getStatement() {

    if (statement == null) {
      return "<NOT FOUND>";
    }
    final StatementPrintVisitor visitor = new StatementPrintVisitor();
    statement.accept(visitor);
    return visitor.getText();
  }

  @Override
  public boolean visit(final IArithmeticExpression node) {

    if (node.leftOperand() == target || node.rightOperand() == target) {
      statement = lastStatement;
    }
    return statement == null;
  }

  @Override
  public boolean visit(final IAssignment node) {

    if (node.variable() == target || node.expression() == target) {
      statement = node;
    }
    lastStatement = node;
    return statement == null;
  }

  @Override
  public boolean visit(final IBooleanExpression node) {

    if (node.leftOperand() == target || node.rightOperand() == target) {
      statement = lastStatement;
    }
    return statement == null;
  }

  @Override
  public boolean visit(final ICompoundStatement node) {

    if (node == target) {
      statement = node;
    }
    lastStatement = node;
    return statement == null;
  }

  @Override
  public boolean visit(final IFunction node) {

    // process the children
    return true;
  }

  @Override
  public boolean visit(final IIfThenElse node) {

    if (node.predicate().leftOperand() == target || node.predicate().rightOperand() == target) {
      statement = node;
    }
    lastStatement = node;
    return statement == null;
  }

  @Override
  public boolean visit(final IReturn node) {

    if (node.expression() == target) {
      statement = node;
    }
    lastStatement = node;
    return statement == null;
  }

  @Override
  public boolean visit(final ITerm node) {

    if (node.leftOperand() == target || node.rightOperand() == target) {
      statement = lastStatement;
    }
    return statement == null;
  }

  @Override
  public boolean visit(final IWhile node) {

    if (node.predicate().leftOperand() == target || node.predicate().rightOperand() == target) {
      statement = node;
    }
    lastStatement = node;
    return statement == null;
  }
}
