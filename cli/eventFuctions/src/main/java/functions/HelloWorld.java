
package functions;

import com.google.cloud.functions.CloudEventsFunction;
import com.google.events.cloud.storage.v1.StorageObjectData;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import io.cloudevents.CloudEvent;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class HelloWorld implements CloudEventsFunction {
  private static final Logger logger = Logger.getLogger(HelloWorld.class.getName());

  @Override
  public void accept(CloudEvent event) throws InvalidProtocolBufferException {
    logger.info("Event: " + event.getId());
    logger.info("Event Type: " + event.getType());

    if (event.getData() == null) {
      logger.warning("No data found in cloud event payload!");
      return;
    }

    String cloudEventData = new String(event.getData().toBytes(), StandardCharsets.UTF_8);
    StorageObjectData.Builder builder = StorageObjectData.newBuilder();
    JsonFormat.parser().merge(cloudEventData, builder);
    StorageObjectData data = builder.build();

    logger.info("Bucket: " + data.getBucket());
    logger.info("File: " + data.getName());
    logger.info("Metageneration: " + data.getMetageneration());
    logger.info("Created: " + data.getTimeCreated());
    logger.info("Updated: " + data.getUpdated());
  }
}