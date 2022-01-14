package vendingmachine.dao;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
//import org.springframework.context.annotation.AspectJAutoProxyRegistrar;

@Aspect
@Component
//@AspectJAutoProxy
public class AuditDao {

	@Pointcut(value = "execution (* vendingmachine.controller.VendingMachineController.*(..))")
	public void dummy()
	{
	}
	
	@Around(value="dummy()")
	public Object logAround(ProceedingJoinPoint jp) throws Throwable
	{
		Object returnValue = null;
		System.out.println("Called "+jp.getSignature().getName() +" at "+LocalDateTime.now().toString());
		try {
			returnValue = jp.proceed();//Invoke business methods
//			System.out.println("After "+jp.getSignature());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			System.out.println(jp.getSignature()+" threw an exception");
			e.printStackTrace();
			throw e;
		}
		return returnValue;

	}
	


}
