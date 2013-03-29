package inference;

import inference.IType.ITypeEquation;
import inference.IType.ITypeEquationResolver;
import inference.IType.ITypeEquationVisitor;
import inference.IType.ITypeVariable;
import parser.IParse.IFunction;
import parser.IParse.IParseNode;

public interface ITypeFactory {

  public ITypeEquation newTypeEquation(ITypeVariable lhs, ITypeVariable exp1);

  public ITypeEquation newTypeEquation(ITypeVariable lhs, ITypeVariable exp1, String op,
      ITypeVariable exp2);

  public ITypeEquationResolver newTypeEquationResolver(IFunction function,
      ITypeEquationVisitor visitor);

  public ITypeEquationVisitor newTypeEquationVisitor();

  public ITypeVariable newTypeVariable(IParseNode node);
}
