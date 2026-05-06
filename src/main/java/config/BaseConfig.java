package config;

import java.util.HashMap;
import java.util.Map;

public class BaseConfig {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static Map<String, String> getDefaultHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
