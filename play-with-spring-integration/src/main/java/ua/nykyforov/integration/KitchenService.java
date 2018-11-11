package ua.nykyforov.integration;

import org.springframework.messaging.Message;
import ua.nykyforov.integration.domain.OrderItem;

public interface KitchenService {

    OrderItem cookItem(Message<OrderItem> message) throws InterruptedException;

}
