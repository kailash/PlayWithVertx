package cal.validation;

import io.vertx.core.json.JsonObject;

public class ValidationsUtil {
	
	public static boolean isValidate(JsonObject jsonObject,Validator validator) {
		return validator.validate(jsonObject);
	}

}
