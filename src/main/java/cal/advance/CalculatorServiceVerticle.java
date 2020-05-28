package cal.advance;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class CalculatorServiceVerticle extends AbstractVerticle{

	
	@Override
	public void start() {
		CalculatorService calculatorService=new CalculatorServiceImpl();
		
		new ServiceBinder(vertx)
			.setAddress("calculator.address")
			.register(CalculatorService.class, calculatorService);
	}
	
	@Override
	public void stop() {
		
	}
}
