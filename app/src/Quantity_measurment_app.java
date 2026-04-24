class Quantity_measurement_app {

    enum Unit {
        FEET, INCH
    }

    static class Quantity {
        private final double value;
        private final Unit unit;

        public Quantity(double value, Unit unit) {
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toInches() {
            if (unit == Unit.FEET) {
                return value * 12;
            }
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;
            return Double.compare(this.toInches(), other.toInches()) == 0;
        }
    }

    // Helper methods
    public static boolean compareFeet(double a, double b) {
        return new Quantity(a, Unit.FEET)
                .equals(new Quantity(b, Unit.FEET));
    }

    public static boolean compareInches(double a, double b) {
        return new Quantity(a, Unit.INCH)
                .equals(new Quantity(b, Unit.INCH));
    }

    public static boolean compareFeetAndInches(double feet, double inches) {
        return new Quantity(feet, Unit.FEET)
                .equals(new Quantity(inches, Unit.INCH));
    }

    // Simple test simulation
    public static void runTests() {
        System.out.println("testEquality_SameValue: " +
                new Quantity(1.0, Unit.INCH).equals(new Quantity(1.0, Unit.INCH)));

        System.out.println("testEquality_DifferentValue: " +
                !new Quantity(1.0, Unit.INCH).equals(new Quantity(2.0, Unit.INCH)));

        System.out.println("testEquality_NullComparison: " +
                !new Quantity(1.0, Unit.INCH).equals(null));

        Quantity q = new Quantity(1.0, Unit.INCH);
        System.out.println("testEquality_SameReference: " + q.equals(q));

        System.out.println("testEquality_FeetToInch: " +
                new Quantity(1.0, Unit.FEET).equals(new Quantity(12.0, Unit.INCH)));

        try {
            new Quantity(Double.NaN, Unit.INCH);
            System.out.println("testEquality_NonNumericInput: false");
        } catch (IllegalArgumentException e) {
            System.out.println("testEquality_NonNumericInput: true");
        }
    }

    public static void main(String[] args) {

        System.out.println("Input: 1.0 inch and 1.0 inch -> " +
                compareInches(1.0, 1.0));

        System.out.println("Input: 1.0 ft and 1.0 ft -> " +
                compareFeet(1.0, 1.0));

        System.out.println("Input: 1.0 ft and 12.0 inch -> " +
                compareFeetAndInches(1.0, 12.0));

        System.out.println("\nRunning Tests:");
        runTests();
    }
}