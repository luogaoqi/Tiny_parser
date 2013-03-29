package internal.tokenizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import tokenizer.IToken;
import tokenizer.ITokenizer;
import tokenizer.TokenizerException;

final class Tokenizer implements ITokenizer {

  private static final boolean DEBUG_MODE = false;
  private StringTokenizer tokenizer;
  private List<IToken> tokens;

  @Override
  public List<IToken> tokenize(final String source) throws TokenizerException {

    if (DEBUG_MODE) {
      System.err.println("tokenize(String) enter: " + source);
    }
    tokens = new ArrayList<IToken>();
    tokenizer = new StringTokenizer(source, TokenSymbol.TOKENS + TokenWhiteSpace.TOKENS, true);
    try {
      while (tokenizer.hasMoreTokens()) {
        consume(tokenizer.nextToken());
      }
      return Collections.unmodifiableList(tokens);
    }
    finally {
      tokens = null;
      tokenizer = null;
      if (DEBUG_MODE) {
        System.err.println("tokenize(String) exit");
      }
    }
  }

  private void addToken(final IToken tk) {

    if (DEBUG_MODE) {
      System.err.println("addToken(Token) enter: " + tk);
    }
    tokens.add(tk);
    if (DEBUG_MODE) {
      System.err.println("addToken(Token) exit");
    }
  }

  private void consume(final String tkString) throws TokenizerException {

    IToken tk = null;
    // try white space
    tk = TokenWhiteSpace.getToken(tkString.charAt(0));
    // white space indeed
    if (tk != null) {
      // ignore the token and proceed
      return;
    }
    // try symbol
    tk = TokenSymbol.getToken(tkString);
    // symbol indeed
    if (tk != null) {
      if (tk == TokenSymbol.TK_LT) {
        // < or <= or <> in the input stream
        scanLT_LE_NE();
      }
      else if (tk == TokenSymbol.TK_GT) {
        // > or >= in the input stream
        scanGT_GE();
      }
      else {
        // symbol in the input stream
        addToken(tk);
      }
      // done
      return;
    }
    // try keyword
    tk = TokenKeyword.getToken(tkString);
    // keyword indeed
    if (tk != null) {
      // keyword in the input stream
      addToken(tk);
      return;
    }
    // try integer
    try {
      final Integer num = Integer.parseInt(tkString);
      // integer or decimal in the input stream
      scanDecimal(num);
      return;
    }
    catch (final NumberFormatException nfe) {
      // proceed to the next parsing attempt
    }
    // try identifier
    boolean isIdent = Character.isLetter(tkString.charAt(0));
    if (isIdent) {
      for (int i = 1; i < tkString.length(); i++) {
        isIdent = isIdent
            && (tkString.charAt(i) == '_' || Character.isLetterOrDigit(tkString.charAt(i)));
      }
    }
    // identifier indeed
    if (isIdent) {
      // identifier in the input stream
      addToken(new TokenIdentifier(tkString));
      return;
    }
    // token is not one of the recognizable ones
    throw new TokenizerException(String.format("Invalid token: %s", tkString));
  }

  private void scanDecimal(final Integer intPart) throws TokenizerException {

    String tkString = null;
    if (!tokenizer.hasMoreTokens()) {
      addToken(new TokenInteger(intPart));
    }
    else {
      tkString = tokenizer.nextToken();
      final IToken tk = TokenSymbol.getToken(tkString);
      // terminal INT or INT followed by something other than TK_DOT
      if (tk != TokenSymbol.TK_DOT || !tokenizer.hasMoreTokens()) {
        addToken(new TokenInteger(intPart));
        consume(tkString);
      }
      // INT followed by TK_DOT and some token
      else {
        tkString = tokenizer.nextToken();
        try {
          // INT followed by TK_DOT and a valid decimal part
          final Double num = Double.parseDouble(intPart.toString() + "." + tkString);
          addToken(new TokenDecimal(num));
          return;
        }
        catch (final NumberFormatException nfe) {
          // not an integer, proceed to process the token
        }
        // INT followed by TK_DOT and some token
        addToken(new TokenInteger(intPart));
        addToken(TokenSymbol.TK_DOT);
        consume(tkString);
      }
    }
  }

  private void scanGT_GE() throws TokenizerException {

    String tkString = null;
    if (!tokenizer.hasMoreTokens()) {
      addToken(TokenSymbol.TK_GT);
    }
    else {
      tkString = tokenizer.nextToken();
      final IToken tk = TokenSymbol.getToken(tkString);
      if (tk == TokenSymbol.TK_EQ) {
        addToken(TokenSymbol.TK_GE);
      }
      else {
        addToken(TokenSymbol.TK_GT);
        consume(tkString);
      }
    }
  }

  private void scanLT_LE_NE() throws TokenizerException {

    String tkString = null;
    if (!tokenizer.hasMoreTokens()) {
      addToken(TokenSymbol.TK_LT);
    }
    else {
      tkString = tokenizer.nextToken();
      final IToken tk = TokenSymbol.getToken(tkString);
      if (tk == TokenSymbol.TK_EQ) {
        addToken(TokenSymbol.TK_LE);
      }
      else if (tk == TokenSymbol.TK_GT) {
        addToken(TokenSymbol.TK_NE);
      }
      else {
        addToken(TokenSymbol.TK_LT);
        consume(tkString);
      }
    }
  }
}