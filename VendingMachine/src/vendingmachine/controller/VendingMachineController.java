package vendingmachine.controller;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import vendingmachine.exception.InsufficientFundsException;
import vendingmachine.exception.NoItemInventoryException;
import vendingmachine.exception.NoSuchItemException;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;
import vendingmachine.service.VendingMachineService;

@Controller
public class VendingMachineController {

	@Autowired
	private VendingMachineService service;

	public void display() {
		System.out.println(service.displayVendingMachine());
	}
	public void displayStock() {
		System.out.println(service.displayStock()); 
	}
	public void displayEntered() {
		System.out.println(service.displayEntered());
	}
	public void enterCoin(Coin coin){
		service.enterCoin(coin);
	}
	public void vend(String name) {
		try{
			System.out.println(service.vend(name));
		}catch(NoItemInventoryException e) {
			System.out.println(name+" is out of stock");
		}catch(NoSuchItemException e) {
			System.out.println(name+" is not a valid item, please select a valid item.");
		}catch(InsufficientFundsException e) {
			System.out.println("Insufficient funds, please enter more coins");
		}
	}
	public void add(Item item) {
		service.addItem(item);
	}
//	public void marshall(String name) {
//		service.marshall(name);
//	}
//
//	public void unmarshall(String name) {
//		service.unmarshall(name);
//	}
}
