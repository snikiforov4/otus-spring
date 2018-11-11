package ua.nykyforov.integration.domain;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.ImmutableList.toImmutableList;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

public class Delivery {

    private final int orderNumber;
    private final List<String> items;

    @SuppressWarnings("WeakerAccess")
    public Delivery(List<OrderItem> preparedItems) {
        checkArgument(isNotEmpty(preparedItems), "preparedItems is empty");
        this.orderNumber = preparedItems.get(0).getOrderNumber();
        this.items = preparedItems.stream().map(item -> item.getType().name()).collect(toImmutableList());
    }

    @Override
    public String toString() {
        return "Delivery#" + orderNumber + " with items=" + items;
    }
}
