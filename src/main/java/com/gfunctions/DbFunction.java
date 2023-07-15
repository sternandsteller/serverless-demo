package com.gfunctions;

import com.google.cloud.functions.Context;
import com.google.cloud.functions.RawBackgroundFunction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DbFunction implements RawBackgroundFunction {

    @Override
    public void accept(String json, Context context) throws Exception {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // Access the Firestore event data
        JsonObject data = jsonObject.getAsJsonObject("data");
        String documentId = data.get("documentId").getAsString();
        JsonObject documentFields = data.getAsJsonObject("fields");

        // Process the event data
        // ...

        // Example: Print the document ID and fields
        System.out.println("Document ID: " + documentId);
        System.out.println("Fields: " + documentFields);
    }
}
