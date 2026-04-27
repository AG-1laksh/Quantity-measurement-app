import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    // ===== FEET TESTS =====

    @Test
    void testFeetEquality_SameValue() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testFeetEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testFeetEquality_NullComparison() {
        QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(f.equals(null));
    }

    @Test
    void testFeetEquality_NonNumericInput() {
        QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(f.equals("1.0"));
    }

    @Test
    void testFeetEquality_SameReference() {
        QuantityMeasurementApp.Feet f = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(f.equals(f));
    }

    // ===== INCH TESTS =====

    @Test
    void testInchEquality_SameValue() {
        assertTrue(QuantityMeasurementApp.compareInch(1.0, 1.0));
    }

    @Test
    void testInchEquality_DifferentValue() {
        assertFalse(QuantityMeasurementApp.compareInch(1.0, 2.0));
    }

    @Test
    void testInchEquality_NullComparison() {
        QuantityMeasurementApp.Inch i = new QuantityMeasurementApp.Inch(1.0);
        assertFalse(i.equals(null));
    }

    @Test
    void testInchEquality_NonNumericInput() {
        QuantityMeasurementApp.Inch i = new QuantityMeasurementApp.Inch(1.0);
        assertFalse(i.equals("1.0"));
    }

    @Test
    void testInchEquality_SameReference() {
        QuantityMeasurementApp.Inch i = new QuantityMeasurementApp.Inch(1.0);
        assertTrue(i.equals(i));
    }
}