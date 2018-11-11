package ua.nykyforov.integration.domain;

public class OrderItem {

	private int orderNumber;
	private PizzaType type;
	private int cnt;

    @SuppressWarnings("WeakerAccess")
    public OrderItem(int orderNumber, PizzaType type, int cnt) {
		this.orderNumber = orderNumber;
		this.type = type;
		this.cnt = cnt;
	}

	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public PizzaType getType() {
		return type;
	}

	public void setType(PizzaType type) {
		this.type = type;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return String.valueOf(cnt) +
                " delicious " +
                type.name();
	}
}
