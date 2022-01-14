package vendingmachine.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.stereotype.Repository;

import vendingmachine.model.Coin;
import vendingmachine.model.Item;


@Repository
public class VendingMachineDao  {
	private static Queue<Item> itemQueue = new PriorityQueue<Item>();
	private static Set<Item> itemSet = new TreeSet<Item>();
	private static String serFile = ".\\src\\vm.ser";
	private static BigDecimal enteredCoin = new BigDecimal(0);
	static {
		unmarshall(serFile);
	}
	public BigDecimal getEnteredAmount() {
		return enteredCoin;
	}
	public Queue<Item> findAll(){
		return itemQueue;
	}

	public Item[] findByName(String name) {
		Item[] items = itemQueue.stream().filter(a -> a.getName().equals(name)).toArray(Item[]::new);
//		System.out.println("items in dao:" + Arrays.deepToString(items) + " "+name);
		return items;

	}
	public void enterCoin(Coin coin) {
		switch(coin)
		{
			case DOLLAR:
			{
				enteredCoin = enteredCoin.add(new BigDecimal(1));
				break;
			}
			case HALFDOLLAR:
			{
				enteredCoin =enteredCoin.add(new BigDecimal(".5"));
				break;
			}
			case QUARTER:
			{
				enteredCoin =enteredCoin.add(new BigDecimal(".25"));
				break;
			}
			case DIME:
			{
				enteredCoin =enteredCoin.add(new BigDecimal(".1"));
				break;
			}
			case NICKEL:
			{
				enteredCoin =enteredCoin.add(new BigDecimal(".05"));
				break;
			}
			case PENNY:
			{
				enteredCoin =enteredCoin.add(new BigDecimal(".01"));
				break;
			}
		}
	}
	public BigDecimal vend(Item item)
	{
		Item temp = item;
		itemQueue.remove(item);
		temp.setAmount(temp.getAmount()-1);
		itemQueue.add(temp);
		marshall(serFile);
		BigDecimal change = enteredCoin.subtract(item.getPrice());
		enteredCoin= new BigDecimal(0);
		return change;
	}
	public void addItem(Item item) {
		itemQueue.add(item);
		marshall(serFile);
	}

	
	private static void marshall(String name)
	{
//		System.out.println("marshalling in dao");
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
//			Item it = new Item("Milk", new BigDecimal("10.5"), 1);
			fos = new FileOutputStream(name);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
//			oos.writeObject(it);
			for(Item i: itemQueue) {
				oos.writeObject(i);
				
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch(IOException e) {
			System.out.println("IO exception");
		}finally {
			try {
				oos.close();
			} catch (IOException e) {
				System.out.println("IO exception while closing file");
			}
		}
	}
	private static void unmarshall(String name) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(name);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			while(true){
				Object obj = null;
				try{
					obj = ois.readObject();
					if (obj instanceof Item) {
						itemQueue.add((Item) obj);
					}
				}catch(IOException e) {
					break;
				}
			}
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch(IOException e) {
			System.out.println("IO exception");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				ois.close();
			} catch (IOException e) {
				System.out.println("IO exception while closing file");
			}
		}
	}
//	public List<Dvd> findAll() {
//		System.out.println("Inside findAll() of UserInMemoryDao");
//		return dvdList;
//	}


//	public User save(User user) {
//		System.out.println("Save() of UserInMemoryDao");
//		// user.setId(++userCount);
//		users.add(user);
//		return user;
//	}

//	public User deleteById(int id) {
//		System.out.println("deleteById of Dao");
//		Iterator<User> itr = users.iterator();
//		while (itr.hasNext()) {
//			User u = itr.next();
//			if (u.getId() == id) {
//
//				users.remove(u);
//				return u;
//			}
//		}
//		return null;
//	}

	

	

}
