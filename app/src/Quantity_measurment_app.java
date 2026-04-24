public class Quantity_measurment_app {
        public static class Feet {
            private final double value;
            public Feet(double value) {
                this.value = value;
            }
            public double getValue() {
                return value;
            }
            @Override
            public boolean equals(Object obj) {

                if (this == obj) return true;

                if (obj == null || getClass() != obj.getClass()) return false;

                Feet other = (Feet) obj;

                return Double.compare(this.value, other.value) == 0;
            }
            @Override
            public int hashCode() {
                return Double.hashCode(value);
            }
        }
        public static void main(String[] args) {

            Feet a = new Feet(1.0);
            Feet b = new Feet(1.0);
            Feet c = new Feet(2.0);

            System.out.println("Test 1: Same Value (1.0 vs 1.0) -> " + a.equals(b)); // true
            System.out.println("Test 2: Different Value (1.0 vs 2.0) -> " + a.equals(c)); // false
            System.out.println("Test 3: Null Comparison -> " + a.equals(null)); // false
            System.out.println("Test 4: Non-Numeric Input -> " + a.equals("text")); // false
            System.out.println("Test 5: Same Reference -> " + a.equals(a)); // true
        }
    }
