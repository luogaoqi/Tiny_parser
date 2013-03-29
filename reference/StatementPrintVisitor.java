package reference;

import java.util.List;

import parser.IParse.IAssignment;
import parser.IParse.ICompoundStatement;
import parser.IParse.IExpression;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.IReturn;
import parser.IParse.IVariable;
import parser.IParse.IWhile;
import parser.ParseNodeVisitor;

public class StatementPrintVisitor extends ParseNodeVisitor {

  private final StringBuffer buffer;

  public StatementPrintVisitor() {

    buffer = new StringBuffer("");
  }

  public String getText() {

    return buffer.toString();
  }

  @Override
  public boolean visit(final IAssignment node) {

    buffer.append(node.variable().name());
    buffer.append("=");
    appendExpression(node.expression());
    buffer.append(";\n");
    return false;
  }

  @Override
  public boolean visit(final ICompoundStatement node) {

    // process the children
    return true;
  }

  @Override
  public boolean visit(final IFunction node) {

    buffer.append("fun ");
    buffer.append(node.name());
    appendParamList(node.parameters());
    buffer.append(" {\n");
    appendStatement(node.body());
    buffer.append("\n}.\n");
    return false;
  }

  @Override
  public boolean visit(final IIfThenElse node) {

    buffer.append("if (");
    appendExpression(node.predicate().leftOperand());
    buffer.append(node.predicate().operator().getText());
    appendExpression(node.predicate().rightOperand());
    buffer.append(") then {\n");
    appendStatement(node.thenStatement());
    buffer.append("\n}\nelse {\n");
    appendStatement(node.elseStatement());
    buffer.append("}\n");
    return false;
  }

  @Override
  public boolean visit(final IReturn node) {

    buffer.append("return ");
    appendExpression(node.expression());
    buffer.append(";");
    return false;
  }

  @Override
  public boolean visit(final IWhile node) {

    buffer.append("while (");
    appendExpression(node.predicate().leftOperand());
    buffer.append(node.predicate().operator().getText());
    appendExpression(node.predicate().rightOperand());
    buffer.append(") {\n");
    appendStatement(node.body());
    buffer.append("}\n");
    return false;
  }

  private void appendExpression(final IExpression expression) {

    final ExpressionPrintVisitor epv = new ExpressionPrintVisitor();
    expression.accept(epv);
    buffer.append(epv.getText());
  }

  private void appendParamList(final List<IVariable> parameters) {

    buffer.append("(");
    for (int i = 0; i < parameters.size(); i++) {
      buffer.append(parameters.get(i).name());
      if (i < parameters.size() - 1) {
        buffer.append(",");
      }
    }
    buffer.append(")");
  }

  private void appendStatement(final ICompoundStatement statement) {

    final StatementPrintVisitor spv = new StatementPrintVisitor();
    statement.accept(spv);
    buffer.append(spv.getText());
  }
}
