package cal.advance;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class CalculatorServiceImpl implements CalculatorService{

	@Override
	public void add(JsonObject jsonObject, Handler<AsyncResult<String>> resultHandler) {
		resultHandler.handle(Future.succeededFuture("calculator service called"));
	}

}
