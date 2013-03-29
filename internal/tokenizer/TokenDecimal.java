package internal.tokenizer;

import tokenizer.TokenClass;

public class TokenDecimal extends TokenNumber {

  private final Double value;

  TokenDecimal(final Double value) {

    this.value = value;
  }

  @Override
  public String getText() {

    return String.valueOf(value);
  }

  @Override
  public TokenClass getTokenClass() {

    return TokenClass.TC_LITERAL_DECIMAL;
  }

  @Override
  public Double getValue() {

    return value;
  }
}