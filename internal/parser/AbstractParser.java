package internal.parser;

import internal.tokenizer.TokenizerFactory;

import java.util.List;

import parser.IParse.IFunction;
import parser.IParse.IParser;
import parser.ParserException;
import tokenizer.IToken;
import tokenizer.ITokenizer;
import tokenizer.TokenClass;
import tokenizer.TokenizerException;

abstract class AbstractParser implements IParser {

  protected static final boolean DEBUG_MODE = false;
  private static final String ERR_INVALID_POSITION = "Parser error: repositioning beyond token stream boundaries (%d).";
  private static final String ERR_TOKENIZER = "Syntax error while tokenizing source string.";
  private static final String ERR_UNEXPECTED_EOS = "Syntax error. Expected some token  but found end of token stream instad.";
  private static final String ERR_UNEXPECTED_TOKEN = "Syntax error. Expected token '%s' but found token '%s' instead.";
  private static final String ERR_UNEXPECTED_TOKEN_CLASS = "Syntax error. Expected token of type '%s' but found token '%s' instead.";
  private static final String ERR_UNEXPECTED_TOKENS = "Syntax error. Expected end of token stream but found %d tokens instead.";
  private int tk;
  protected IToken token;
  protected List<IToken> tokens;

  @Override
  public final IFunction parse(final String source) throws ParserException {

    tokenize(source);
    beforeParse();
    try {
      return doParse();
    }
    finally {
      afterParse();
    }
  }

  protected boolean advance() {

    // token before advancing
    if (tk > -1) {
      token = tokens.get(tk);
    }
    else {
      token = null;
    }
    // updated pointer into the token list
    tk += 1;
    return true;
  }

  protected boolean advanceTo(final IToken token) {

    while (testToken() && !isToken(token) && advance()) {
      // no-op
    }
    return isToken(token) && advance();
  }

  protected void afterParse() {

    resetTokenStream();
  }

  protected void beforeParse() {

  }

  protected boolean check(final IToken token) throws ParserException {

    return checkToken()
        && (test(token) || die(ERR_UNEXPECTED_TOKEN, token.getText(), tokens.get(tk).getText()));
  }

  protected boolean check(final TokenClass tokenClass) throws ParserException {

    return checkToken()
        && (test(tokenClass) || die(ERR_UNEXPECTED_TOKEN_CLASS, tokenClass.getText(), tokens
            .get(tk).getText()));
  }

  protected boolean checkEOS() throws ParserException {

    return (tk == tokens.size()) || die(ERR_UNEXPECTED_TOKENS, tokens.size() - tk - 1);
  }

  protected boolean checkToken() throws ParserException {

    return testToken() || die(ERR_UNEXPECTED_EOS);
  }

  protected void debug(final String message) {

    if (DEBUG_MODE) {
      System.err.println(message);
    }
  }

  protected void debug(final String message, final Object... args) {

    if (DEBUG_MODE) {
      System.err.format(message, args);
    }
  }

  protected boolean die(final String message) throws ParserException {

    debug("Dying at token (id: %d, value: %s)\n", tk, tokens.get(tk));
    throw new ParserException(message);
  }

  protected boolean die(final String message, final Object... args) throws ParserException {

    debug("Dying at token (id: %d, value: %s)\n", tk, tokens.get(tk));
    throw new ParserException(String.format(message, args));
  }

  protected boolean die(final String message, final Throwable cause) throws ParserException {

    debug("Dying at token (id: %d, value: %s)\n", tk, tokens.get(tk));
    throw new ParserException(message, cause);
  }

  protected abstract IFunction doParse() throws ParserException;

  protected boolean isToken(final IToken token) {

    return testToken() && tokens.get(tk).equals(token);
  }

  protected boolean isTokenClass(final TokenClass tokenClass) {

    return testToken() && tokens.get(tk).getTokenClass() == tokenClass;
  }

  protected int mark() {

    return tk;
  }

  protected void moveTo(final int position) throws ParserException {

    if (position < 0 || position >= tokens.size()) {
      die(ERR_INVALID_POSITION, position);
    }
    tk = position;
  }

  protected void resetTokenStream() {

    tokens = null;
    tk = -1;
  }

  protected boolean test(final IToken token) {

    return isToken(token) && advance();
  }

  protected boolean test(final TokenClass tokenClass) {

    return isTokenClass(tokenClass) && advance();
  }

  protected boolean testToken() {

    return (tk > -1 && tk < tokens.size());
  }

  private void tokenize(final String source) throws ParserException {

    final ITokenizer t = TokenizerFactory.INSTANCE.createTokenizer();
    try {
      resetTokenStream();
      tokens = t.tokenize(source);
      if (DEBUG_MODE) {
        debug(tokens.toString());
      }
      advance();
    }
    catch (final TokenizerException te) {
      die(ERR_TOKENIZER, te);
    }
  }
}
