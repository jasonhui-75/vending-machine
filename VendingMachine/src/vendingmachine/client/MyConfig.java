package vendingmachine.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import vendingmachine.controller.VendingMachineController;
import vendingmachine.dao.VendingMachineDao;
import vendingmachine.service.VendingMachineService;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@ComponentScan(basePackages = "vendingmachine")
//@AspectJAutoProxy
public class MyConfig {
	@Bean("controller")
	public VendingMachineController getController() {
		return new VendingMachineController();
	}
	@Bean("service")
	public VendingMachineService getService() {
		return new VendingMachineService();
	}
	@Bean("dao")
	public VendingMachineDao getDao() {
		return new VendingMachineDao();
	}
//	@Bean(name="d1")
//	@Primary
//	public Dvd getDvd1() {
//		return new Dvd("Free Guy",LocalDate.of(2021, Month.AUGUST, 13), 9, "Shawn Levy", "20th Century Studios", "Family friendly comedy");
//	}
//	@Bean(name="d2")
//	public Dvd getAddress2() {
//		return new Dvd("Spider-Man: No Way Home",LocalDate.of(2021, Month.DECEMBER, 15), 5, "Jon Watts", "Marvel Studio", "The worst spider-man movie");
//		
//	}
}
