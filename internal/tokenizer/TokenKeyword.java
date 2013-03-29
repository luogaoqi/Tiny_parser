package internal.tokenizer;

import tokenizer.IToken;
import tokenizer.TokenClass;

public enum TokenKeyword implements IToken {
  TK_ELSE("ELSE", TokenClass.TC_KEYWORD),
  TK_FUN("FUN", TokenClass.TC_KEYWORD),
  TK_IF("IF", TokenClass.TC_KEYWORD),
  TK_RETURN("RETURN", TokenClass.TC_KEYWORD),
  TK_WHILE("WHILE", TokenClass.TC_KEYWORD);

  private final String keyword;

  private final TokenClass tokenClass;

  static TokenKeyword getToken(final String token) {

    for (final TokenKeyword tk : values()) {
      if (tk.keyword.equalsIgnoreCase(token)) {
        return tk;
      }
    }
    return null;
  }

  private TokenKeyword(final String token, final TokenClass tokenClass) {

    this.keyword = token;
    this.tokenClass = tokenClass;
  }

  @Override
  public String getText() {

    return keyword;
  }

  @Override
  public TokenClass getTokenClass() {

    return tokenClass;
  }

  @Override
  public String toString() {

    return String.format("%s(%s)", getTokenClass(), getText());
  }
}