public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBase() {
        return unit.convertToBaseUnit(value);
    }

    // ===== EQUALS =====
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;

        // prevent cross category
        if (this.unit.getClass() != other.unit.getClass()) return false;

        return Double.compare(this.toBase(), other.toBase()) == 0;
    }

    // ===== CONVERT =====
    public Quantity<U> convertTo(U target) {
        if (target == null) throw new IllegalArgumentException("Target unit null");

        double base = this.toBase();
        double result = target.convertFromBaseUnit(base);

        return new Quantity<>(Math.round(result * 100.0) / 100.0, target);
    }

    // ===== ADD =====
    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U target) {
        if (other == null || target == null)
            throw new IllegalArgumentException("Invalid input");

        double sum = this.toBase() + other.toBase();
        double result = target.convertFromBaseUnit(sum);

        return new Quantity<>(Math.round(result * 100.0) / 100.0, target);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(toBase());
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}