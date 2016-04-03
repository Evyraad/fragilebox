# FragileBox

This is a procedurally generated exploration multiplayer game.

Requirements:

1. JDK 1.8 or above
2. Dart 1.15.x or above (point DART_SDK to the installation directory)
3. Apache Maven 3.x
4. Docker 1.10.x or above (to build and run images)

## Build and run

### Uber jars

```shell
$ cd fragilebox
$ mvn clean package
...
[INFO] fragilebox ......................................... SUCCESS [  0.098 s]
[INFO] box-client ......................................... SUCCESS [  7.136 s]
[INFO] box-server ......................................... SUCCESS [  5.869 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 13.194 s
[INFO] Finished at: 2016-04-03T15:03:50+02:00
[INFO] Final Memory: 48M/928M
[INFO] ------------------------------------------------------------------------
```
```shell
$ cd fragilebox
$ java -Dswarm.port.offset=10 -jar box-client/target/box-client-1.0-SNAPSHOT-swarm.jar
INFO  [org.jboss.msc] (main) JBoss MSC version 1.2.6.Final
INFO  [org.jboss.as] (MSC service thread 1-6) WFLYSRV0049: WildFly Core 2.0.10.Final "Kenny" starting
INFO  [stdout] (MSC service thread 1-3) << Starting monitor service >>
INFO  [org.jboss.as.naming] (ServerService Thread Pool -- 13) WFLYNAM0001: Activating Naming Subsystem
...
INFO  [org.wildfly.extension.undertow] (MSC service thread 1-3) WFLYUT0006: Undertow HTTP listener default listening on [0:0:0:0:0:0:0:0]:8090
...
```
```shell
$ cd fragilebox
$ java -jar box-server/target/box-server-1.0-SNAPSHOT-swarm.jar
INFO  [org.jboss.msc] (main) JBoss MSC version 1.2.6.Final
INFO  [org.jboss.as] (MSC service thread 1-6) WFLYSRV0049: WildFly Core 2.0.10.Final "Kenny" starting
...
INFO  [org.wildfly.extension.undertow] (ServerService Thread Pool -- 3) WFLYUT0021: Registered web context: /
INFO  [org.jboss.as.server] (main) WFLYSRV0010: Deployed "box-server-1.0-SNAPSHOT.war" (runtime-name : "box-server-1.0-SNAPSHOT.war")
...
```

### Docker images
Note that server and client ports are mapped onto ```18080``` and ```28080``` in Docker images accordingly.
```shell
$ cd fragilebox/box-client
$ mvn clean package docker:build docker:start -Ddocker.follow
[INFO] --- docker-maven-plugin:0.13.7:start (default-cli) @ box-client ---
[INFO] DOCKER> [dsalychev/io.fragilebox.box-client:latest] : Start container fbfd8b6382aa
SWARM> 13:24:43,130 INFO  [org.jboss.msc] (main) JBoss MSC version 1.2.6.Final
SWARM> 13:24:43,283 INFO  [org.jboss.as] (MSC service thread 1-7) WFLYSRV0049: WildFly Core 2.0.10.Final "Kenny" starting
SWARM> 13:24:43,516 INFO  [stdout] (MSC service thread 1-3) << Starting monitor service >>
...
```
```shell
$ cd fragilebox/box-server
$ mvn clean package docker:build docker:start -Ddocker.follow
[INFO] --- docker-maven-plugin:0.13.7:start (default-cli) @ box-server ---
[INFO] DOCKER> [dsalychev/io.fragilebox.box-server:latest] : Start container dbf90b4454b0
SWARM> 13:28:36,935 INFO  [org.jboss.msc] (main) JBoss MSC version 1.2.6.Final
SWARM> 13:28:37,071 INFO  [org.jboss.as] (MSC service thread 1-6) WFLYSRV0049: WildFly Core 2.0.10.Final "Kenny" starting
SWARM> 13:28:37,304 INFO  [stdout] (MSC service thread 1-4) << Starting monitor service >>
...
```
