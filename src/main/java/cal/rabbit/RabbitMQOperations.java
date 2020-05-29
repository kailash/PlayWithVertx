package cal.rabbit;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.rabbitmq.RabbitMQClient;

public class RabbitMQOperations {
	
	RabbitMQClient rabbitMQClient;
	Vertx vertx;
	
	public RabbitMQOperations(RabbitMQClient rabbitMQClient,Vertx vertx){
		this.rabbitMQClient=rabbitMQClient;
		this.vertx=vertx;
		rabbitMQClient.start(resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("succedded");
			}else {
				System.out.println("failed");
			}
		});
	}
	
	public void createExchange(String exchangeName) {
		rabbitMQClient.exchangeDeclare("kailash.exchange", "topic", true, false, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("created successfully");
			}else {
				System.out.println("failed");
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	
	public void createQueue(String queueName) {
		rabbitMQClient.queueDeclare("kailash.queue", true, true, false, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("queue created successfully");
			}else {
				System.out.println("failed");
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	public void bindQueue2Exchange(String queueName,String exchangeName) {
		rabbitMQClient.queueBind("kailash.queue", "kailash.exchange", "kailash.routingKey.calculator", resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("bind created successfully");
			}else {
				System.out.println("failed");
				resultHandler.cause().printStackTrace();
			}
		});
	}
	
	public void publish2RabbitMQ(String request) {
		JsonObject json=new JsonObject();
		json.put("body", request);
		
		System.out.println(json);
		rabbitMQClient.basicPublish("kailash.exchange", "kailash.routingKey.calculator", json, resultHandler->{
			if(resultHandler.succeeded()) {
				System.out.println("message published to queue");
			}else {
				System.out.println("failed");
				resultHandler.cause().printStackTrace();
			}
		});
	}
}
