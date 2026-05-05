import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    double EPS = 1e-6;

    // ===== EQUALITY =====

    @Test
    void testLengthEquality() {
        var q1 = new Quantity<>(1.0, LengthUnit.FEET);
        var q2 = new Quantity<>(12.0, LengthUnit.INCH);

        assertTrue(q1.equals(q2));
    }

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

        assertEquals(q1.convertTo(WeightUnit.KILOGRAM).getValue(),
                     q2.convertTo(WeightUnit.KILOGRAM).getValue(), 1e-3);
    }

    @Test
    void testEquality_Different() {
        var q1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        var q2 = new QuantityWeight(2.0, WeightUnit.KILOGRAM);

        assertFalse(q1.equals(q2));
    }

    // ===== CONVERSION =====

    @Test
    void testConversion() {
        var q = new Quantity<>(1.0, LengthUnit.FEET);

        assertEquals(12.0, q.convertTo(LengthUnit.INCH).getValue(), EPS);
    }

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

    // ===== NULL / INVALID =====

    @Test
    void testNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, null);
        });
    }

    @Test
    void testNullUnit_Weight() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityWeight(1.0, null);
        });
    }

    @Test
    void testInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(Double.NaN, LengthUnit.FEET);
        });
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

    // ===== ADDITION (Weight) =====

    @Test
    void testAddition_SameUnit_Weight() {
        var r = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(2.0, WeightUnit.KILOGRAM));

        assertEquals(3.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_Weight() {
        var r = new QuantityWeight(1.0, WeightUnit.KILOGRAM)
                .add(new QuantityWeight(1000.0, WeightUnit.GRAM));

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_TargetUnit_Weight() {
        var r = QuantityWeight.add(
                new QuantityWeight(1.0, WeightUnit.KILOGRAM),
                new QuantityWeight(1000.0, WeightUnit.GRAM),
                WeightUnit.GRAM
        );

        assertEquals(2000.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Commutative_Weight() {
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

    // ===== ADDITION (Length) =====

    @Test
    void testAddition_SameUnit_FeetPlusFeet() {
        var r = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(2.0, LengthUnit.FEET), LengthUnit.FEET);

        assertEquals(3.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_FeetPlusInches() {
        var r = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.FEET);

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_CrossUnit_InchPlusFeet() {
        var r = new Quantity<>(12.0, LengthUnit.INCH)
                .add(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCH);

        assertEquals(24.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_YardPlusFeet() {
        var r = new Quantity<>(1.0, LengthUnit.YARD)
                .add(new Quantity<>(3.0, LengthUnit.FEET), LengthUnit.YARD);

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_CmPlusInch() {
        var r = new Quantity<>(2.54, LengthUnit.CENTIMETER)
                .add(new Quantity<>(1.0, LengthUnit.INCH), LengthUnit.CENTIMETER);

        assertEquals(5.08, r.getValue(), 1e-2);
    }

    @Test
    void testAddition_Commutative_FeetTarget() {
        var r1 = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.FEET);

        var r2 = new Quantity<>(12.0, LengthUnit.INCH)
                .add(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.FEET);

        assertEquals(r1.getValue(), r2.getValue(), EPS);
    }

    @Test
    void testAddition_WithZero() {
        var r = new Quantity<>(5.0, LengthUnit.FEET)
                .add(new Quantity<>(0.0, LengthUnit.INCH), LengthUnit.FEET);

        assertEquals(5.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_NegativeValues() {
        var r = new Quantity<>(5.0, LengthUnit.FEET)
                .add(new Quantity<>(-2.0, LengthUnit.FEET), LengthUnit.FEET);

        assertEquals(3.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_NullOperand() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, LengthUnit.FEET).add(null, LengthUnit.FEET);
        });
    }

    @Test
    void testAddition_Target_Feet() {
        var r = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.FEET);

        assertEquals(2.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Target_Inch() {
        var r = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.INCH);

        assertEquals(24.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_Target_Yard() {
        var r = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.YARD);

        assertEquals(0.6667, r.getValue(), 1e-3);
    }

    @Test
    void testAddition_Target_Centimeter() {
        var r = new Quantity<>(1.0, LengthUnit.INCH)
                .add(new Quantity<>(1.0, LengthUnit.INCH), LengthUnit.CENTIMETER);

        assertEquals(5.08, r.getValue(), 1e-2);
    }

    @Test
    void testAddition_Commutative() {
        var r1 = new Quantity<>(1.0, LengthUnit.FEET)
                .add(new Quantity<>(12.0, LengthUnit.INCH), LengthUnit.YARD);

        var r2 = new Quantity<>(12.0, LengthUnit.INCH)
                .add(new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.YARD);

        assertEquals(r1.getValue(), r2.getValue(), EPS);
    }

    @Test
    void testAddition_WithZero_Yard() {
        var r = new Quantity<>(5.0, LengthUnit.FEET)
                .add(new Quantity<>(0.0, LengthUnit.INCH), LengthUnit.YARD);

        assertEquals(1.6667, r.getValue(), 1e-3);
    }

    @Test
    void testAddition_Negative() {
        var r = new Quantity<>(5.0, LengthUnit.FEET)
                .add(new Quantity<>(-2.0, LengthUnit.FEET), LengthUnit.INCH);

        assertEquals(36.0, r.getValue(), EPS);
    }

    @Test
    void testAddition_NullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Quantity<>(1.0, LengthUnit.FEET).add(new Quantity<>(12.0, LengthUnit.INCH), null);
        });
    }
}
