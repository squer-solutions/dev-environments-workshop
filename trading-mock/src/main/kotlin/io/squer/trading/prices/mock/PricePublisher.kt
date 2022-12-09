package io.squer.trading.prices.mock

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.squer.trading.prices.kafka.Constants
import io.squer.trading.prices.models.Price

@KafkaClient
interface PricePublisher {

    @Topic(Constants.SQUER_TRADING_PRICES)
    fun publish(@KafkaKey key: String, price: Price) : Price

}