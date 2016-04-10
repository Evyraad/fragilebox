package io.fragilebox.server;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class WebsocketServer {

  @OnOpen
  public void open(final Session session) {
    System.out.print("Session has been opened");
  }

  @OnClose
  public void close(final Session session) {
    System.out.print("Session has been closed");
  }
}
