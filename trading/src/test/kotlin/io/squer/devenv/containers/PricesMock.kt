package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.utility.DockerImageName

class PricesMock(network: Network?) : GenericContainer<PricesMock?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withEnv("KAFKA_BOOTSTRAP_SERVERS", "redpanda:29092")
        withNetwork(network)
        withNetworkMode("host")
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("prices-mock") }
    }

    companion object {
        private const val IMAGE_NAME = "squer-trading-mock"
        private const val HOST_PORT = 8090
        private const val CONTAINER_PORT = 8090
    }
}
