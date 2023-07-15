package com.gfunctions;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.events.cloud.storage.v1.StorageObjectData;
import com.google.gson.Gson;

public class FileFunction implements BackgroundFunction<StorageObjectData> {

    public FileFunction() {
        new Gson();
    }

    @Override
    public void accept(StorageObjectData storageObjectData, Context context) {
        // Process the storage event
        String bucket = storageObjectData.getBucket();
        String object = storageObjectData.getName();
        String eventType = context.eventType();
        String eventId = context.eventId();

        System.out.println("Received Cloud Storage event:");
        System.out.println("Bucket: " + bucket);
        System.out.println("Object: " + object);
        System.out.println("Event Type: " + eventType);
        System.out.println("Event ID: " + eventId);

        // Implement your custom logic here based on the event
        // For example, you could trigger additional processing, notifications, etc.
    }
}
