import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 */
public class Clause {


    //Invariant: Clause must contain at least one element on the left side of the implication
    private final List<IAtom> leftSide = new ArrayList<>();
    private IAtom rightSide;

    public void addConjunct(IAtom atom) {

        this.leftSide.add(atom);
    }

    public void setRightSide(IAtom atom) {
        this.rightSide = atom;
    }


    @Override
    public boolean equals(Object o) {
        //they are equal if they contain the same atoms in the conjunct
        //(it does not matter how many times an atom occurs in the conjunction (i.e if a occurs twice))
        //and if they have the same atom on the right side of the implication

        //TODO

        return super.equals(o);
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
