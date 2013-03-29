package reference;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IBooleanExpression;
import parser.IParse.IDecimalLiteral;
import parser.IParse.IExpression;
import parser.IParse.IIntegerLiteral;
import parser.IParse.ILiteral;
import parser.IParse.ITerm;
import parser.IParse.IVariable;
import parser.ParseNodeVisitor;

public class ExpressionPrintVisitor extends ParseNodeVisitor {

  private final StringBuffer buffer;

  ExpressionPrintVisitor() {

    buffer = new StringBuffer("");
  }

  public String getText() {

    return buffer.toString();
  }

  @Override
  public boolean visit(final IArithmeticExpression node) {

    appendExpression(node.leftOperand());
    buffer.append(node.operator().getText());
    appendExpression(node.rightOperand());
    return false;
  }

  @Override
  public boolean visit(final IBooleanExpression node) {

    appendExpression(node.leftOperand());
    buffer.append(node.operator().getText());
    appendExpression(node.rightOperand());
    return false;
  }

  @Override
  public boolean visit(final IDecimalLiteral node) {

    buffer.append(node.value());
    return false;
  }

  @Override
  public boolean visit(final IIntegerLiteral node) {

    buffer.append(node.value());
    return false;
  }

  @Override
  public boolean visit(final ITerm node) {

    appendExpression(node.leftOperand());
    buffer.append(node.operator().getText());
    appendExpression(node.rightOperand());
    return false;
  }

  @Override
  public boolean visit(final IVariable node) {

    buffer.append(node.name());
    return false;
  }

  private void appendExpression(final IExpression expression) {

    if (isTerminal(expression)) {
      if (expression instanceof IDecimalLiteral) {
        visit((IDecimalLiteral) expression);
      }
      else if (expression instanceof IIntegerLiteral) {
        visit((IIntegerLiteral) expression);
      }
      else {
        visit((IVariable) expression);
      }
    }
    else {
      final ExpressionPrintVisitor epv = new ExpressionPrintVisitor();
      expression.accept(epv);
      buffer.append(epv.getText());
    }
  }

  private boolean isTerminal(final IExpression expression) {

    return expression instanceof ILiteral || expression instanceof IVariable;
  }
}
