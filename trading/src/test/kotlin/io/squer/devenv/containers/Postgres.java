package io.squer.devenv.containers;

import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class Postgres extends GenericContainer<Postgres> {

  private static final String IMAGE_NAME = "postgres:14.1-alpine";
  private static final int EXPOSED_PORT = 5432;
  private static final int HOST_PORT = 5432;
  private static final int CONTAINER_PORT = 5432;
  private static final Map<String, String> ENV = Map.of(
    "POSTGRES_USER", "postgres",
    "POSTGRES_PASSWORD", "postgres"
  );

  private Postgres() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  private static GenericContainer<Postgres> instance;

  public static GenericContainer<Postgres> getInstance(final Network network) {
    if (instance == null) {
      instance = new Postgres()
        .withNetwork(network)
        .withNetworkMode("bridge")
        .withExposedPorts(EXPOSED_PORT)
        .withEnv(ENV)
        .withCreateContainerCmdModifier(cmd -> cmd.withHostName("postgres").withName("postgres"))
        .withReuse(false);

      ((Postgres) instance).configurePorts();

    }
    return instance;
  }

  private void configurePorts() {
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
  }

}
