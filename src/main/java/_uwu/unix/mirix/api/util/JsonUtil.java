package _uwu.unix.mirix.api.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * @author Unix on 06.10.2019.
 */
public final class JsonUtil {

    private static final JsonParser jsonParser = new JsonParser();
    private static final Gson gson = new GsonBuilder().create();

    private JsonUtil() {
    }

    public static JsonParser getJsonParser() {
        return jsonParser;
    }

    public static Gson getGson() {
        return gson;
    }
}