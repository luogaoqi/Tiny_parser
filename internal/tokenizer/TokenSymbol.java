package internal.tokenizer;

import tokenizer.IToken;
import tokenizer.TokenClass;

public enum TokenSymbol implements IToken {
  TK_COMMA(","),
  TK_DIVIDE("/"),
  TK_DOT("."),
  TK_EQ("="),
  TK_GE(">="),
  TK_GT(">"),
  TK_LE("<="),
  TK_LBRACE("{"),
  TK_LPARENS("("),
  TK_LT("<"),
  TK_MINUS("-"),
  TK_NE("<>"),
  TK_PLUS("+"),
  TK_RBRACE("}"),
  TK_RPARENS(")"),
  TK_SEMICOLON(";"),
  TK_TIMES("*");

  static final String TOKENS;
  static {
    String temp = "";
    for (final TokenSymbol tk : values()) {
      // all other symbols of length 2 are prefixed by a length 1 symbol
      if (tk.symbol.length() == 1) {
        temp += tk.symbol;
      }
    }
    TOKENS = temp;
  }

  private final String symbol;

  static TokenSymbol getToken(final String token) {

    for (final TokenSymbol tk : values()) {
      if (tk.symbol.equals(token)) {
        return tk;
      }
    }
    return null;
  }

  private TokenSymbol(final String symbol) {

    this.symbol = symbol;
  }

  @Override
  public String getText() {

    return String.valueOf(symbol);
  }

  @Override
  public TokenClass getTokenClass() {

    return TokenClass.TC_SYMBOL;
  }

  @Override
  public String toString() {

    return String.format("%s(%s)", getTokenClass(), getText());
  }
}