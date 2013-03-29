package internal.parser;

import static internal.tokenizer.TokenKeyword.TK_ELSE;
import static internal.tokenizer.TokenKeyword.TK_FUN;
import static internal.tokenizer.TokenKeyword.TK_IF;
import static internal.tokenizer.TokenKeyword.TK_RETURN;
import static internal.tokenizer.TokenKeyword.TK_WHILE;
import static internal.tokenizer.TokenSymbol.TK_COMMA;
import static internal.tokenizer.TokenSymbol.TK_DIVIDE;
import static internal.tokenizer.TokenSymbol.TK_DOT;
import static internal.tokenizer.TokenSymbol.TK_EQ;
import static internal.tokenizer.TokenSymbol.TK_GE;
import static internal.tokenizer.TokenSymbol.TK_GT;
import static internal.tokenizer.TokenSymbol.TK_LBRACE;
import static internal.tokenizer.TokenSymbol.TK_LE;
import static internal.tokenizer.TokenSymbol.TK_LPARENS;
import static internal.tokenizer.TokenSymbol.TK_LT;
import static internal.tokenizer.TokenSymbol.TK_MINUS;
import static internal.tokenizer.TokenSymbol.TK_NE;
import static internal.tokenizer.TokenSymbol.TK_PLUS;
import static internal.tokenizer.TokenSymbol.TK_RBRACE;
import static internal.tokenizer.TokenSymbol.TK_RPARENS;
import static internal.tokenizer.TokenSymbol.TK_SEMICOLON;
import static internal.tokenizer.TokenSymbol.TK_TIMES;
import static tokenizer.TokenClass.TC_IDENTIFIER;
import static tokenizer.TokenClass.TC_LITERAL_DECIMAL;
import static tokenizer.TokenClass.TC_LITERAL_INTEGER;
import internal.tokenizer.TokenDecimal;
import internal.tokenizer.TokenIdentifier;
import internal.tokenizer.TokenInteger;

import java.util.ArrayList;
import java.util.List;

import parser.IParse.IBooleanExpression;
import parser.IParse.ICompoundStatement;
import parser.IParse.IExpression;
import parser.IParse.IFunction;
import parser.IParse.ISimpleStatement;
import parser.IParse.IVariable;
import parser.IParseFactory;
import parser.ParserException;
import tokenizer.IToken;

class Parser extends AbstractParser {

// Some code provided, March 27, 2012 - BJ

  private static final String ERR_INVALID_BOOLEXPR = "Missing relational opr.";

  private final IParseFactory factory;

  Parser(final IParseFactory factory) {

    this.factory = factory;
  }

  @Override
  public IFunction doParse() throws ParserException {

    return function();
  }

  // function --> 'fun' ident '(' idlist ')' cmpdstmt '.'
  private IFunction function() throws ParserException {
    check(TK_FUN);
    final String identifier = identifier();
    final List<IVariable> parameters = parameterList();
    final ICompoundStatement body = compoundStatement();
    check(TK_DOT);
    checkEOS();
    return factory.newFunction(identifier, parameters, body);
  }


  // bexpr --> aexpr REL_OP aexpr
  // REL_OP --> '>' | '<' | '=' | '<>' | '<=' | '>='

  private IBooleanExpression booleanExpression() throws ParserException {

    final IExpression left = expression();
    if (test(TK_GE) || test(TK_GT) || test(TK_LE) || test(TK_LT) || test(TK_EQ) || test(TK_NE)) {
      final IToken operator = token;
      final IExpression right = expression();
      return factory.newBooleanExpression(left, operator, right);
    }
    die(ERR_INVALID_BOOLEXPR);
    return null;
  }


// remaining code to be written by you


}
