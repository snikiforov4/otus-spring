package ua.nykyforov.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ua.nykyforov.integration.domain.Delivery;

@MessageEndpoint
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    @ServiceActivator
    public void sendToClient(Delivery delivery) {
        logger.info("{} is sent to client", delivery);
    }

}

