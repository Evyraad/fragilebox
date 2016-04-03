package io.fragilebox.client;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.undertow.WARArchive;

/**
 * Entry point for the FragileBox Client application.
 */
public class App {

  public static void main(String[] args) throws Exception {
    Container container = new Container();
    WARArchive deployment = ShrinkWrap.create(WARArchive.class);
    deployment.staticContent();
    container.start().deploy(deployment);
  }
}
