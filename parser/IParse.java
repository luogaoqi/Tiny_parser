package parser;

import java.util.Collection;
import java.util.List;

import tokenizer.IToken;

/**
 * <pre>
 * 
 * Keywords...: fun, if, while, return
 * Types......: boolean, integer, real
 * Symbols....: '(', ')', ';', '{', '}', '.', '<', '>', '=', '+', '-', '*', '/', 0..9, A..Z, a..z
 * 
 * Grammar
 * =======
 * 
 * function   -->  'fun' ident '(' idlist ')' cmpdstmt '.'
 * 
 * cmpdsmt    -->  '{' stmt [ stmt ]* '}'
 * 
 * stmt       -->  assign | ifthenelse | returnstmt | while 
 * 
 * assign     -->  var '=' aexpr ';'
 * 
 * ifthenelse -->  'if' '(' bexpr ')' cmpdstmt ['else' cmpdstmt]
 * 
 * while      -->  'while' '(' bexpr ')' cmpdstmt
 * 
 * returnstmt --> 'return' aexpr ';'
 * 
 * bexpr      --> aexpr REL_OP aexpr
 * 
 * aexpr      --> term [ ( + | - ) term ]
 * 
 * term       --> factor [ ( * | / ) factor ]
 * 
 * factor     --> REAL | INTEGER | var | '(' aexpr ')'
 * 
 * var        --> IDENT
 * 
 * REL_OP     --> '>' | '<' | '=' | '<>' | '<=' | '>='
 * 
 * IDENT      --> CHARACTER
 * 
 * TEST CASE: factorial
 * 
 *   fun factorial(n) { 
 *     i = 1; 
 *     f = 1; 
 *     while (i < n + 1)  { 
 *       f = f*i; 
 *       i = i+1; 
 *     } 
 *     return f; 
 *   }.
 * </pre>
 */
public interface IParse {

  public interface IArithmeticExpression extends IExpression {

    public IExpression leftOperand();

    public IToken operator();

    public IExpression rightOperand();
  }

  public interface IAssignment extends ISimpleStatement {

    public IExpression expression();

    public IVariable variable();
  }

  public interface IBooleanExpression extends IParseNode {

    public IExpression leftOperand();

    public IToken operator();

    public IExpression rightOperand();
  }

  public interface ICompoundStatement extends IParseNode {

    public Collection<ISimpleStatement> statements();
  }

  public interface IDecimalLiteral extends ILiteral {

    public Double value();
  }

  public interface IExpression extends IParseNode {

  }

  public interface IFunction extends IParseNode {

    public ICompoundStatement body();

    public String name();

    public List<IVariable> parameters();
  }

  public interface IIfThenElse extends ISimpleStatement {

    public ICompoundStatement elseStatement();

    public IBooleanExpression predicate();

    public ICompoundStatement thenStatement();
  }

  public interface IIntegerLiteral extends ILiteral {

    public Integer value();
  }

  public interface ILiteral extends IExpression {

  }

  public interface IParseNode {

    public void accept(IParseNodeVisitor visitor);

    public int id();

    public String varId();
  }

  public interface IParseNodeVisitor {

    public boolean visit(final IArithmeticExpression node);

    public boolean visit(final IAssignment node);

    public boolean visit(final IBooleanExpression node);

    public boolean visit(final ICompoundStatement node);

    public boolean visit(final IDecimalLiteral node);

    public boolean visit(final IFunction node);

    public boolean visit(final IIfThenElse node);

    public boolean visit(final IIntegerLiteral node);

    public boolean visit(final IReturn node);

    public boolean visit(final ITerm node);

    public boolean visit(final IVariable node);

    public boolean visit(final IWhile node);
  }

  public interface IParser {

    public IFunction parse(String source) throws ParserException;
  }

  public interface IReturn extends ISimpleStatement {

    public IExpression expression();
  }

  public interface ISimpleStatement extends IParseNode {

  }

  public interface ITerm extends IExpression {

    public IExpression leftOperand();

    public IToken operator();

    public IExpression rightOperand();
  }

  public interface IVariable extends IExpression {

    public String name();
  }

  public interface IWhile extends ISimpleStatement {

    public ICompoundStatement body();

    public IBooleanExpression predicate();
  }
}