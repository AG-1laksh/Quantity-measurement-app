public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equal: " + w1.equals(w2));

        System.out.println("Convert: " + w1.convertTo(WeightUnit.GRAM));

        System.out.println("Add: " +
                QuantityWeight.add(w1, w2, WeightUnit.KILOGRAM));
    }
}