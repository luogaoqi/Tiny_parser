package inference;

import java.util.List;
import java.util.Map;

import parser.IParse.IParseNode;
import parser.IParse.IParseNodeVisitor;

public interface IType {

  /**
   * Represents a yet unknown type of a return node in the parse tree.
   */
  public interface IReturnTypeVariable extends ITypeVariable {

  }

  /**
   * Represents a type equation in the language. The following types of equations are supported:
   * 
   * <pre>
   *   T(var)  = T(exp1), e.g., for assignments such as 'z = 10'
   * 
   *   T(var)  = T(exp1) op T(exp2), e.g., for assignments such as 'z = (x*y + 1) / (z - 10)'
   * 
   *   BOOLEAN = T(exp1) op T(exp1), e.g., for boolean expressions such as 'x+1 < 2*y'
   * </pre>
   * 
   * These equations are uniformly represented by the ITypeEquation type.
   */
  public interface ITypeEquation {

    public ITypeVariable getExpression1();

    public ITypeVariable getExpression2();

    public ITypeVariable getLHS();

    public String getOperator();

    public int size();
  }

  public interface ITypeEquationResolver {

    public void solveEquations();
  }

  public interface ITypeEquationVisitor extends IParseNodeVisitor {

    public List<ITypeEquation> getEquations();

    public Map<ITypeVariable, IParseNode> getVariableMap();
  }

  /**
   * Represents a yet unknown type of a node in the parse tree.
   */
  public interface ITypeVariable {

    public String getId();
  }

  /**
   * Set of types in the language-- UNDEFINED is used only as a temporary place holder during type
   * inference.
   */
  public static enum Type {
    BOOLEAN,
    INTEGER,
    REAL,
    UNDEFINED;

    public boolean isNumber() {

      return (this == INTEGER) || (this == REAL);
    }

    @Override
    public String toString() {

      return name();
    }
  }
}
