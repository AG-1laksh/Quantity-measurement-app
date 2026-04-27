public class QuantityMeasurementApp {

    // Inner class Feet
    public static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Override equals()
        @Override
        public boolean equals(Object obj) {

            // Same reference
            if (this == obj) {
                return true;
            }

            // Null or different type
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Type cast
            Feet other = (Feet) obj;

            // Compare double safely
            return Double.compare(this.value, other.value) == 0;
        }
    }

    // Main method (for manual testing)
    public static void main(String[] args) {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Equal: " + f1.equals(f2));
    }
}