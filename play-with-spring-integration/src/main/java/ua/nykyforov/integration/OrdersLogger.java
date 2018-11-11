package ua.nykyforov.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ua.nykyforov.integration.domain.Order;
import ua.nykyforov.integration.domain.OrderItem;

import static java.util.stream.Collectors.joining;

@MessageEndpoint
public class OrdersLogger {
    private static final Logger logger = LoggerFactory.getLogger(OrdersLogger.class);

    @ServiceActivator
    public void log(Order order) {
        String orderItems = order.getItems().stream().map(OrderItem::toString).collect(joining(", "));
        logger.info("order#{} with items: {}", order.getNumber(), orderItems);
    }

}

