package internal.tokenizer;

import tokenizer.IToken;

abstract class TokenNumber implements IToken {

  @Override
  public String toString() {

    return String.format("%s(%s)", getTokenClass(), getText());
  }

  abstract Number getValue();
}