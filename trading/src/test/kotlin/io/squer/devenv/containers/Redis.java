package io.squer.devenv.containers;

import java.util.Map;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class Redis extends GenericContainer<Redis> {

  private static final String IMAGE_NAME = "docker.io/bitnami/redis:7.0";
  private static final int EXPOSED_PORT = 6379;
  private static final int HOST_PORT = 6379;
  private static final int CONTAINER_PORT = 6379;
  private static final Map<String, String> ENV = Map.of(
    "ALLOW_EMPTY_PASSWORD", "yes"
  );

  private Redis() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  private static GenericContainer<Redis> instance;

  public static GenericContainer<Redis> getInstance(final Network network) {
    if (instance == null) {
      instance = new Redis()
        .withEnv(ENV)
        .withNetwork(network)
        .withNetworkMode("bridge")
        .withExposedPorts(EXPOSED_PORT)
        .withCreateContainerCmdModifier(cmd -> cmd.withHostName("redis").withName("redis"))
        .waitingFor(
          Wait.forLogMessage(".*Ready to accept connections.*\\n", 1)
        );

      ((Redis) instance).configurePorts();
    }
    return instance;
  }

  private void configurePorts() {
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
  }

}
