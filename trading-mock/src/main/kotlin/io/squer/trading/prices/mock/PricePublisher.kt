package io.squer.trading.prices.mock

import io.micronaut.configuration.kafka.annotation.KafkaClient
import io.micronaut.configuration.kafka.annotation.KafkaKey
import io.micronaut.configuration.kafka.annotation.Topic
import io.squer.trading.prices.kafka.Constants
import io.squer.trading.prices.models.Price
import java.util.UUID

@KafkaClient
interface PricePublisher {

    @Topic(Constants.SQUER_TRADING_PRICES)
    fun publish(@KafkaKey key: UUID, price: Price) : Price

}
