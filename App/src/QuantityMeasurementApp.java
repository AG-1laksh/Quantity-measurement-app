public class QuantityMeasurementApp {

    // ===== ENUM =====
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double factor) {
            this.toFeetFactor = factor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeetFactor;
        }
    }

    // ===== QUANTITY CLASS =====
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

        private double toFeet() {
            return unit.toFeet(value);
        }

        // ===== UC7 ADD METHOD (TARGET UNIT) =====
        public static Quantity add(Quantity q1, Quantity q2, LengthUnit targetUnit) {

            if (q1 == null || q2 == null || targetUnit == null) {
                throw new IllegalArgumentException("Invalid input");
            }

            // Convert both to base unit (feet)
            double sumInFeet = q1.toFeet() + q2.toFeet();

            // Convert to target unit
            double result = targetUnit.fromFeet(sumInFeet);

            return new Quantity(result, targetUnit);
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        System.out.println(Quantity.add(q1, q2, LengthUnit.FEET).getValue());
        System.out.println(Quantity.add(q1, q2, LengthUnit.INCH).getValue());
        System.out.println(Quantity.add(q1, q2, LengthUnit.YARD).getValue());
    }
}