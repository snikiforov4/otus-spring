package ua.nykyforov.integration;

import ua.nykyforov.integration.domain.Order;
import ua.nykyforov.integration.domain.PizzaType;

import javax.annotation.Nullable;
import java.util.concurrent.ThreadLocalRandom;

public class OrdersQueue {

    @Nullable
    public Order nextOrder() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int orderNumber = random.nextInt();
        Order order = new Order(orderNumber);
        PizzaType[] values = PizzaType.values();
        order.addItem(values[random.nextInt(0, values.length)], 1);
        order.addItem(values[random.nextInt(0, values.length)], 1);
        return order;
    }

}
