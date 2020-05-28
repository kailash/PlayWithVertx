package cal;

import cal.validation.AdditionValidator;
import cal.validation.ValidationsUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class APIVerticle extends AbstractVerticle {
	private HttpServer server;

	@Override
	public void start() {
		System.out.println("inside API start");
		Router router = Router.router(vertx);
		router.get("/api/add").handler(this::add);
		router.get("/api/sub").handler(this::subtract);
		router.get("/api/multiply").handler(this::multiply);
		router.get("/api/divide").handler(this::divide);
		server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);
	}

	public void add(RoutingContext routingContext) {
		System.out.println("inside add");
		JsonObject requested_data = toJson(routingContext);
		if(ValidationsUtil.isValidate(requested_data, new AdditionValidator())) {
			publishEvent("add", requested_data, null, routingContext.response());
		}else {
			handleResponse(400, "invalid numbers of params", routingContext);
		}
	}

	public void subtract(RoutingContext routingContext) {
		System.out.println("inside sub");
		JsonObject requested_data = toJson(routingContext);
		publishEvent("sub", requested_data, null, routingContext.response());
	}
	
	public void multiply(RoutingContext routingContext) {
		System.out.println("inside sub");
		JsonObject requested_data = toJson(routingContext);
		publishEvent("multiply", requested_data, null, routingContext.response());
	}
	
	
	public void divide(RoutingContext routingContext) {
		JsonObject requested_data=toJson(routingContext);
		publishEvent("divide", requested_data, null, routingContext.response());
	}

	private JsonObject toJson(RoutingContext routingContext) {
		MultiMap params = routingContext.queryParams();
		JsonObject json = new JsonObject();
		params.forEach(entry->{
			json.put(entry.getKey(), entry.getValue());
		});
		return json;
	}

	private void publishEvent(String event, JsonObject requested_data,
			DeliveryOptions options, HttpServerResponse response) {
		System.out.println("publish event");
		if(options==null) options=new DeliveryOptions();
		vertx.eventBus().request(event, requested_data, options, replyHandler -> {
			if (replyHandler.succeeded()) {
				String reply = replyHandler.result().body().toString();
				response.setStatusCode(200)
						.putHeader(HttpHeaders.CONTENT_TYPE.toString(),"application/json")
						.end(reply);
			}
			else if(replyHandler.failed()) {
				response.setStatusCode(500)
						.putHeader(HttpHeaders.CONTENT_TYPE.toString(),"application/json")
						.end(new CustomResponse
								.ResponseBuilder()
								.withStatusCode(500)
								.withMessage(replyHandler.cause().getMessage())
								.build()
								.toJsonString());
			}
		});
	}
	
	public void handleResponse(int statusCode,String message,RoutingContext routingContext) {
		routingContext
			.response()
			.putHeader("content-type", "application/json; charset=utf-8")
			.setStatusCode(statusCode)
			.end(new CustomResponse
		    		  .ResponseBuilder()
		    		  .withStatusCode(400)
		    		  .withMessage("bad request")//Enum.getHttpCodes
		    		  .withCustomMessage(message)
		    		  .build()
		    		  .toJsonString());
	}
}
