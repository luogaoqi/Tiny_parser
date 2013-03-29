// there are no missing components here

package parser;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IAssignment;
import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IDecimalLiteral;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.IIntegerLiteral;
import parser.IParse.IParseNodeVisitor;
import parser.IParse.IReturn;
import parser.IParse.ITerm;
import parser.IParse.IVariable;
import parser.IParse.IWhile;

public abstract class ParseNodeVisitor implements IParseNodeVisitor {

  @Override
  public boolean visit(final IArithmeticExpression node) {

    return false;
  }

  @Override
  public boolean visit(final IAssignment node) {

    return false;
  }

  @Override
  public boolean visit(final IBooleanExpression node) {

    return false;
  }

  @Override
  public boolean visit(final ICompoundStatement node) {

    return false;
  }

  @Override
  public boolean visit(final IDecimalLiteral node) {

    return false;
  }

  @Override
  public boolean visit(final IFunction node) {

    return false;
  }

  @Override
  public boolean visit(final IIfThenElse node) {

    return false;
  }

  @Override
  public boolean visit(final IIntegerLiteral node) {

    return false;
  }

  @Override
  public boolean visit(final IReturn node) {

    return false;
  }

  @Override
  public boolean visit(final ITerm node) {

    return false;
  }

  @Override
  public boolean visit(final IVariable node) {

    return false;
  }

  @Override
  public boolean visit(final IWhile node) {

    return false;
  }
}
