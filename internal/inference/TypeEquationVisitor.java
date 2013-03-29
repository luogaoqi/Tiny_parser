package internal.inference;

import inference.IType.ITypeEquation;
import inference.IType.ITypeEquationVisitor;
import inference.IType.ITypeVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

class TypeEquationVisitor extends ParseNodeVisitor implements ITypeEquationVisitor {

 private final List<ITypeEquation> equations;
  private final TypeFactory typeFactory;
  private final Map<ITypeVariable, IParseNode> nodeToVar;

  TypeEquationVisitor() {

    this.equations = new ArrayList<ITypeEquation>();
    this.typeFactory = new TypeFactory();
    this.nodeToVar = new HashMap<ITypeVariable, IParseNode>();
  }

@Override
  public boolean visit(final IArithmeticExpression node) {

    final ITypeVariable lhs = typeFactory.newTypeVariable(node);
    final ITypeVariable exp1 = typeFactory.newTypeVariable(node.leftOperand());
    final ITypeVariable exp2 = typeFactory.newTypeVariable(node.rightOperand());
    nodeToVar.put(lhs, node);
    nodeToVar.put(exp1, node.leftOperand());
    nodeToVar.put(exp2, node.rightOperand());

    final ITypeEquation eq = typeFactory.newTypeEquation(lhs, exp1, node.operator().getText(), exp2);

    equations.add(eq);

    // process the children
    return true;
  }

// similarly add code for other cases where a type equation is to be generated

}
