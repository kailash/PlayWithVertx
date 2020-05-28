package cal;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class CalculatorVerticle extends AbstractVerticle {

	@Override
	public void start() {
		System.out.println("calculator varticle..");
		vertx.eventBus().consumer("add", message -> {
			add(message);
		});
		vertx.eventBus().consumer("sub", message -> {
			sub(message);
		});
		vertx.eventBus().consumer("multiply", message -> {
			multiply(message);
		});
		vertx.eventBus().consumer("divide", message -> {
			divide(message);
		});

	}

	private void add(Message<Object> message) {
		System.out.println("requested_body : " + message.body().toString());
		JsonObject request = (JsonObject) message.body();
		int a = Integer.parseInt(request.getString("a"));
		int b = Integer.parseInt(request.getString("b"));
		int res = a + b;
		// JsonArray response = new JsonArray();
		JsonObject result = new JsonObject();
		result.put("result", res);
		// response.add(result);
		message.reply(result);
	}

	private void sub(Message<Object> message) {
		System.out.println("requested_body : " + message.body().toString());
		JsonObject request = (JsonObject) message.body();
		int a = Integer.parseInt(request.getString("a"));
		int b = Integer.parseInt(request.getString("b"));
		int res = a - b;
		// JsonArray response = new JsonArray();
		JsonObject result = new JsonObject();
		result.put("result", res);
		// response.add(result);
		message.reply(result);
	}

	private void multiply(Message<Object> message) {
		System.out.println("requested_body : " + message.body().toString());
		JsonObject request = (JsonObject) message.body();
		int a = Integer.parseInt(request.getString("a"));
		int b = Integer.parseInt(request.getString("b"));
		int res = a * b;
		// JsonArray response = new JsonArray();
		JsonObject result = new JsonObject();
		result.put("result", res);
		// response.add(result);
		message.reply(result);
	}

	private void divide(Message<Object> message) {
		JsonObject request = (JsonObject) message.body();
		int a = Integer.parseInt(request.getString("a"));
		int b = Integer.parseInt(request.getString("b"));
		if (b == 0) {
			message.fail(500, "error: divide by zero error.");
		}
		else {
			int res = a / b;
			// JsonArray response = new JsonArray();
			JsonObject result = new JsonObject();
			result.put("result", res);
			// response.add(result);
			message.reply(result);
		}
	}
}
