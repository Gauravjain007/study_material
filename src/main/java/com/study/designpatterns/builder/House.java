package com.study.designpatterns.builder;

/**
 * Demonstrates the Builder Design Pattern by constructing different types of
 * House objects.
 * <p>
 * Key Points:
 * <ul>
 * <li>Always make the final product immutable by:
 * <ul>
 * <li>Making all fields final
 * <li>Providing only getters, no setters
 * <li>Using private constructors that take the builder as parameter
 * </ul>
 * <li>Validate in the Builder: Perform validation in the builder's build()
 * method or the individual building methods
 * <li>Use Method Chaining: Return this from builder methods to enable fluent
 * interface
 * <li>Clearly distinguish between required and optional parameters
 * </ul>
 */
public class House {
    private final String foundation;
    private final String structure;
    private final String roof;
    private final String interior;
    private final boolean hasGarage;
    private final boolean hasGarden;
    private final boolean hasPool;

    public String getFoundation() {
        return foundation;
    }

    public String getStructure() {
        return structure;
    }

    public String getRoof() {
        return roof;
    }

    public String getInterior() {
        return interior;
    }

    public boolean hasGarage() {
        return hasGarage;
    }

    public boolean hasGarden() {
        return hasGarden;
    }

    public boolean hasPool() {
        return hasPool;
    }

    @Override
    public String toString() {
        return "House{" +
                "foundation='" + foundation + '\'' +
                ", structure='" + structure + '\'' +
                ", roof='" + roof + '\'' +
                ", interior='" + interior + '\'' +
                ", hasGarage=" + hasGarage +
                ", hasGarden=" + hasGarden +
                ", hasPool=" + hasPool +
                '}';
    }

    /**
     * Private constructor to enforce object creation through the builder
     * 
     * @param builder
     */
    private House(HouseBuilder builder) {
        this.foundation = builder.foundation;
        this.structure = builder.structure;
        this.roof = builder.roof;
        this.interior = builder.interior;
        this.hasGarage = builder.hasGarage;
        this.hasGarden = builder.hasGarden;
        this.hasPool = builder.hasPool;
    }

    /**
     * Builder class for constructing {@link House} instances.
     */
    public static class HouseBuilder {
        private String foundation;
        private String structure;
        private String roof;
        private String interior = "Standard";
        private boolean hasGarage = false;
        private boolean hasGarden = false;
        private boolean hasPool = false;

        /**
         * Private constructor to enforce object creation through the builder
         */
        private HouseBuilder() {
        }

        /**
         * Private constructor to enforce object creation through the builder
         * Makes foundation, structure, and roof mandatory.
         * 
         * @param foundation
         * @param structure
         * @param roof
         */
        private HouseBuilder(String foundation, String structure, String roof) {
            this.foundation = foundation;
            this.structure = structure;
            this.roof = roof;
        }

        /**
         * Creates a new {@link HouseBuilder} instance.
         * 
         * @return a new {@link HouseBuilder} instance
         */
        public static HouseBuilder builder() {
            return new HouseBuilder();
        }

        /**
         * Creates a new {@link HouseBuilder} instance with the given foundation,
         * structure, and roof.
         * 
         * @param foundation the foundation type
         * @param structure  the structure type
         * @param roof       the roof type
         * @return a new {@link HouseBuilder} instance
         */
        public static HouseBuilder builder(String foundation, String structure, String roof) {
            return new HouseBuilder(foundation, structure, roof);
        }

        public HouseBuilder foundation(String foundation) {
            this.foundation = foundation;
            return this;
        }

        public HouseBuilder structure(String structure) {
            this.structure = structure;
            return this;
        }

        public HouseBuilder roof(String roof) {
            this.roof = roof;
            return this;
        }

        public HouseBuilder interior(String interior) {
            this.interior = interior;
            return this;
        }

        public HouseBuilder addGarage() {
            this.hasGarage = true;
            return this;
        }

        public HouseBuilder addGarden() {
            this.hasGarden = true;
            return this;
        }

        public HouseBuilder addPool() {
            this.hasPool = true;
            return this;
        }

        /**
         * Builds a new {@link House} instance using the builder configuration.
         * <p>
         * The following components are mandatory to build a house:
         * <ul>
         * <li>Foundation</li>
         * <li>Structure</li>
         * <li>Roof</li>
         * </ul>
         * If any of these components are missing, an {@link IllegalStateException} is
         * thrown.
         * </p>
         * 
         * @return a new {@link House} instance
         */
        public House build() {
            if (this.foundation == null || this.structure == null || this.roof == null) {
                System.out.println("Foundation, Structure and Roof are mandatory to build House");
                throw new IllegalStateException("Foundation, Structure and Roof are mandatory to build House");
            }
            return new House(this);
        }
    }
}
