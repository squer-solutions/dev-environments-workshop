package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.utility.DockerImageName

class RedpandaConsole(network: Network?) : GenericContainer<RedpandaConsole?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withNetwork(network)
        withNetworkMode("host")
        withEnv(ENV)
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("redpanda-console").withName("redpanda-console") }
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withEntrypoint("/bin/sh") }
        withCommand("-c", "echo \"\$CONSOLE_CONFIG_FILE\" > /tmp/config.yml ; cat /tmp/config.yml ; /app/console")
    }

    companion object {
        private const val IMAGE_NAME = "docker.redpanda.com/vectorized/console:latest"
        private const val HOST_PORT = 4040
        private const val CONTAINER_PORT = 8080
        private val ENV = mapOf("CONFIG_FILEPATH" to "/tmp/config.yml", "CONSOLE_CONFIG_FILE" to """kafka:
  brokers: ["redpanda:29092"]
  schemaRegistry:
    enabled: true
    urls: ["http://redpanda:8081"]
redpanda:
  adminApi:
    enabled: true
    urls: ["http://redpanda:9644"]
connect:
  enabled: true
  clusters:
    - name: local-connect-cluster
      url: http://connect:8083""")
    }
}