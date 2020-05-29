package cal.advance;

import io.vertx.core.AbstractVerticle;
import io.vertx.serviceproxy.ServiceBinder;

public class CalculatorServiceVerticle extends AbstractVerticle{

	
	@Override
	public void start() {
		CalculatorService calculatorService=new CalculatorServiceImpl();
		// Register the handler on event bus with address (this address will be used to send messages to this verticle proxy.)
		new ServiceBinder(vertx)
			.setAddress("calculator.address")
			.register(CalculatorService.class, calculatorService);
	}
	
	@Override
	public void stop() {
		
	}
}
