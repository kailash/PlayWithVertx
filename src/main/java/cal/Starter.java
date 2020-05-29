package cal;

import cal.advance.APIServerVerticle;
import cal.advance.CalculatorServiceVerticle;
import cal.rabbit.RabbitMQ;
import cal.rabbit.RabbitMQOperations;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;

public class Starter extends AbstractVerticle {
	
	public static void main(String[] args) {
		Vertx vertx=Vertx.vertx();
		//vertx.deployVerticle(APIVerticle.class.getName());
		//vertx.deployVerticle(CalculatorVerticle.class.getName());
		
		
		RabbitMQClient rabbitMQClient=RabbitMQ.getInstance(vertx);
		rabbitMQClient.start(handler->{
			System.out.println("client started");
			RabbitMQOperations rabbitOperation=new RabbitMQOperations(rabbitMQClient,vertx);
			
			rabbitOperation.createExchange("kailash.exchange");
			rabbitOperation.createQueue("kailash.queue");
			rabbitOperation.bindQueue2Exchange("kailash.queue", "kailash.exchange");
		});
		
		
		
		vertx.deployVerticle(APIServerVerticle.class.getName());
		vertx.deployVerticle(CalculatorServiceVerticle.class.getName());
	}

}
