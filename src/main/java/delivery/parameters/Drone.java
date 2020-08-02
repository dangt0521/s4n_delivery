package delivery.parameters;

import java.util.Objects;

public class Drone {

    private Integer droneId;
    private Integer positionX;
    private Integer positionY;
    private CardinalPoint orientation;

    public Drone(Integer droneId, Integer positionX, Integer positionY, CardinalPoint orientation) {
        this.droneId = droneId;
        this.positionX = positionX;
        this.positionY = positionY;
        this.orientation = orientation;
    }

    public Integer getDroneId() {
        return droneId;
    }

    public void setDroneId(Integer droneId) {
        this.droneId = droneId;
    }

    public Integer getPositionX() {
        return positionX;
    }

    public void setPositionX(Integer positionX) {
        this.positionX = positionX;
    }

    public Integer getPositionY() {
        return positionY;
    }

    public void setPositionY(Integer positionY) {
        this.positionY = positionY;
    }

    public CardinalPoint getOrientation() {
        return orientation;
    }

    public void setOrientation(CardinalPoint orientation) {
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Drone)) return false;
        Drone drone = (Drone) o;
        return Objects.equals(droneId, drone.droneId) &&
                Objects.equals(positionX, drone.positionX) &&
                Objects.equals(positionY, drone.positionY) &&
                orientation == drone.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(droneId, positionX, positionY, orientation);
    }
}
