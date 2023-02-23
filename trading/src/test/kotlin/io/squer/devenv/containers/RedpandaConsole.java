package io.squer.devenv.containers;

import java.util.Map;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.shaded.org.apache.commons.lang3.StringEscapeUtils;
import org.testcontainers.utility.DockerImageName;

public class RedpandaConsole extends GenericContainer<RedpandaConsole> {

  private static final String IMAGE_NAME = "docker.redpanda.com/vectorized/console:latest";
  private static final int EXPOSED_PORT = 8080;
  private static final int HOST_PORT = 4040;
  private static final int CONTAINER_PORT = 8080;

  private static final Map<String, String> ENV = Map.of(
    "CONFIG_FILEPATH", "/tmp/config.yml",
    "CONSOLE_CONFIG_FILE", "kafka:\n" +
      "  brokers: [\"redpanda:29092\"]\n" +
      "  schemaRegistry:\n" +
      "    enabled: true\n" +
      "    urls: [\"http://redpanda:8081\"]\n" +
      "redpanda:\n" +
      "  adminApi:\n" +
      "    enabled: true\n" +
      "    urls: [\"http://redpanda:9644\"]\n" +
      "connect:\n" +
      "  enabled: true\n" +
      "  clusters:\n" +
      "    - name: local-connect-cluster\n" +
      "      url: http://connect:8083"
  );

  private RedpandaConsole() {
    super(DockerImageName.parse(IMAGE_NAME));
  }

  private static GenericContainer<RedpandaConsole> instance;

  public static GenericContainer<RedpandaConsole> getInstance(final Network network) {
    if (instance == null) {
      instance = new RedpandaConsole()
        .withNetwork(network)
        .withNetworkMode("host")
        .withExposedPorts(EXPOSED_PORT)
        .withEnv(ENV)
        .withCreateContainerCmdModifier(cmd -> cmd.withHostName("redpanda-console").withName("redpanda-console"))
        .withCreateContainerCmdModifier(cmd -> cmd.withEntrypoint("/bin/sh"))
        .withCommand("-c", "echo \"$CONSOLE_CONFIG_FILE\" > /tmp/config.yml ; cat /tmp/config.yml ; /app/console")
        .withReuse(false);

      ((RedpandaConsole) instance).configurePorts();

    }
    return instance;
  }

  private void configurePorts() {
    super.addFixedExposedPort(HOST_PORT, CONTAINER_PORT);
  }

}
