package com.seshutechie.taxii2lib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.seshutechie.taxii2lib.TaxiiAppException;
import com.seshutechie.taxii2lib.stix.model.StixDiscovery;

public class JsonUtil {
    private static final JsonParser parser = new JsonParser();

    public static String prettyJson(String jsonString) throws TaxiiAppException {
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

    public static Object jsonToObject(String jsonString, Class objType) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, objType);
    }

    public static String objectToJson(Object object) {
        return new Gson().toJson(object);
    }
}
