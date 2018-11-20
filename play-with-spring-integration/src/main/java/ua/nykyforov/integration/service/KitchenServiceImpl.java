package ua.nykyforov.integration.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import ua.nykyforov.integration.domain.OrderItem;

public class KitchenServiceImpl implements KitchenService {
    private static final Logger logger = LoggerFactory.getLogger(KitchenService.class);

    @Override
    public OrderItem cookItem(Message<OrderItem> message) throws InterruptedException {
        OrderItem item = message.getPayload();
        logger.info("order#{} start cooking pizza {}",
                item.getOrderNumber(), item.getType().getName());
        Thread.sleep(item.getType().getCookingTime());
        logger.info("order#{} finished cooking pizza {}",
                item.getOrderNumber(), item.getType().getName());
        return item;
    }

}
