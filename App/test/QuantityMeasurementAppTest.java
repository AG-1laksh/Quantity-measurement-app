import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    double EPS = 1e-6;

    // ===== EQUALITY =====

    @Test
    void testEquality_KgToGram() {
        var q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testEquality_KgToPound() {
        var q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(2.20462, WeightUnit.POUND);

        assertEquals(q1.toBase(), q2.toBase(), 1e-3);
    }

    @Test
    void testEquality_Different() {
        var q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

        assertFalse(q1.equals(q2));
    }

    // ===== CONVERSION =====

    @Test
    void testConversion_KgToGram() {
        var q = new QuantityWeight(1.0, WeightUnit.KILOGRAM);

        assertEquals(1000.0, q.convertTo(WeightUnit.GRAM).getValue(), EPS);
    }

    @Test
    void testConversion_PoundToKg() {
        var q = new QuantityWeight(2.20462, WeightUnit.POUND);

        assertEquals(1.0, q.convertTo(WeightUnit.KILOGRAM).getValue(), 1e-3);
    }

    // ===== ADDITION =====

    @Test
    void testAddition_SameUnit() {
        var r = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));

        assertEquals(3.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit() {
        var r = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(1000.0, WeightUnit.GRAM));

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_TargetUnit() {
        var r = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                WeightUnit.GRAM
        );

        assertEquals(2000.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Commutative() {
        var r1 = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                WeightUnit.KILOGRAM
        );

        var r2 = QuantityWeight.add(
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                WeightUnit.KILOGRAM
        );

        assertEquals(r1.getValue(), r2.getValue(), EPS);
    }

    // ===== EDGE =====

    @Test
    void testZero() {
        var q1 = new QuantityWeight(0.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(0.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testNegative() {
        var q1 = new QuantityWeight(-1.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(-1000.0, WeightUnit.GRAM);

        assertTrue(q1.equals(q2));
    }

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityWeight(1.0, null);
        });
    }
}