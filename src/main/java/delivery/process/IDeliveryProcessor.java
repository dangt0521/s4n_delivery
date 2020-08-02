package delivery.process;

import delivery.parameters.Drone;

import java.util.List;

public interface IDeliveryProcessor {

    List<Drone> deliverMeal(List<String> deliveryIndications, Integer droneId, Integer displacementCapacity);
}
