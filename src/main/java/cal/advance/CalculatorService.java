package cal.advance;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

@ProxyGen
@VertxGen
public interface CalculatorService {


	void add(JsonObject jsonObject,Handler<AsyncResult<String>> resultHandler);
	
	@Fluent
	static CalculatorService create(Vertx vertx) {
		return new CalculatorServiceImpl();
	}
	
	@Fluent
	static CalculatorService createProxy(Vertx vertx,String address) {
		return new CalculatorServiceVertxEBProxy(vertx,address);
	}
}
