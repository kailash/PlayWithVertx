package cal.advance;

import cal.CustomResponse;
import cal.rabbit.RabbitMQ;
import cal.rabbit.RabbitMQOperations;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.rabbitmq.RabbitMQClient;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.EventBusService;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class APIServerVerticle extends AbstractVerticle {
	private ClusterManager manager;
	private VertxOptions options;
	private HttpServer server;
	private CalculatorService calculatorService;
	private RabbitMQOperations rabbitOperation;
	private RabbitMQClient rabbitMQClient;
	private ServiceDiscovery discovery;

	@Override
	public void start() {

		manager = new HazelcastClusterManager();
		options = new VertxOptions().setClusterManager(manager);

		Vertx.clusteredVertx(options, resultHandler -> {
			if (resultHandler.succeeded()) {
				Router router = Router.router(vertx);
				router.get("/api/add").handler(this::add);
				router.get("/api/subtract").handler(this::subtract);
				router.get("/api/multiply").handler(this::multiply);
				router.get("/api/divide").handler(this::divide);

				server = vertx.createHttpServer();
				server.requestHandler(router::accept).listen(8080);

				discovery = ServiceDiscovery.create(vertx);
				EventBusService.getProxy(discovery, CalculatorService.class, ar -> {
					if (ar.succeeded()) {
						System.out.println("discovered calculator service");
						calculatorService = ar.result();
					}
					else {
						System.out.println("proxy discovery failed.");
					}
				});

				rabbitMQClient = RabbitMQ.getInstance(vertx);
				rabbitOperation = new RabbitMQOperations(rabbitMQClient, vertx);
			}

		});

	}

	public void add(RoutingContext routingContext) {
		System.out.println("SP:add called");
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.add(Integer.parseInt(json.getString("a")),
				Integer.parseInt(json.getString("b")), handler -> {
					if (handler.succeeded()) {
						System.out.println("successfully called");

						rabbitOperation
								.publish2RabbitMQ("successfully called for Addition: a="
										+ json.getString("a") + " b="
										+ json.getString("b") + " Result :"
										+ handler.result().toString());

						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	public void subtract(RoutingContext routingContext) {
		System.out.println("SP:sub called");
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.subtract(Integer.parseInt(json.getString("a")),
				Integer.parseInt(json.getString("b")), handler -> {
					if (handler.succeeded()) {
						System.out.println("successfully called");

						rabbitOperation
								.publish2RabbitMQ("successfully called for Subtract: a="
										+ json.getString("a") + " b="
										+ json.getString("b") + "Result :"
										+ handler.result().toString());

						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	public void multiply(RoutingContext routingContext) {
		System.out.println("SP:multiply called");
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.multiply(Integer.parseInt(json.getString("a")),
				Integer.parseInt(json.getString("b")), handler -> {
					if (handler.succeeded()) {
						System.out.println("successfully called");

						rabbitOperation
								.publish2RabbitMQ("successfully called for Multiply: a="
										+ json.getString("a") + " b="
										+ json.getString("b") + " Result :"
										+ handler.result().toString());

						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	public void divide(RoutingContext routingContext) {
		System.out.println("SP:divide called");
		HttpServerResponse response = routingContext.response();
		JsonObject json = toJson(routingContext);
		calculatorService.divide(Integer.parseInt(json.getString("a")),
				Integer.parseInt(json.getString("b")), handler -> {
					if (handler.succeeded()) {
						System.out.println("successfully called");

						rabbitOperation
								.publish2RabbitMQ("successfully called for division: a="
										+ json.getString("a") + " b="
										+ json.getString("b") + " Result :"
										+ handler.result().toString());

						response.putHeader("content-type", "application/json")
								.setStatusCode(200)
								.end(new CustomResponse.ResponseBuilder()
										.withStatusCode(200).build().toJson()
										.put("result", handler.result().toString())
										.toString());
					}
					else {

					}
				});
	}

	private JsonObject toJson(RoutingContext routingContext) {
		MultiMap params = routingContext.queryParams();
		JsonObject json = new JsonObject();
		params.forEach(entry -> {
			json.put(entry.getKey(), entry.getValue());
		});
		return json;
	}

}
