import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a horn clause.
 */
public class Clause {

    private final List<IAtom> leftSide = new ArrayList<>(); //Invariant: always contains at least one atom.
    private IAtom rightSide; //Invariant: Can never be null

    /**
     * Add new conjunct on the left side of the implication to the clause.
     *
     * @param atom atom to be added to the conjunction.
     */
    public void addConjunct(IAtom atom) {
        this.leftSide.add(atom);
    }

    /**
     * Set the right side of the implication.
     *
     * @param atom atom to be added
     */
    public void setRightSide(IAtom atom) {
        this.rightSide = atom;
    }

    /**
     * Retrieves the right side of the implication.
     *
     * @return atom on the right side of the implication.
     */
    public IAtom getRightSide() {
        return this.rightSide;
    }

    /**
     * Retrieve a set of all unique atoms contained in this clause.
     *
     * @return set of all contained atoms.
     */
    public Set<IAtom> containedAtoms() {
        Set<IAtom> atoms = new HashSet<>(this.leftSide);
        atoms.add(this.rightSide);

        return atoms;
    }

    /**
     * Checks whether the clause evaluates to false under the given variable assignment.
     *
     * @param marked variable assignment. If an atom is contained in this map, it is assumed to be true, otherwise it's
     *               assumed to be false.
     * @return 'true' if the clause evaluates to false under the given assignment, 'false' otherwise.
     */
    public boolean evaluatesToFalse(Set<IAtom> marked) {
        //check if all atoms in the 'marked' set are contained on the left side of the implication
        for (IAtom atom : this.leftSide) {
            if (!marked.contains(atom)) return false;
        }

        return !marked.contains(this.rightSide);
    }

    @Override
    public boolean equals(Object o) {
        //Two clauses are equal if they have the same atoms in the conjunct (it does not matter if one clause
        // contains e.g. contains atom 'a' two times and the other only contains it one time).
        //It's also necessary that they have the same symbol on the right side of the implication.
        if (o == this) return true;
        if (o == null || o.getClass() != this.getClass()) return false;

        Clause otherClause = (Clause) o;

        if (!this.rightSide.equals(otherClause.rightSide)) return false;

        Set<IAtom> clauseAtoms = new HashSet<>(this.leftSide);
        Set<IAtom> otherClauseAtoms = new HashSet<>(otherClause.leftSide);

        for (IAtom atom : clauseAtoms) {
            if (!otherClauseAtoms.contains(atom)) return false;
            otherClauseAtoms.remove(atom);
        }

        return otherClauseAtoms.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (IAtom atom : this.leftSide) {
            stringBuilder.append(atom.toString()).append("∧");
        }

        stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length());
        stringBuilder.append(" -> ").append(this.rightSide);

        return stringBuilder.toString();
    }
}
