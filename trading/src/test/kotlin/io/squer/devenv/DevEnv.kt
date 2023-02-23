package io.squer.devenv

import io.squer.devenv.containers.*
import org.testcontainers.containers.Network.newNetwork

fun main() {

    init()

    while (true) {
    }

}

fun init() {
    val defaultNetwork = newNetwork()

    // init containers
    val postgres = Postgres(defaultNetwork)
    val redis = Redis(defaultNetwork)
    val redisCommander = RedisCommander(defaultNetwork)
    val redpanda = Redpanda(defaultNetwork)
    val redpandaConsole = RedpandaConsole(defaultNetwork)
    val pricesMock = PricesMock(defaultNetwork)

    // starting containers
    postgres.start()

    redis.start()

    redisCommander.start()

    redpanda.start()
    redpanda.createTopics()

    redpandaConsole.start()

    pricesMock.start()

    // done

}