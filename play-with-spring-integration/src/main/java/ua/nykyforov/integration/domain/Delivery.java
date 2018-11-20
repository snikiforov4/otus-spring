package ua.nykyforov.integration.domain;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class Delivery {

    private final int orderNumber;
    private final List<String> items;

    public static Delivery fromPreparedItems(List<OrderItem> preparedItems) {
        checkArgument(isNotEmpty(preparedItems), "preparedItems is empty");
        int orderNumber = preparedItems.get(0).getOrderNumber();
        List<String> items = preparedItems.stream()
                .map(item -> item.getType().getName())
                .collect(toList());
        return new Delivery(orderNumber, items);
    }

    private Delivery(int orderNumber, List<String> items) {
        this.orderNumber = orderNumber;
        this.items = items;
    }

    @Override
    public String toString() {
        return "Delivery#" + orderNumber + " with items=" + items;
    }
}
