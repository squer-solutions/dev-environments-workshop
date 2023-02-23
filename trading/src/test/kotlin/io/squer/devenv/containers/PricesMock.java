package io.squer.devenv.containers;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

public class PricesMock extends GenericContainer<PricesMock> {

  private static final String IMAGE_NAME = "squer-trading-mock";
  private static final int HOST_PORT = 8090;
  private static final int CONTAINER_PORT = 8090;

  public PricesMock(final Network network) {
    super(DockerImageName.parse(IMAGE_NAME));
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
    this
      .withEnv("KAFKA_BOOTSTRAP_SERVERS", "redpanda:29092")
      .withNetwork(network)
      .withNetworkMode("host")
      .withCreateContainerCmdModifier(cmd -> cmd.withHostName("prices-mock").withName("prices-mock"));
  }
}
