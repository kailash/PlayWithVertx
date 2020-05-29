package cal.rabbit;

import io.vertx.core.Vertx;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.rabbitmq.RabbitMQOptions;

public class RabbitMQ {

	private static RabbitMQClient instance;

	private RabbitMQ() {
	}

	public static RabbitMQClient getInstance(Vertx vertx) {
		if(instance==null) {
			instance=RabbitMQClient.create(vertx, rabbitConfig());
		}
		return instance;
	}

	private static RabbitMQOptions rabbitConfig() {
		RabbitMQOptions config = new RabbitMQOptions();
		config.setUser("IUDXDemoUser");
		config.setPassword("IUDXDemoUser@123");
		config.setHost("68.183.80.248");
		config.setPort(5672);
		config.setVirtualHost("/");
		return config;
	}

}
