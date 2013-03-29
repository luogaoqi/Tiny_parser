import inference.IType.ITypeEquationResolver;
import inference.IType.ITypeEquationVisitor;
import inference.ITypeFactory;
import internal.inference.TypeFactory;
import internal.parser.ParseFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import parser.IParse.IFunction;
import parser.IParse.IParser;
import parser.IParseFactory;
import parser.ParseNodeVisitor;
import parser.ParserException;
import reference.NodePrintVisitor;
import reference.StatementPrintVisitor;

public class Driver {

  private static final String FILE_NAME = "C:/users/barat/workspace/tinyPL-mvc/src/test.ty";
  // private static final String FILE_NAME = "/home/demian/undef.ty";
  //private static final String FILE_NAME = "/home/demian/bool.ty";

  public static void main(final String args[]) throws ParserException {

    final Driver driver = new Driver();
    driver.run(FILE_NAME);
  }

  private final String getSourceCode(final String fileName) {

    try {
      final BufferedReader reader = new BufferedReader(new FileReader(fileName));
      final StringBuffer buffer = new StringBuffer("");
      String line;
      while (null != (line = reader.readLine())) {
        buffer.append(line).append("\n");
      }
      return buffer.toString();
    }
    catch (final FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (final IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  @SuppressWarnings("unused")
  private void printNodes(final IFunction function) {

    System.out
        .println("Parsing completed successfully. Running a node print visitor on the parse tree.\n");
    final ParseNodeVisitor printer = new NodePrintVisitor();
    function.accept(printer);
  }

  @SuppressWarnings("unused")
  private void printStatements(final IFunction function) {

    System.out.println("\nRunning a statement print visitor on the parse tree.\n");
    final StatementPrintVisitor printer = new StatementPrintVisitor();
    function.accept(printer);
    System.out.println(printer.getText());
  }

  private void printTypeEquations(final IFunction function, final ITypeFactory factory) {

    System.out.println("\nRunning the type equation visitor on the parse tree.\n");
    final ITypeEquationVisitor visitor = factory.newTypeEquationVisitor();
    function.accept(visitor);

    System.out.println("\nSolving the type equations.\n");
    final ITypeEquationResolver resolver = factory.newTypeEquationResolver(function, visitor);
    try {
      resolver.solveEquations();
    }
    catch (final IllegalStateException e) {
      System.out.println();
      System.out.println(e.getMessage());
    }
  }

  private void run(final String fileName) throws ParserException {

    final IParseFactory factory = new ParseFactory();
    final IParser parser = factory.newParser();
    final String source = getSourceCode(fileName);
    System.out.format("Calling the Tiny PL parser on source file %s:\n\n%s", fileName, source);
    final IFunction function = parser.parse(source);
    // printNodes(function);
    // printStatements(function);
    final ITypeFactory typeFactory = new TypeFactory();
    printTypeEquations(function, typeFactory);
    System.out.println("\nThank you for using Tiny PL.\n");
  }
}