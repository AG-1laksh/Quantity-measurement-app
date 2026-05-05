import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testConvertToBase_InchToFeet() {
        assertEquals(1.0, LengthUnit.INCH.convertToBaseUnit(12.0), EPS);
    }

    @Test
    void testConvertFromBase_FeetToInch() {
        assertEquals(12.0, LengthUnit.INCH.convertFromBaseUnit(1.0), EPS);
    }

    @Test
    void testEquality_Refactored() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.Quantity(12.0, LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testConvertTo() {
        var q = new QuantityMeasurementApp.Quantity(1.0, LengthUnit.FEET);
        var result = q.convertTo(LengthUnit.INCH);

        assertEquals(12.0, result.getValue(), EPS);
    }

    @Test
    void testAddition() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.Quantity(12.0, LengthUnit.INCH);

        var result = QuantityMeasurementApp.Quantity.add(q1, q2, LengthUnit.FEET);

        assertEquals(2.0, result.getValue(), EPS);
    }

    @Test
    void testAddition_TargetUnit() {
        var q1 = new QuantityMeasurementApp.Quantity(1.0, LengthUnit.FEET);
        var q2 = new QuantityMeasurementApp.Quantity(12.0, LengthUnit.INCH);

        var result = QuantityMeasurementApp.Quantity.add(q1, q2, LengthUnit.YARD);

        assertEquals(0.6667, result.getValue(), 1e-3);
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.Quantity(1.0, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityMeasurementApp.Quantity(Double.NaN, LengthUnit.FEET);
        });
    }
}