import java.util.Objects;

/**
 * Represents an atom.
 */
public class Atom implements IAtom {

    private final String symbol;

    public Atom(String symbol) {
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom atom = (Atom) o;
        return Objects.equals(symbol, atom.symbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}