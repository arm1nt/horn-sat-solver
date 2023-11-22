import java.util.HashSet;
import java.util.Set;

/**
 * Gets a horn formula and determines whether this formula is unsatisfiable or not.
 * If the formula is satisfiable, a satisfying variable assignment is given.
 */
public class Solver {

    private static final String UNSAT = "UNSAT";
    private static final String SAT = "SAT";

    private static final Verum VERUM = new Verum();
    private static final Falsum FALSUM = new Falsum();



    //Maybe pass an outputstrean, so we can write the variable assignment to either a file or to stdout
    public static String solve(Formula formula) {

        //Set of all unique atoms that are contained in any of the clauses
        Set<IAtom> atoms = formula.containedAtoms();

        //All atoms that are in this set are assigned the value 'true', the rest is assigned the value 'false'
        Set<IAtom> marked = new HashSet<>();


        if (atoms.contains(VERUM)) {
            marked.add(VERUM); //Verum must always be assigned the truth value 'true'
        }


        Clause clause;
        //Retrieve a clause of the form P1 ∧ P2 ∧ ... ∧ Pn => P* that under the current value assignment evaluates to false.
        while ( (clause = formula.getCandidateClause(marked)) != null) {
            //mark P* to make the whole clause true
            marked.add(clause.getRightSide());
        }

        if (marked.contains(FALSUM)) {
            //If falsum its required to have the value 'true' in order for the formula to evaluate to true,
            //we know that this formula is unsatisfiable, since falsum is the constant for 'false'.
            return UNSAT;
        }

        //Print truth value assignment
        //TODO:

        return SAT;
    }
}
