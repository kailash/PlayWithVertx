package cal.advance;

import io.vertx.core.AbstractVerticle;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.serviceproxy.ServiceBinder;

public class CalculatorServiceVerticle extends AbstractVerticle {

	ServiceDiscovery discovery;
	Record record;

	@Override
	public void start() {
		CalculatorService calculatorService = new CalculatorServiceImpl();
		// Register the handler on event bus with address (this address will be used to
		// send messages to this verticle proxy.)
		new ServiceBinder(vertx).setAddress("calculator.address")
				.register(CalculatorService.class, calculatorService);

		//settings for publishing in discovery
		discovery = ServiceDiscovery.create(vertx);
		//create a record in discovery so that others can discover this service/proxy
		record = EventBusService.createRecord("calculator-service", "calculator.address",CalculatorService.class);

		//publish record 
		discovery.publish(record, resultHandler -> {
			if (resultHandler.succeeded()) {
				Record publishedRecord = resultHandler.result();
				System.out.println("record publish succedded");
				System.out.println("published record : "+publishedRecord.toJson());
			}
			else {
				System.out.println("record publish failed");
			}
		});

	}

	@Override
	public void stop() {
		discovery.unpublish(record.getRegistration(), resultHandler -> {
			if (resultHandler.succeeded()) {
				System.out.println("record unpublish failed.");
			}
			else {
				System.out.println("record unpublished failed.");
			}
		});
		discovery.close();
	}
}
