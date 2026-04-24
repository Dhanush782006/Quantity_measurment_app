
 class Quantity_measurement_app {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0);

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

        System.out.println("testEquality_FeetToFeet_SameValue: " +
                compare(1.0, LengthUnit.FEET, 1.0, LengthUnit.FEET));

        System.out.println("testEquality_InchToInch_SameValue: " +
                compare(1.0, LengthUnit.INCH, 1.0, LengthUnit.INCH));

        System.out.println("testEquality_FeetToInch: " +
                compare(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH));

        System.out.println("testEquality_InchToFeet: " +
                compare(12.0, LengthUnit.INCH, 1.0, LengthUnit.FEET));

        System.out.println("testEquality_FeetToFeet_DifferentValue: " +
                !compare(1.0, LengthUnit.FEET, 2.0, LengthUnit.FEET));

        System.out.println("testEquality_InchToInch_DifferentValue: " +
                !compare(1.0, LengthUnit.INCH, 2.0, LengthUnit.INCH));

        QuantityLength q = new QuantityLength(1.0, LengthUnit.FEET);
        System.out.println("testEquality_SameReference: " + q.equals(q));

        System.out.println("testEquality_NullComparison: " +
                !q.equals(null));

        try {
            new QuantityLength(1.0, null);
            System.out.println("testEquality_NullUnit: false");
        } catch (IllegalArgumentException e) {
            System.out.println("testEquality_NullUnit: true");
        }

        try {
            new QuantityLength(Double.NaN, LengthUnit.FEET);
            System.out.println("testEquality_InvalidNumber: false");
        } catch (IllegalArgumentException e) {
            System.out.println("testEquality_InvalidNumber: true");
        }
    }

    public static void main(String[] args) {

        // Example Outputs
        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH) -> " +
                compare(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCH));

        System.out.println("Input: Quantity(1.0, INCH) and Quantity(1.0, INCH) -> " +
                compare(1.0, LengthUnit.INCH, 1.0, LengthUnit.INCH));

        System.out.println("\nRunning Tests:");
        runTests();
    }
}