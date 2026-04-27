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

    // ===== CONVERSION METHOD =====
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        // Validation
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        // Step 1: convert to base (feet)
        double valueInFeet = source.toFeet(value);

        // Step 2: convert to target
        return target.fromFeet(valueInFeet);
    }

    // ===== MAIN =====
    public static void main(String[] args) {

        System.out.println(convert(1.0, LengthUnit.FEET, LengthUnit.INCH));     // 12
        System.out.println(convert(3.0, LengthUnit.YARD, LengthUnit.FEET));     // 9
        System.out.println(convert(36.0, LengthUnit.INCH, LengthUnit.YARD));    // 1
    }
}