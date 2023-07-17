
package functions;

import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

public class HelloWorld implements BackgroundFunction<HelloWorld.StorageEvent> {

  @Override
  public void accept(StorageEvent event, Context context) throws Exception {
    System.out.println("Receive event: " + event);
  }

  //
  public static class StorageEvent {
    public String name;
  }
}