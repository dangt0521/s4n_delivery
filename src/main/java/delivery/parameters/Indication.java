package delivery.parameters;

public enum Indication {
    GO_FORWARD("A"),
    TURN_LEFT("I"),
    TURN_RIGHT("D");

    private final String indication;

    Indication(String indication) {
        this.indication = indication;
    }

    public String getIndication() {
        return indication;
    }
}
