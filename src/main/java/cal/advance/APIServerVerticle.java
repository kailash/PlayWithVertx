package cal.advance;

import cal.CustomResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.serviceproxy.ServiceProxyBuilder;

public class APIServerVerticle extends AbstractVerticle {
	private HttpServer server;
	private CalculatorService calculatorService;

	@Override
	public void start() {
		Router router = Router.router(vertx);
		router.get("/api/add").handler(this::add);
		

		ServiceProxyBuilder builder = new ServiceProxyBuilder(vertx)
				.setAddress("calculator.address");
		calculatorService = builder.build(CalculatorService.class);
		
		server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);
	}

	public void add(RoutingContext routingContext) {
		HttpServerResponse response=routingContext.response();
		calculatorService.add(new JsonObject().put("dummy", "dummy"), handler->{
			if(handler.succeeded()) {
				System.out.println("successfully called");
				response.putHeader("content-type", "application/json").setStatusCode(200)
						.end(new CustomResponse
								.ResponseBuilder()
								.withStatusCode(200)
								.withMessage(handler.result())
								.build()
								.toJsonString());
			}else {
				
			}
		});
	}

}
