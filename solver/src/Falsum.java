import java.util.Objects;

/**
 * Represents the constant for 'false'
 */
public class Falsum implements IAtom {
    private final boolean TRUTH_VALUE = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(TRUTH_VALUE);
    }

    @Override
    public String toString() {
        return "⊥";
    }
}
