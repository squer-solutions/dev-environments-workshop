package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName

class Redis(network: Network?) : GenericContainer<Redis?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withEnv(ENV)
        withNetwork(network)
        withNetworkMode("bridge")
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("redis").withName("redis") }
        waitingFor(
                Wait.forLogMessage(".*Ready to accept connections.*\\n", 1)
        )
    }

    companion object {
        private const val IMAGE_NAME = "docker.io/bitnami/redis:7.0"
        private const val HOST_PORT = 6379
        private const val CONTAINER_PORT = 6379
        private val ENV = mapOf("ALLOW_EMPTY_PASSWORD" to "yes")
    }
}