package config;

import java.util.HashMap;
import java.util.Map;

public class BaseConfig {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> defaultHeaders =  new HashMap<>();
        defaultHeaders.put("Accept", "application/json");
        defaultHeaders.put("Content-Type", "application/json");
        return defaultHeaders;
    }
}
