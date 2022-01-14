package vendingmachine.client;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.model.Coin;
import vendingmachine.model.Item;
public class Client {

	public static void main(String[] args) {

//		Item i3 = new Item("Lemon Juice", new BigDecimal(19), 5);
		Item i4 = new Item("Apple Juice", new BigDecimal(15), 1);
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(".\\src\\vm.ser");
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
//			oos.writeObject(i3);
			oos.writeObject(i4);
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
		}catch(IOException e) {
			System.out.println("IO exception in client");
		}finally {
			try {
				oos.close();
			} catch (IOException e) {
				System.out.println("IO exception while closing file");
			}
		}
		System.out.println("Generated ser file in directory src");

		ApplicationContext ac = //new AnnotationConfigApplicationContext(MyConfig.class);
				new ClassPathXmlApplicationContext("vm.xml");
		System.out.println("ac: "+ ac);
		System.out.println("Vending Machine is populated in the Static block in Dao layer through unmarshalling");
		VendingMachineController controller = ac.getBean("controller", VendingMachineController.class);
		
		System.out.println("\nDisplay");
		controller.displayStock();
		
		
		System.out.println("\nAdd item and display");
		Item i1 = new Item("Lemon Tea", new BigDecimal(9), 5);
		Item i2 = new Item("Apple Tea", new BigDecimal(5), 5);
		controller.add(i1);
		controller.add(i2);
		controller.displayStock();
		
		System.out.println("\nEnterCoin");
		controller.enterCoin(Coin.DOLLAR);
		controller.displayEntered();
		controller.enterCoin(Coin.HALFDOLLAR);
		controller.displayEntered();
		controller.enterCoin(Coin.QUARTER);
		controller.displayEntered();
		controller.enterCoin(Coin.DIME);
		controller.displayEntered();
		controller.enterCoin(Coin.NICKEL);
		controller.displayEntered();
		controller.enterCoin(Coin.PENNY);
		controller.displayEntered();
		for(int i = 0; i < 25; ++i)
			controller.enterCoin(Coin.DOLLAR);
		controller.displayEntered();
		
		System.out.println("\nSelect Item");
		controller.vend("Apple Ju");
		controller.vend("Apple Juice");
		controller.vend("Apple Juice");
		controller.vend("Lemon Tea");
		controller.displayStock();
		for(int i = 0; i < 25; ++i)
			controller.enterCoin(Coin.DOLLAR);
		controller.vend("Lemon Tea");
		controller.displayStock();
		
		
	}

}
