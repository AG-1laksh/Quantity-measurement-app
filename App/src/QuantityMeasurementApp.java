public class QuantityMeasurementApp {

    public static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            this.value = value;
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public LengthUnit getUnit() {
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

            Quantity other = (Quantity) obj;

            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        // ===== CONVERT =====
        public Quantity convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double base = this.toBase();
            double result = target.convertFromBaseUnit(base);

            return new Quantity(result, target);
        }

        // ===== ADD (UC7 logic retained) =====
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            double sumBase = q1.toBase() + q2.toBase();
            double result = targetUnit.convertFromBaseUnit(sumBase);

            return new Quantity(result, targetUnit);
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(q1.convertTo(LengthUnit.INCH).getValue());
        System.out.println(Quantity.add(q1, q2, LengthUnit.FEET).getValue());
        System.out.println(q1.equals(q2));
    }
}