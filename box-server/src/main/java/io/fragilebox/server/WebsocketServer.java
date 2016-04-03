package io.fragilebox.server;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint("/echo")
public class WebsocketServer {

  private final ExecutorService exec = Executors.newSingleThreadExecutor();

  @OnMessage
  public String message(final Session session, String message) {
    exec.submit(() -> {
      for (int i = 0; i < 3; i++) {
        try {
          session.getBasicRemote().sendText("Message from server #" + i);
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    return "Thanks for: " + message;
  }

  @OnOpen
  public void open(final Session session) {
    try {
      session.getBasicRemote().sendText("New connection has been established " +
          "with session ID: " + session.getId());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
