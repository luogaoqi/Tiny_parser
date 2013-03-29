package internal.inference;

import inference.IType.IReturnTypeVariable;
import inference.IType.ITypeEquation;
import inference.IType.ITypeEquationResolver;
import inference.IType.ITypeEquationVisitor;
import inference.IType.ITypeVariable;
import inference.IType.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import parser.IParse.IBooleanExpression;
import parser.IParse.IDecimalLiteral;
import parser.IParse.IFunction;
import parser.IParse.IIntegerLiteral;
import parser.IParse.IParseNode;
import parser.IParse.IVariable;
import reference.StatementFinderVisitor;

class TypeEquationResolver implements ITypeEquationResolver {

 private static final boolean PRINT_ITERATIONS = true;
  private final List<ITypeEquation> equations;
  private final List<ITypeEquation> resolved;
  private final IFunction function;
  private final Map<String, Type> varToType;
  private final Map<ITypeVariable, IParseNode> varToNode;

  TypeEquationResolver(final IFunction function, final ITypeEquationVisitor visitor) {

    this.equations = visitor.getEquations();
    this.function = function;
    this.resolved = new ArrayList<ITypeEquation>();
    this.varToNode = visitor.getVariableMap();
    this.varToType = new HashMap<String, Type>();
  }

  public void printEquations() {

    for (final ITypeEquation eq : resolved) {
      final String lhs = varToString(eq.getLHS());
      final String exp1 = varToString(eq.getExpression1());
      if (eq.getExpression2() == null) {
        System.out.format("  %s = %s\n", lhs, exp1);
      }
      else {
        final String exp2 = varToString(eq.getExpression2());
        System.out.format("  %s = %s %s %s\n", lhs, exp1, eq.getOperator().toString(), exp2);
      }
    }
  }

 private String varToString(final ITypeVariable var) {

    return varType(var) != Type.UNDEFINED ? varType(var).toString() : var.toString();
  }

  private Type varType(final ITypeVariable variable) {

    return varToType.get(variable.getId());
  }
}

// rest of the code to be written by you

}
