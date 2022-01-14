package vendingmachine.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import vendingmachine.model.Item;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Item i1 = new Item("Milk tea", new BigDecimal(10), 10);
		Item i2 = new Item("Oolong tea", new BigDecimal("13.8"), 10);
		Queue<Item> q = new PriorityQueue<Item>();
		q.add(i1);
		q.add(i2);
		System.out.println(q);
		String file =".\\src\\test.ser";
		String file1 =".\\src\\test1.ser";
		marshall(q, file);
		q = new PriorityQueue<Item>();
		System.out.println(q);
		unmarshall(q, file);
		System.out.println("after deserial: "+q);
		Item temp = q.peek();
		System.out.println(temp);
		q.remove(temp);
		System.out.println("after remove: " +q);
		temp.setAmount(temp.getAmount()-1);
		q.add(temp);
		marshall(q, file);
		q = new PriorityQueue<Item>();
		System.out.println("before deserial:" + q);
		unmarshall(q,file);
		System.out.println("after deserial: "+q);
	}
	private static void marshall(Queue<Item> q, String name)
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
			for(Item i: q) {
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
	private static void unmarshall(Queue<Item> q, String name) {
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
						q.add((Item) obj);
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
}
