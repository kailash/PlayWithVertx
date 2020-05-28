package cal.validation;

import io.vertx.core.json.JsonObject;

public interface Validator {

	public boolean validate(JsonObject jsonObject);
}
