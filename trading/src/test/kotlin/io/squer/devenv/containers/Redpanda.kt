package io.squer.devenv.containers;

import java.io.IOException;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

public class Redpanda extends GenericContainer<Redpanda> {

  private static final String IMAGE_NAME = "docker.redpanda.com/vectorized/redpanda:latest";

  public Redpanda(final Network network) {
    super(DockerImageName.parse(IMAGE_NAME));
    this
      .withNetwork(network)
      .withNetworkMode("bridge")
      .withCreateContainerCmdModifier(cmd -> cmd.withHostName("redpanda").withName("redpanda"))
      .withCreateContainerCmdModifier(cmd -> cmd.withEntrypoint("/usr/bin/rpk"))
      .withCommand("redpanda start --smp 1 --overprovisioned --node-id 0 --kafka-addr PLAINTEXT://0.0.0.0:29092,OUTSIDE://0.0.0.0:9092 --advertise-kafka-addr PLAINTEXT://redpanda:29092,OUTSIDE://localhost:9092 --pandaproxy-addr 0.0.0.0:8082 --advertise-pandaproxy-addr localhost:8082")
      .withExposedPorts(8081, 8082, 9092, 9644, 29092)
      .waitingFor(
        Wait.forLogMessage(".*Successfully started Redpanda*.\\n", 1)
      );
  }

  public void createTopics() {
    try {
      this.execInContainer("sh", "-c", "rpk topic create squer-trading-prices");
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Could not create topics", e);
    }
  }

}
