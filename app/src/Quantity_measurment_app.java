 class Quantity_measurement_app {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),          // 1 inch = 1/12 feet
        YARD(3.0),                 // 1 yard = 3 feet
        CENTIMETER(0.393701 / 12.0); // 1 cm = 0.393701 inch → convert to feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }
    }

    public static boolean compare(double v1, LengthUnit u1, double v2, LengthUnit u2) {
        return new QuantityLength(v1, u1)
                .equals(new QuantityLength(v2, u2));
    }

    public static void runTests() {

        System.out.println("testEquality_YardToYard_SameValue: " +
                compare(1.0, LengthUnit.YARD, 1.0, LengthUnit.YARD));

        System.out.println("testEquality_YardToYard_DifferentValue: " +
                !compare(1.0, LengthUnit.YARD, 2.0, LengthUnit.YARD));

        System.out.println("testEquality_YardToFeet: " +
                compare(1.0, LengthUnit.YARD, 3.0, LengthUnit.FEET));

        System.out.println("testEquality_FeetToYard: " +
                compare(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARD));

        System.out.println("testEquality_YardToInches: " +
                compare(1.0, LengthUnit.YARD, 36.0, LengthUnit.INCH));

        System.out.println("testEquality_InchesToYard: " +
                compare(36.0, LengthUnit.INCH, 1.0, LengthUnit.YARD));

        System.out.println("testEquality_YardToFeet_NonEquivalent: " +
                !compare(1.0, LengthUnit.YARD, 2.0, LengthUnit.FEET));

        System.out.println("testEquality_CmToCm_SameValue: " +
                compare(2.0, LengthUnit.CENTIMETER, 2.0, LengthUnit.CENTIMETER));

        System.out.println("testEquality_CmToInch: " +
                compare(1.0, LengthUnit.CENTIMETER, 0.393701, LengthUnit.INCH));

        System.out.println("testEquality_CmToFeet_NonEquivalent: " +
                !compare(1.0, LengthUnit.CENTIMETER, 1.0, LengthUnit.FEET));

        boolean a = compare(1.0, LengthUnit.YARD, 3.0, LengthUnit.FEET);
        boolean b = compare(3.0, LengthUnit.FEET, 36.0, LengthUnit.INCH);
        boolean c = compare(1.0, LengthUnit.YARD, 36.0, LengthUnit.INCH);

        System.out.println("testEquality_TransitiveProperty: " + (a && b && c));

        System.out.println("testEquality_AllUnits_Complex: " +
                compare(2.0, LengthUnit.YARD, 72.0, LengthUnit.INCH));

        QuantityLength q = new QuantityLength(1.0, LengthUnit.YARD);
        System.out.println("testEquality_SameReference: " + q.equals(q));

        System.out.println("testEquality_NullComparison: " + !q.equals(null));

        try {
            new QuantityLength(1.0, null);
            System.out.println("testEquality_NullUnit: false");
        } catch (IllegalArgumentException e) {
            System.out.println("testEquality_NullUnit: true");
        }
    }

    public static void main(String[] args) {

        System.out.println("Input: Quantity(1.0, YARD) and Quantity(3.0, FEET) -> " +
                compare(1.0, LengthUnit.YARD, 3.0, LengthUnit.FEET));

        System.out.println("Input: Quantity(1.0, YARD) and Quantity(36.0, INCH) -> " +
                compare(1.0, LengthUnit.YARD, 36.0, LengthUnit.INCH));

        System.out.println("Input: Quantity(1.0, CM) and Quantity(0.393701, INCH) -> " +
                compare(1.0, LengthUnit.CENTIMETER, 0.393701, LengthUnit.INCH));

        System.out.println("\nRunning Tests:");
        runTests();
    }
}