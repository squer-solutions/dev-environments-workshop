package io.squer.devenv.containers;

import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class Postgres extends GenericContainer<Postgres> {

  private static final String IMAGE_NAME = "postgres:14.1-alpine";
  private static final int HOST_PORT = 5432;
  private static final int CONTAINER_PORT = 5432;
  private static final Map<String, String> ENV = Map.of(
    "POSTGRES_USER", "postgres",
    "POSTGRES_PASSWORD", "postgres"
  );

  public Postgres(final Network network) {
    super(DockerImageName.parse(IMAGE_NAME));
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
    this
      .withNetwork(network)
      .withNetworkMode("bridge")
      .withEnv(ENV)
      .withCreateContainerCmdModifier(cmd -> cmd.withHostName("postgres").withName("postgres"));
  }
}
