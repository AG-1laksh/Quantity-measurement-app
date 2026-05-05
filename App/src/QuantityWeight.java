public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
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

        QuantityWeight other = (QuantityWeight) obj;

        return Double.compare(this.toBase(), other.toBase()) == 0;
    }

    // ===== CONVERT =====
    public QuantityWeight convertTo(WeightUnit target) {
        if (target == null) throw new IllegalArgumentException("Target unit cannot be null");

        double base = this.toBase();
        double result = target.convertFromBaseUnit(base);

        return new QuantityWeight(result, target);
    }

    // ===== ADD (default unit = first) =====
    public QuantityWeight add(QuantityWeight other) {
        return add(this, other, this.unit);
    }

    // ===== ADD (target unit) =====
    public static QuantityWeight add(QuantityWeight q1, QuantityWeight q2, WeightUnit target) {

        if (q1 == null || q2 == null || target == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double sum = q1.toBase() + q2.toBase();
        double result = target.convertFromBaseUnit(sum);

        return new QuantityWeight(result, target);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}