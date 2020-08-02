package delivery.parameters;

public enum CardinalPoint {
    EAST("direcci\u00f3n Oriente"),
    NORTH("direcci\u00f3n Norte"),
    SOUTH("direcci\u00f3n Sur"),
    WEST("direcci\u00f3n Occidente");

    private final String orientationName;

    CardinalPoint(String orientationName) {
        this.orientationName = orientationName;
    }

    public String getOrientationName() {
        return orientationName;
    }
}
