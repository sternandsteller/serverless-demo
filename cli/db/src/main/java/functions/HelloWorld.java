
package functions;

import com.google.cloud.functions.Context;
import com.google.cloud.functions.RawBackgroundFunction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HelloWorld implements RawBackgroundFunction {

  private static final Gson gson = new Gson();

  @Override
  public void accept(String json, Context context) {
    JsonObject body = gson.fromJson(json, JsonObject.class);
    System.out.println("Function triggered by event on: " + context.resource());
    System.out.println("Event type: " + context.eventType());

    if (body != null && body.has("oldValue")) {
      System.out.println("Old value:");
      System.out.println(body.get("oldValue").getAsString());
    }

    if (body != null && body.has("value")) {
      System.out.println("New value:");
      System.out.println(body.get("value").getAsString());
    }
  }
}