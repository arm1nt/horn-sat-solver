import Exceptions.InvalidClauseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Parse the formula and pass it to the solver.
 */
public class Main {
    private static final Log LOG = LogFactory.getLog(Main.class);

    public static void main(String[] args) {

        //command line parsing
        if (args.length != 1) {
            System.err.println("Error: expected the path to the input file containing the formula as only argument");
            System.exit(1);
        }

        String path = args[0];
        Formula formula = parseFormula(path);
        System.out.println(Solver.solve(formula, System.out));
    }


    /**
     * Parses the given file, and returns a formula object, representing this parsed formula.
     *
     * @param path path to the file containing the file
     * @return formula representing the formula specified in the given file
     */
    private static Formula parseFormula(String path) {
        LOG.trace("parseFormula(" + path + ")");

        Formula formula = new Formula();
        String line = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(path)); ) {

            while ((line = reader.readLine()) != null) {

                Clause clause = parseClause(line);
                formula.addClause(clause);
            }
        } catch (InvalidClauseException e) {
            LOG.error("Clause '" + line + "' is invalid\n" + e.getMessage());
        } catch (FileNotFoundException e) {
            LOG.error("File " + path + " not found.");
            System.exit(1);
        } catch (IOException e) {
            LOG.error("There was an error reading from the provided file.");
            System.exit(1);
        }

        LOG.trace("Successfully parsed formula");
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
        LOG.trace("parseClause(" + line + ")");

        if (!line.contains("->")) {
            throw new InvalidClauseException("Clause does not contain an implication.");
        }

        String[] splitLine = line.split("->");
        String leftSide = splitLine[0].trim();
        String rightSide = splitLine[1].trim();

        //TODO: validity checks for left and right side.

        Clause clause = new Clause();

        //Parse the conjuncts
        String[] conjuncts = leftSide.split("&");
        for (String conjunct : conjuncts) {
            clause.addConjunct(symbolToAtom(conjunct));
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
     */
    private static IAtom symbolToAtom(String symbol) {
        symbol = symbol.trim();

        if (symbol.equals("1")) {
            return new Verum();
        } else if (symbol.equals("0")) {
            return new Falsum();
        } else {
            return new Atom(symbol);
        }
    }

}
