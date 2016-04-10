package io.fragilebox.protocol.benchmark.server;

import com.google.protobuf.InvalidProtocolBufferException;
import io.fragilebox.protocol.BoxProtocol.BenchmarkMessage;
import static io.fragilebox.protocol.BoxProtocol.BenchmarkMessage.Type.*;
import io.fragilebox.protocol.BoxProtocol.BenchmarkOptions;
import io.fragilebox.protocol.BoxProtocol.BenchmarkResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/benchmark")
public class BenchmarkServer {

  private static final Logger LOG = LoggerFactory.getLogger(
      BenchmarkServer.class);

  private static final int DEFAULT_REQUESTS_NUMBER = 10_000;
  private static final int DEFAULT_PAYLOAD_SIZE = 32;
  
  private static final AtomicInteger ID = new AtomicInteger(-1);
  
  private final AtomicInteger currentId = new AtomicInteger(-1);
  private final AtomicLong startTime = new AtomicLong(0L);
  private final AtomicLong accumulator = new AtomicLong(0L);

  private CountDownLatch requests;
  private volatile int benchmarkSize;
  private volatile int payloadSize;

  @OnMessage
  public void message(final Session session, byte[] bytes) throws
      InvalidProtocolBufferException, IOException {
    final long timePoint = System.nanoTime();
    final BenchmarkMessage msg = BenchmarkMessage.parseFrom(bytes);

    switch (msg.getType()) {
      case RUN:
        BenchmarkOptions options = msg.getOptions();
        int bmSize = DEFAULT_REQUESTS_NUMBER,
         pldSize = DEFAULT_PAYLOAD_SIZE;

        if (options != null) {
          bmSize = options.getBenchmarkSize() > 0
              ? options.getBenchmarkSize() : DEFAULT_REQUESTS_NUMBER;
          pldSize = options.getPayloadSize() > 0 ? options.getPayloadSize()
              : DEFAULT_PAYLOAD_SIZE;
        }
        benchmarkSize = bmSize;
        payloadSize = pldSize;
        requests = new CountDownLatch(benchmarkSize);

        startTime.set(System.nanoTime());
        currentId.set(sendRequest(session, payloadSize));
        break;
      case PAYLOAD_RESPONSE:
        if (currentId.get() == msg.getId()) {
          requests.countDown();
          accumulator.addAndGet(timePoint - startTime.get());

          if (requests.getCount() == 0L) {
            long msgTime = (accumulator.get() / benchmarkSize) / 1000;
            sendStop(session, msgTime, benchmarkSize);
          } else {
            startTime.set(System.nanoTime());
            currentId.set(sendRequest(session, payloadSize));
          }
        } else {
          LOG.warn("Unexpected response with id={}", msg.getId());
        }
        break;
      default:
        LOG.warn("Unexpected message type={}", msg.getType());
        break;
    }
  }

  @OnOpen
  public void open(final Session session) {
    LOG.info("Session has been opened");
  }

  @OnClose
  public void close(final Session session) {
    LOG.info("Session has been closed");
  }

  private static int sendRequest(final Session session, final int payloadSize) {
    final int id = ID.addAndGet(1);

    try (OutputStream out = session.getBasicRemote().getSendStream()) {
      BenchmarkMessage.Builder builder = BenchmarkMessage.newBuilder()
          .setType(PAYLOAD_REQUEST).setId(id);
      for (int i = 0; i < payloadSize / 4; i++) {
        builder.addPayload(75);
      }
      builder.build().writeTo(out);
    } catch (IOException e) {
      LOG.error("Can not send PAYLOAD_REQUEST message", e);
    }
    return id;
  }

  private static void sendStop(final Session session, final long messageTime,
      final int processedMessages) {
    try (OutputStream out = session.getBasicRemote().getSendStream()) {
      BenchmarkMessage.newBuilder()
          .setType(STOP)
          .setId(57)
          .setResult(BenchmarkResult.newBuilder()
              .setMessageTime(messageTime)
              .setProcessedMessages(processedMessages)
              .build())
          .build().writeTo(out);
    } catch (IOException e) {
      LOG.error("Can not send STOP message", e);
    }
  }
}
