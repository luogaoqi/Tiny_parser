package internal.tokenizer;

import tokenizer.IToken;
import tokenizer.TokenClass;

public class TokenIdentifier implements IToken {

  private final String token;

  TokenIdentifier(final String token) {

    this.token = token;
  }

  @Override
  public String getText() {

    return token;
  }

  @Override
  public TokenClass getTokenClass() {

    return TokenClass.TC_IDENTIFIER;
  }

  @Override
  public String toString() {

    return String.format("%s(%s)", getTokenClass(), getText());
  }
}