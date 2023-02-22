package io.squer.ports.incoming

import io.micronaut.configuration.kafka.annotation.KafkaListener
import io.micronaut.configuration.kafka.annotation.Topic
import io.squer.ports.outgoing.prices.AssetPriceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.launch
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@KafkaListener
class PriceListener(
    private val assetPriceRepository: AssetPriceRepository
) {
    @Topic(SQUER_TRADING_PRICES)
    fun receivePrice(price: Price): Mono<Unit> {
        return Mono.fromCallable {
            CoroutineScope(coroutineContext).launch {
                assetPriceRepository.persistPrice(
                    price = price.price,
                    assetId = price.assetId.toString(),
                    createdAt = LocalDateTime.now()
                )
            }
        }
    }

    companion object {
        const val SQUER_TRADING_PRICES = "squer-trading-prices"
    }
}

data class Price(val assetId: UUID, val price: BigDecimal, val createdAt: LocalDateTime)
