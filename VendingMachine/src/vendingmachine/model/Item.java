package vendingmachine.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Item implements Serializable, Comparable<Item> {
	private String name;
	private BigDecimal price;
	private int amount;
	
	public Item(String name, BigDecimal price, int amount) {
		super();
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "Item [name=" + name + ", price=" + price + ", amount=" + amount + "]";
	}
	
	@Override
	public int compareTo(Item item) {
		int stringComp = this.getName().compareTo(item.getName());
		int bdComp = this.getPrice().compareTo(item.getPrice());;
		int intComp = this.getAmount() - item.getAmount();
		if(stringComp != 0)
			return stringComp;
		else if(bdComp != 0)
			return bdComp;
		else if(intComp != 0)
			return intComp;
		return 0;
	}
	
	
	
}
