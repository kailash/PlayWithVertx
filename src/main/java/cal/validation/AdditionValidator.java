package cal.validation;

import io.vertx.core.json.JsonObject;

public class AdditionValidator implements Validator {

	@Override
	public boolean validate(JsonObject jsonObject) {
		return validateRequiredParams(jsonObject);
	}

	private boolean validateRequiredParams(JsonObject jsonObject) {
		return jsonObject.size() >= 2;
	}
}
