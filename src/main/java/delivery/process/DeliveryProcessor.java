package delivery.process;

import delivery.parameters.CardinalPoint;
import delivery.parameters.Drone;
import delivery.parameters.Indication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DeliveryConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DeliveryProcessor implements IDeliveryProcessor {

    private final Logger logger = LogManager.getLogger(DeliveryProcessor.class);

    @Override
    public List<Drone> deliverMeal(List<String> deliveryIndications, Integer droneId, Integer displacementCapacity) {
        Drone drone = new Drone(droneId,
                DeliveryConstants.INITIAL_X_POSITION,
                DeliveryConstants.INITIAL_Y_POSITION,
                DeliveryConstants.INITIAL_ORIENTATION);

        List<Drone> drones = new ArrayList<>();

        for (String indications : deliveryIndications) {
            char[] indication = indications.toCharArray();

            if (!validateIndication(indication)) {
                logger.error("Indication {} for drone {} not valid. Please check.", indications, drone.getDroneId());
            } else {
                drone = getDroneNewPosition(drone, indication);

                if (Math.abs(drone.getPositionX()) > displacementCapacity || Math.abs(drone.getPositionY()) > displacementCapacity) {
                    logger.error("Order {} out of range for drone {}", indications, drone.getDroneId());
                } else {
                    drones.add(drone);
                }
            }
        }

        return drones;
    }

    private Drone getDroneNewPosition(Drone drone, char[] indications) {
        Integer xPosition = drone.getPositionX();
        Integer yPosition = drone.getPositionY();
        CardinalPoint orientation = drone.getOrientation();

        for (char i : indications) {

            switch (orientation) {
                case EAST:
                    xPosition = String.valueOf(i).equals(Indication.GO_FORWARD.getIndication()) ? xPosition + 1 : xPosition;
                    orientation = String.valueOf(i).equals(Indication.TURN_LEFT.getIndication()) ? CardinalPoint.NORTH
                            : String.valueOf(i).equals(Indication.TURN_RIGHT.getIndication()) ? CardinalPoint.SOUTH : orientation;
                    break;
                case NORTH:
                    yPosition = String.valueOf(i).equals(Indication.GO_FORWARD.getIndication()) ? yPosition + 1 : yPosition;
                    orientation = String.valueOf(i).equals(Indication.TURN_LEFT.getIndication()) ? CardinalPoint.WEST
                            : String.valueOf(i).equals(Indication.TURN_RIGHT.getIndication()) ? CardinalPoint.EAST : orientation;
                    break;
                case SOUTH:
                    yPosition = String.valueOf(i).equals(Indication.GO_FORWARD.getIndication()) ? yPosition - 1 : yPosition;
                    orientation = String.valueOf(i).equals(Indication.TURN_LEFT.getIndication()) ? CardinalPoint.EAST
                            : String.valueOf(i).equals(Indication.TURN_RIGHT.getIndication()) ? CardinalPoint.WEST : orientation;
                    break;
                case WEST:
                    xPosition = String.valueOf(i).equals(Indication.GO_FORWARD.getIndication()) ? xPosition - 1 : xPosition;
                    orientation = String.valueOf(i).equals(Indication.TURN_LEFT.getIndication()) ? CardinalPoint.SOUTH
                            : String.valueOf(i).equals(Indication.TURN_RIGHT.getIndication()) ? CardinalPoint.NORTH : orientation;
                    break;
            }
        }

        return new Drone(drone.getDroneId(), xPosition, yPosition, orientation);
    }

    private boolean validateIndication(char[] indication) {

        for (char c : indication) {
            if (Stream.of(Indication.GO_FORWARD.getIndication(), Indication.TURN_LEFT.getIndication(), Indication.TURN_RIGHT.getIndication())
                    .noneMatch(String.valueOf(c)::contains)) {
                return false;
            }
        }
        return true;
    }

}
