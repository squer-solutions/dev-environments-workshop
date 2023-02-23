package io.squer.devenv.containers;

import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class RedisCommander extends GenericContainer<RedisCommander> {

  private static final String IMAGE_NAME = "rediscommander/redis-commander:latest";
  private static final int HOST_PORT = 8089;
  private static final int CONTAINER_PORT = 8081;
  private static final int EXPOSED_PORT = 8081;

  private RedisCommander() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  private static GenericContainer<RedisCommander> instance;

  public static GenericContainer<RedisCommander> getInstance(final Network network) {
    if (instance == null) {
      instance = new RedisCommander()
        .withNetwork(network)
        .withNetworkMode("host")
        .withExposedPorts(EXPOSED_PORT)
        .withEnv("REDIS_HOSTS", "local:redis:6379")
        .withCreateContainerCmdModifier(cmd -> cmd.withHostName("redis-commander").withName("redis-commander"));

      ((RedisCommander) instance).configurePorts();
    }
    return instance;
  }

  private void configurePorts() {
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
  }

}
