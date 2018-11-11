package ua.nykyforov.integration.domain;

public class OrderItem {

	private final int orderNumber;
	private final PizzaType type;
	private final int cnt;

    @SuppressWarnings("WeakerAccess")
    public OrderItem(int orderNumber, PizzaType type, int cnt) {
		this.orderNumber = orderNumber;
		this.type = type;
		this.cnt = cnt;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public PizzaType getType() {
		return type;
	}

	public int getCnt() {
		return cnt;
	}

	@Override
	public String toString() {
		return String.valueOf(cnt) +
                " of " +
                type.name() + " pizza(s)";
	}
}
