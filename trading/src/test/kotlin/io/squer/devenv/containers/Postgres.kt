package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.utility.DockerImageName

class Postgres(network: Network?) : GenericContainer<Postgres?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withNetwork(network)
        withNetworkMode("bridge")
        withEnv(ENV)
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("postgres").withName("postgres") }
    }

    companion object {
        private const val IMAGE_NAME = "postgres:14.1-alpine"
        private const val HOST_PORT = 5432
        private const val CONTAINER_PORT = 5432
        private val ENV = mapOf(
                "POSTGRES_USER" to "postgres",
                "POSTGRES_PASSWORD" to "postgres"
        )
    }
}