import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Horn Formula.
 */
public class Formula {

    //Stores the horn clauses, that are implicitly connected via a logical 'and'
    private final Set<Clause> clauses = new HashSet<>();

    public Formula() {}

    /**
     * Add a new horn clause to the formula.
     *
     * @param clause Clause to be added. If this clause is already contained, it's quietly discarded.
     */
    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }

    /**
     * Gather all atoms (including Verum and Falsum) in this formula.
     *
     * @return Set of all atoms (including verum and falsum) in this formula
     */
    public Set<IAtom> containedAtoms() {
        Set<IAtom> containedAtoms = new HashSet<>();

        for (Clause clause : this.clauses) {
            containedAtoms.addAll(clause.containedAtoms());
        }

        return containedAtoms;
    }

    /**
     * Returns a clause of this formula that, under the current value assignment, evaluates to false.
     *
     * @param markedAtoms current value assignment
     * @return clause that evaluates to false under the given assignment, null if all clauses evaluate to true.
     */
    public Clause getCandidateClause(Set<IAtom> markedAtoms) {

        for (Clause clause : this.clauses) {
            if (clause.evaluatesToFalse(markedAtoms)) return clause;
        }

        return null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(Clause clause : this.clauses) {
            stringBuilder.append("(").append(clause.toString()).append(")").append(" ∧\n");
        }
        stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length());

        return stringBuilder.toString();
    }
}
