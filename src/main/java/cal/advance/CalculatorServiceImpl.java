package cal.advance;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

public class CalculatorServiceImpl implements CalculatorService{

	@Override
	public void add(int a,int b, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("SP:Calculator proxy called");
		System.out.println("a : "+a+" b: "+b);
		int result=a+b;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void subtract(int a, int b, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("SP:Calculator proxy called");
		System.out.println("a : "+a+" b: "+b);
		int result=a-b;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void multiply(int a, int b, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("SP:Calculator proxy called");
		System.out.println("a : "+a+" b: "+b);
		int result=a*b;
		resultHandler.handle(Future.succeededFuture(result));
	}

	@Override
	public void divide(int a, int b, Handler<AsyncResult<Integer>> resultHandler) {
		System.out.println("SP:Calculator proxy called");
		System.out.println("a : "+a+" b: "+b);
		int result=a/b;
		resultHandler.handle(Future.succeededFuture(result));
	}

}
