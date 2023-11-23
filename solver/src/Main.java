import Exceptions.InvalidAtomException;
import Exceptions.InvalidClauseException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse the formula and pass it to the solver.
 */
public class Main {

    public static void main(String[] args) {

        //argument parsing
        String path = null;
        PrintWriter out = null;

        if (args.length < 1) {
            usage();
        } else if(args.length == 1) {
            path = args[0];
            out = new PrintWriter(System.out);
        } else if (args.length == 3) {
            //Find at which index the -o option is, so we can differentiate between the input and the ouput file.
            List<String> arguments = List.of(args);
            List<Integer> possibleIndicesForInputFile = new ArrayList<>(List.of(0, 1, 2));

            int index = arguments.indexOf("-o");

            if (index == -1) {
                usage();
            }

            if (index+1 < arguments.size()) {
                possibleIndicesForInputFile.remove(Integer.valueOf(index));
                possibleIndicesForInputFile.remove(Integer.valueOf(index+1));

                try {
                    out = new PrintWriter(new FileWriter(arguments.get(index+1)));
                } catch (IOException e) {
                    System.err.println("Error: Unable to open / create file by the name: " + arguments.get(index+1));
                    System.exit(1);
                }
            } else {
                usage();
            }

            path = arguments.get(possibleIndicesForInputFile.get(0));
        } else {
            usage();
        }

        Formula formula = parseFormula(path);
        System.out.println(Solver.solve(formula, out));

        if (out != null) {
            out.flush();
            out.close();
        }
    }

    /**
     * Prints the correct usage for the program, and then terminates.
     */
    private static void usage() {
        System.err.println("Usage: Solver [-o outputFile] inputFile");
        System.exit(1);
    }


    /**
     * Parses the given file, and returns a formula object, representing this parsed formula.
     *
     * @param path path to the file containing the file
     * @return formula representing the formula specified in the given file
     */
    private static Formula parseFormula(String path) {

        Formula formula = new Formula();
        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            while ((line = reader.readLine()) != null) {

                Clause clause = parseClause(line);
                formula.addClause(clause);
            }
        } catch (InvalidClauseException e) {
            System.err.println("Clause '" + line + "' is invalid\n" + e.getMessage());
            System.exit(1);
        } catch (FileNotFoundException e) {
            System.err.println("File " + path + " not found.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("There was an error reading from the provided file.");
            System.exit(1);
        }

        return formula;
    }

    /**
     * Given a line representing a clause, return a Clause object representing this clause.
     *
     * @param line line representing a clause.
     * @return the clause object
     * @throws InvalidClauseException is thrown if the clause is invalid, e.g. missing implication.
     */
    private static Clause parseClause(String line) throws InvalidClauseException {

        if (!line.contains("->")) {
            throw new InvalidClauseException("Clause does not contain an implication.");
        }

        String[] splitLine = line.split("->");

        if (splitLine.length != 2) {
            throw new InvalidClauseException("Clause must have symbols on the left" +
                    " and the right side of the implication");
        }

        String leftSide = splitLine[0].trim();
        String rightSide = splitLine[1].trim();


        if (leftSide.length() < 1) {
            throw new InvalidClauseException("There needs to be at least one symbol" +
                    " on the left side of the implication!");
        }

        if (leftSide.startsWith("&")) {
            throw new InvalidClauseException("Clause must not start with '&'");
        }

        if (leftSide.endsWith("&")) {
            throw new InvalidClauseException("Clause mot not end with '&'");
        }


        if (rightSide.length() < 1) {
            throw new InvalidClauseException("There needs to be at least one symbol" +
                    " on the right side of the implication!");
        }

        if (rightSide.contains(" ")) {
            throw new InvalidClauseException("Right side of the implication must contain exactly one symbol!");
        }

        Clause clause = new Clause();

        //Parse the conjuncts
        String[] conjuncts = leftSide.split("&");

        try {
            for (String conjunct : conjuncts) {
                clause.addConjunct(symbolToAtom(conjunct));
            }
        } catch (InvalidAtomException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }


        //Set the atom on the right side of the implication
        clause.setRightSide(symbolToAtom(rightSide));

        return clause;
    }


    /**
     * Takes the symbol representation and returns its corresponding IAtom object subtype.
     *
     * @param symbol symbol to convert to IAtom object
     * @return corresponding IAtom object
     * @throws InvalidAtomException is thrown if the given symbol is invalid, e.g. contains whitespaces space, is blank
     */
    private static IAtom symbolToAtom(String symbol) throws InvalidAtomException {
        symbol = symbol.trim();

        if (symbol.length() == 0) { //e.g. a & b b & c
            throw new InvalidAtomException("Symbol '" + symbol + "' is invalid:\nSymbol must not be blank!");
        }

        if (symbol.contains(" ")) { //e.g. a & & b
            throw new InvalidAtomException("Symbol '" + symbol + "' is invalid:\nSymbol must not contain whitespaces");
        }

        if (symbol.equals("1")) {
            return new Verum();
        } else if (symbol.equals("0")) {
            return new Falsum();
        } else {
            return new Atom(symbol);
        }
    }

}
