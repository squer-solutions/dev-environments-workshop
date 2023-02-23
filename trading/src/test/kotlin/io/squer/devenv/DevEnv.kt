package io.squer.devenv

import io.squer.devenv.containers.*
import org.slf4j.LoggerFactory
import org.testcontainers.containers.Network.newNetwork

fun main() {

    DevEnv().setupDevEnv()

    while (true) {}
}

class DevEnv {
    fun setupDevEnv() {
        LOG.info("Creating new shared network...")
        val defaultNetwork = newNetwork()


        LOG.info("Initializing containers...")
        val postgres = Postgres(defaultNetwork)
        val redis = Redis(defaultNetwork)
        val redisCommander = RedisCommander(defaultNetwork)
        val redpanda = Redpanda(defaultNetwork)
        val redpandaConsole = RedpandaConsole(defaultNetwork)
        val pricesMock = PricesMock(defaultNetwork)

        LOG.info("Starting containers...")
        postgres.start()

        redis.start()

        redisCommander.start()

        redpanda.start()
        redpanda.createTopics()

        redpandaConsole.start()

        pricesMock.start()

        LOG.info("Setup complete. Containers will shut down on application exit.")
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(DevEnv::class.java)
    }
}