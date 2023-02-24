package io.squer.devenv

import io.squer.devenv.containers.*
import org.slf4j.LoggerFactory
import org.testcontainers.containers.Network.newNetwork

fun main() {

    DevEnv().setupDevEnv(false)

    while (true) {}
}

class DevEnv {

    fun setupDevEnv(isIntegrationTest: Boolean) {
        LOG.info("Creating new shared network...")
        val defaultNetwork = newNetwork()

        LOG.info("Starting containers...")
        val postgres = Postgres(defaultNetwork)
        postgres.start()
        val redis = Redis(defaultNetwork)
        redis.start()
        val redpanda = Redpanda(defaultNetwork)
        redpanda.start()
        redpanda.createTopics()

        if (!isIntegrationTest) {
            val redisCommander = RedisCommander(defaultNetwork)
            redisCommander.start()
            val redpandaConsole = RedpandaConsole(defaultNetwork)
            redpandaConsole.start()
            val pricesMock = PricesMock(defaultNetwork)
            pricesMock.start()
        }

        LOG.info("Setup complete. Containers will shut down on application exit.")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(DevEnv::class.java)
    }
}
