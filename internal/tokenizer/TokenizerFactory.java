package internal.tokenizer;

import tokenizer.ITokenizer;

public enum TokenizerFactory {
  INSTANCE;

  public ITokenizer createTokenizer() {

    return new Tokenizer();
  }
}
