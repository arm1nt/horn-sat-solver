public class Verum implements  IAtom {

    private final boolean TRUTH_VALUE = true;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        return true;
    }

    @Override
    public String toString() {
        return "⊤";
    }
}
