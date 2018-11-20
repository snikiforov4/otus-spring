package ua.nykyforov.integration.domain;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class Order {

	private final int number;
	private final List<OrderItem> items;

	public Order(int number) {
		this.number = number;
        this.items = new ArrayList<>();
	}

	public void addItem(PizzaType pizzaType, int cnt) {
		this.items.add(new OrderItem(this.number, pizzaType, cnt));
	}

	public int getNumber() {
		return number;
	}

	public List<OrderItem> getItems() {
		return unmodifiableList(this.items);
	}

}
