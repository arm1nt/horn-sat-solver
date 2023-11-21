import java.util.HashSet;
import java.util.Set;

/**
 * Stores a set of Clauses that are implicitly connected via a logical and.
 */
public class Formula {

    //maybe don't use a set to retain the ordering
    //private final Set<Clause> clauses = new HashSet<>();
    private final Set<Clause> clauses = new HashSet<>();

    public Formula() {}

    //Add new clause to the formula
    //If this clause is already contained in the formula, its quietly discarded
    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }


    //Return all atoms that are contained in this formula (including verum and falsum)
    public Set<Atom> getContainedAtoms() {
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
