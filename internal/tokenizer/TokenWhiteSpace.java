package internal.tokenizer;

import tokenizer.IToken;
import tokenizer.TokenClass;

enum TokenWhiteSpace implements IToken {
  TK_CR('\r'),
  TK_NL('\n'),
  TK_SPACE(' '),
  TK_TAB('\t');

  static final String TOKENS;
  static {
    String temp = "";
    for (final TokenWhiteSpace tk : values()) {
      temp += tk.symbol;
    }
    TOKENS = temp;
  }

  private final char symbol;

  static TokenWhiteSpace getToken(final char token) {

    for (final TokenWhiteSpace tk : values()) {
      if (tk.symbol == token) {
        return tk;
      }
    }
    return null;
  }

  private TokenWhiteSpace(final char symbol) {

    this.symbol = symbol;
  }

  @Override
  public String getText() {

    return String.valueOf(symbol);
  }

  @Override
  public TokenClass getTokenClass() {

    return TokenClass.TC_WHITE;
  }

  @Override
  public String toString() {

    return String.format("%s(%s)", getTokenClass(), getText());
  }
}