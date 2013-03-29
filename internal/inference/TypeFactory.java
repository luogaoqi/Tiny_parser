// this class is provided to you in entirety

package internal.inference;

import inference.IType.IReturnTypeVariable;
import inference.IType.ITypeEquation;
import inference.IType.ITypeEquationResolver;
import inference.IType.ITypeEquationVisitor;
import inference.IType.ITypeVariable;
import inference.ITypeFactory;
import parser.IParse.IFunction;
import parser.IParse.IParseNode;

public class TypeFactory implements ITypeFactory {

  public ITypeVariable newReturnVariable() {

    return new ReturnVariable();
  }

  @Override
  public ITypeEquation newTypeEquation(final ITypeVariable lhs, final ITypeVariable exp1) {

    return new TypeEquation(lhs, exp1, null, null);
  }

  @Override
  public ITypeEquation newTypeEquation(final ITypeVariable lhs, final ITypeVariable exp1,
      final String op, final ITypeVariable exp2) {

    return new TypeEquation(lhs, exp1, op, exp2);
  }

  @Override
  public ITypeEquationResolver newTypeEquationResolver(final IFunction function,
      final ITypeEquationVisitor visitor) {

    return new TypeEquationResolver(function, visitor);
  }

  @Override
  public ITypeEquationVisitor newTypeEquationVisitor() {

    return new TypeEquationVisitor();
  }

  @Override
  public ITypeVariable newTypeVariable(final IParseNode node) {

    return new TypeVariable(node);
  }

  private static class ReturnVariable implements IReturnTypeVariable {

    @Override
    public String getId() {

      return "T_0";
    }

    @Override
    public String toString() {

      return getId();
    }
  }

  private static class TypeEquation implements ITypeEquation {

    private final ITypeVariable lhs;
    private final ITypeVariable exp1;
    private final ITypeVariable exp2;
    private final String operator;

    TypeEquation(final ITypeVariable lhs, final ITypeVariable exp1, final String op,
        final ITypeVariable exp2) {

      this.exp1 = exp1;
      this.exp2 = exp2;
      this.lhs = lhs;
      this.operator = op;
    }

    @Override
    public ITypeVariable getExpression1() {

      return this.exp1;
    }

    @Override
    public ITypeVariable getExpression2() {

      return this.exp2;
    }

    @Override
    public ITypeVariable getLHS() {

      return this.lhs;
    }

    @Override
    public String getOperator() {

      return this.operator;
    }

    @Override
    public int size() {

      return 2 + (exp2 == null ? 0 : 1);
    }

    @Override
    public String toString() {

      final StringBuffer buffer = new StringBuffer("");
      buffer.append(lhs.toString());
      buffer.append(" = ");
      buffer.append(exp1.toString());
      if (exp2 != null) {
        buffer.append(" op ");
        buffer.append(exp2.toString());
      }
      return buffer.toString();
    }
  }

  private static class TypeVariable implements ITypeVariable {

    private final String id;

    TypeVariable(final IParseNode node) {

      this.id = node.varId();
    }

    @Override
    public String getId() {

      return this.id;
    }

    @Override
    public String toString() {

      return id;
    }
  }
}


}
