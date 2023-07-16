
package functions;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import com.google.events.cloud.storage.v1.StorageObjectData;
import com.google.gson.Gson;

public class HelloWorld implements BackgroundFunction<StorageObjectData> {

  public HelloWorld() {
    new Gson();
  }

  @Override
  public void accept(StorageObjectData storageObjectData, Context context) {
    String bucket = storageObjectData.getBucket();
    String object = storageObjectData.getName();
    String eventType = context.eventType();
    String eventId = context.eventId();

    System.out.println("Received Cloud Storage event:");
    System.out.println("Bucket: " + bucket);
    System.out.println("Object: " + object);
    System.out.println("Event Type: " + eventType);
    System.out.println("Event ID: " + eventId);
  }
}
