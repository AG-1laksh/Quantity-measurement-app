import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    @Test
    void testAddition_Target_Feet() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.FEET
        );

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Target_Inch() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.INCH
        );

        assertEquals(24.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Target_Yard() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD
        );

        assertEquals(0.6667, r.getValue(), 1e-3);
    }

    @Test
    void testAddition_Target_Centimeter() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.INCH),
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.CENTIMETER
        );

        assertEquals(5.08, r.getValue(), 1e-2);
    }

    @Test
    void testAddition_Commutative() {
        var r1 = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD
        );

        var r2 = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                QuantityMeasurementApp.LengthUnit.YARD
        );

        assertEquals(r1.getValue(), r2.getValue(), EPS);
    }

    @Test
    void testAddition_WithZero() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(5.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(0.0, QuantityMeasurementApp.LengthUnit.INCH),
                QuantityMeasurementApp.LengthUnit.YARD
        );

        assertEquals(1.6667, r.getValue(), 1e-3);
    }

    @Test
    void testAddition_Negative() {
        var r = QuantityMeasurementApp.Quantity.add(
                new QuantityMeasurementApp.Quantity(5.0, QuantityMeasurementApp.LengthUnit.FEET),
                new QuantityMeasurementApp.Quantity(-2.0, QuantityMeasurementApp.LengthUnit.FEET),
                QuantityMeasurementApp.LengthUnit.INCH
        );

        assertEquals(36.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_NullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            QuantityMeasurementApp.Quantity.add(
                    new QuantityMeasurementApp.Quantity(1.0, QuantityMeasurementApp.LengthUnit.FEET),
                    new QuantityMeasurementApp.Quantity(12.0, QuantityMeasurementApp.LengthUnit.INCH),
                    null
            );
        });
    }
}