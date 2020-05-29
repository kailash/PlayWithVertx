package cal.advance;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

@ProxyGen
@VertxGen
public interface CalculatorService {


	void add(int a,int b,Handler<AsyncResult<Integer>> resultHandler);
	void subtract(int a,int b,Handler<AsyncResult<Integer>> resultHandler);
	void multiply(int a,int b,Handler<AsyncResult<Integer>> resultHandler);
	void divide(int a,int b,Handler<AsyncResult<Integer>> resultHandler);
	
	@Fluent
	static CalculatorService create(Vertx vertx) {
		return new CalculatorServiceImpl();
	}
	//create proxy
	@Fluent
	static CalculatorService createProxy(Vertx vertx,String address) {
		return new CalculatorServiceVertxEBProxy(vertx,address);
	}
}
