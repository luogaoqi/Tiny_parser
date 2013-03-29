package reference;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IAssignment;
import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IDecimalLiteral;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.IIntegerLiteral;
import parser.IParse.IReturn;
import parser.IParse.ITerm;
import parser.IParse.IVariable;
import parser.IParse.IWhile;
import parser.ParseNodeVisitor;

public class NodePrintVisitor extends ParseNodeVisitor {

  @Override
  public boolean visit(final IArithmeticExpression node) {

    System.out.format("id=%d, Arithmetic Expression '%s'\n", node.id(), node.operator().getText());
    return true;
  }

  @Override
  public boolean visit(final IAssignment node) {

    System.out.format("id=%d, Assignment\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final IBooleanExpression node) {

    System.out.format("id=%d, Boolean Expression '%s'\n", node.id(), node.operator().getText());
    return true;
  }

  @Override
  public boolean visit(final ICompoundStatement node) {

    System.out.format("id=%d, Compound Statement\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final IDecimalLiteral node) {

    System.out.format("id=%d, Decimal '%5.2f'\n", node.id(), node.value());
    return true;
  }

  @Override
  public boolean visit(final IFunction node) {

    System.out.format("id=%d, Function\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final IIfThenElse node) {

    System.out.format("id=%d, If-Then-Else'\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final IIntegerLiteral node) {

    System.out.format("id=%d, Integer '%d'\n", node.id(), node.value());
    return true;
  }

  @Override
  public boolean visit(final IReturn node) {

    System.out.format("id=%d, Return\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final ITerm node) {

    System.out.format("id=%d, Term '%s'\n", node.id(), node.operator().getText());
    return true;
  }

  @Override
  public boolean visit(final IVariable node) {

    System.out.format("id=%d, Variable\n", node.id());
    return true;
  }

  @Override
  public boolean visit(final IWhile node) {

    System.out.format("id=%d, While\n", node.id());
    return true;
  }
}
