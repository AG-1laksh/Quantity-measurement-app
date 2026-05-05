import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    double EPS = 1e-6;

    @Test
    void testLengthEquality() {
        var q1 = new Quantity<>(1.0, LengthUnit.FEET);
        var q2 = new Quantity<>(12.0, LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testWeightEquality() {
        var q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testConversion() {
        var q = new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(12.0, q.convertTo(LengthUnit.INCH).getValue(), EPS);
    }

    @Test
    void testAddition() {
        var q1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        var q2 = new Quantity<>(1000.0, WeightUnit.GRAM);

        assertEquals(2.0, q1.add(q2).getValue(), EPS);
    }

    @Test
    void testCrossCategory() {
        var l = new Quantity<>(1.0, LengthUnit.FEET);
        var w = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(l.equals(w));
    }
}