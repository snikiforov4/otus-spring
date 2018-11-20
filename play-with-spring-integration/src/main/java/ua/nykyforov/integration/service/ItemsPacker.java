package ua.nykyforov.integration.service;

import ua.nykyforov.integration.domain.Delivery;
import ua.nykyforov.integration.domain.OrderItem;

import java.util.List;

public class ItemsPacker {

	public Delivery prepareDelivery(List<OrderItem> preparedItems) {
		return Delivery.fromPreparedItems(preparedItems);
	}

}
