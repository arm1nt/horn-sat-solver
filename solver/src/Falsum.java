public class Falsum implements IAtom {

    private final boolean TRUTH_VALUE = false;

    @Override
    public String toString() {
        return "⊥";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
