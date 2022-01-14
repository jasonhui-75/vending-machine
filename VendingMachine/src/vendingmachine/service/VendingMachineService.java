package vendingmachine.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vendingmachine.dao.VendingMachineDao;
import vendingmachine.exception.InsufficientFundsException;
import vendingmachine.exception.NoItemInventoryException;
import vendingmachine.exception.NoSuchItemException;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;

@Service
public class VendingMachineService {
	@Autowired
	private VendingMachineDao dao;
	
	public String displayVendingMachine(){
		Queue<Item> q = dao.findAll();
		String result = "This vending machine currently has "+q.size()+" items"+
				((q.size() == 0)? " with "+dao.getEnteredAmount() +"$ entered": " with "+dao.getEnteredAmount() +"$ entered[\n");
		int count = 1;
		for(Item i : q)
		{
			if(i.getAmount()>0)
				result+= "Slot "+count+++": "+i.getName()+": "+i.getPrice().toString()+",\n";
		}
		result+=((q.size() == 0)? "": "]");
 		return result;
	}
	public String displayStock(){
		Queue<Item> q = dao.findAll();
		String result = "This vending machine currently has "+q.size()+" items"+
				((q.size() == 0)? " with "+dao.getEnteredAmount() +"$ entered": " with "+dao.getEnteredAmount() +"$ entered[\n");
		int count = 1;
		for(Item i : q)
		{
			if(i.getAmount()>0)
				result+= "Slot "+count+++": "+i.getName()+": "+i.getPrice().toString()+"$ Amount: "+i.getAmount()+",\n";
		}
		result+=((q.size() == 0)? "": "]");
 		return result;
	}
	public String displayEntered() {
		return "You have entered "+dao.getEnteredAmount().toString()+"$";
	}
	public void enterCoin(Coin coin) {
		dao.enterCoin(coin);
	}
	public String vend(String name) {
		Item[] items = dao.findByName(name);
//		System.out.println("vend: "+ Arrays.toString(items) +" name: "+name);
		if(items == null || items.length == 0)
			throw new NoSuchItemException();
//		boolean noInventory = true;
		for(Item i: items) {
			if(i.getAmount()>0)
			{
//				noInventory = false;
				if(i.getPrice().compareTo(dao.getEnteredAmount()) >0) {
					throw new InsufficientFundsException();
				}
				else {
					int[] result = minNumCoins(dao.vend(i));
					return String.format("%s purchased sucessfully!\nReturned %d dollar(s), %d half dollar(s), %d quarter(s), %d dime(s), %d nickel(s), %d "+
						(result[5] ==0 ?"penny":"pennies"), name, result[0], result[1], result[2], result[3],result[4], result[5] );
				}
				
			}
		}
		throw new NoItemInventoryException();
	}
	private int[] minNumCoins(BigDecimal change) {
		BigDecimal[] coins = new BigDecimal[] {new BigDecimal(1), new BigDecimal(".5"), new BigDecimal(".25"),
				new BigDecimal(".1"), new BigDecimal(".05"), new BigDecimal(".01")};
		
		int[] result = new int[6];
		
		int index =0;
//		System.out.println("change before while "+change.toString() );
		while(change.compareTo(new BigDecimal(0)) > 0) {
			if(change.compareTo(coins[index])>=0) {
				change = change.subtract(coins[index]);
//				System.out.println("change: "+change.toString());
				result[index]+=1;
			}
			else
			{
				++index;
			}
		}
			
//		System.out.println(Arrays.toString(result));
		return result; 
		
	}
	public void addItem(Item item) {
		dao.addItem(item);
	}
//	public void marshall(String name)
//	{
//		dao.marshall(name);
//	}
//	public void unmarshall(String name)
//	{
//		dao.unmarshall(name);
//	}
}
