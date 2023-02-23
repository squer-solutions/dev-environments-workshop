package io.squer.devenv.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class RedisCommander extends GenericContainer<RedisCommander> {

  private static final String IMAGE_NAME = "rediscommander/redis-commander:latest";
  private static final int HOST_PORT = 8089;
  private static final int CONTAINER_PORT = 8081;

  public RedisCommander(final Network network) {
    super(DockerImageName.parse(IMAGE_NAME));
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
    this
      .withNetwork(network)
      .withNetworkMode("host")
      .withEnv("REDIS_HOSTS", "local:redis:6379")
      .withCreateContainerCmdModifier(cmd -> cmd.withHostName("redis-commander").withName("redis-commander"));
  }
}
