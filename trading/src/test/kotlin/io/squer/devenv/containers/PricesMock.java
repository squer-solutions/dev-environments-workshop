package io.squer.devenv.containers;

import java.nio.file.Path;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

public class PricesMock extends GenericContainer<PricesMock> {

  private static final String IMAGE_NAME = "squer-trading-mock";

  private static final int EXPOSED_PORT = 8090;

  private static final int HOST_PORT = 8090;

  private static final int CONTAINER_PORT = 8090;

  private PricesMock() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  private static GenericContainer<PricesMock> instance;

  public static GenericContainer<PricesMock> getInstance(final Network network) {
    if (instance == null) {
      instance = new PricesMock()
        .withEnv("KAFKA_BOOTSTRAP_SERVERS", "redpanda:29092")
        .withNetwork(network)
        .withNetworkMode("host")
        .withExposedPorts(EXPOSED_PORT)
        .withCreateContainerCmdModifier(cmd -> cmd.withHostName("prices-mock").withName("prices-mock"));

      ((PricesMock) instance).configurePorts();
    }
    return instance;
  }

  private void configurePorts() {
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
  }

}
