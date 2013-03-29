package parser;

import java.util.List;

import parser.IParse.IArithmeticExpression;
import parser.IParse.IAssignment;
import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IExpression;
import parser.IParse.IFunction;
import parser.IParse.IIfThenElse;
import parser.IParse.ILiteral;
import parser.IParse.IParser;
import parser.IParse.IReturn;
import parser.IParse.ISimpleStatement;
import parser.IParse.ITerm;
import parser.IParse.IVariable;
import parser.IParse.IWhile;
import tokenizer.IToken;

public interface IParseFactory {

  public IArithmeticExpression newArithmeticExpression(IExpression leftOperand, IToken operator,
      IExpression rightOperand);

  public IAssignment newAssignment(IVariable variable, IExpression expression);

  public IBooleanExpression newBooleanExpression(IExpression leftOperand, IToken operator,
      IExpression rightOperand);

  public ICompoundStatement newCompoundStatement(List<ISimpleStatement> statements);

  public ILiteral newDecimal(Double value);

  public IFunction newFunction(String name, List<IVariable> parameters, ICompoundStatement body);

  public IIfThenElse newIfThen(IBooleanExpression ifPredicate, ICompoundStatement thenPart);

  public IIfThenElse newIfThenElse(IBooleanExpression ifPredicate,
      ICompoundStatement thenStatement, ICompoundStatement elseStatement);

  public ILiteral newInteger(Integer value);

  public IParser newParser();

  public IReturn newReturn(IExpression expression);

  public ITerm newTerm(IExpression leftOperand, IToken operator, IExpression rightOperand);

  public IVariable newVariable(String name);

  public IWhile newWhile(IBooleanExpression whilePredicate, ICompoundStatement body);
}