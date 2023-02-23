package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

class Redpanda(network: Network?) : GenericContainer<Redpanda?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withNetwork(network)
        withNetworkMode("bridge")
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("redpanda").withName("redpanda") }
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withEntrypoint("/usr/bin/rpk") }
        withCommand("redpanda start --smp 1 --overprovisioned --node-id 0 --kafka-addr PLAINTEXT://0.0.0.0:29092,OUTSIDE://0.0.0.0:9092 --advertise-kafka-addr PLAINTEXT://redpanda:29092,OUTSIDE://localhost:9092 --pandaproxy-addr 0.0.0.0:8082 --advertise-pandaproxy-addr localhost:8082")
        withExposedPorts(8081, 8082, 9092, 9644, 29092)
        waitingFor(
                Wait.forLogMessage(".*Successfully started Redpanda*.\\n", 1)
        )
    }

    fun createTopics() {
        try {
            this.execInContainer("sh", "-c", "rpk topic create squer-trading-prices")
        } catch (e: Exception) {
            throw RuntimeException("Could not create topics", e)
        }
    }

    companion object {
        private const val IMAGE_NAME = "docker.redpanda.com/vectorized/redpanda:latest"
        private const val HOST_PORT = 9092
        private const val CONTAINER_PORT = 9092
    }
}