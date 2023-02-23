package io.squer.devenv.containers

import com.github.dockerjava.api.command.CreateContainerCmd
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.utility.DockerImageName

class RedisCommander(network: Network?) : GenericContainer<RedisCommander?>(DockerImageName.parse(IMAGE_NAME)) {
    init {
        addFixedExposedPort(HOST_PORT, CONTAINER_PORT)
        withNetwork(network)
        withNetworkMode("host")
        withEnv("REDIS_HOSTS", "local:redis:6379")
        withCreateContainerCmdModifier { cmd: CreateContainerCmd -> cmd.withHostName("redis-commander").withName("redis-commander") }
    }

    companion object {
        private const val IMAGE_NAME = "rediscommander/redis-commander:latest"
        private const val HOST_PORT = 8089
        private const val CONTAINER_PORT = 8081
    }
}