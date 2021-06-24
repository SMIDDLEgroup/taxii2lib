package com.seshutechie.taxii2lib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
    private static final Gson prettyGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    public static String prettyJson(String jsonString) {
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
        return prettyGson.toJson(json);
    }

    public static <T> T jsonToObject(String jsonString, Class<T> type) {
        return gson.fromJson(jsonString, type);
    }

    public static String objectToJson(Object object) {
        return gson.toJson(object);
    }
}
