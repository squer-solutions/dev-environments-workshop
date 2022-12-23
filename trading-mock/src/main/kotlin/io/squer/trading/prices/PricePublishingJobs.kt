package io.squer.trading.prices

import io.micronaut.scheduling.annotation.Scheduled
import io.squer.trading.prices.mock.PricePublisher
import io.squer.trading.prices.models.Price
import jakarta.inject.Singleton
import kotlin.random.Random
import org.slf4j.LoggerFactory
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Singleton
class PricePublishingJobs(private val pricePublisher: PricePublisher) {

    @Scheduled(fixedDelay = "10s")
    fun publishEveryTenSeconds() {
        assetIds.forEach {
            val price = Price(it, BigDecimal.valueOf(Random.nextDouble(0.0, 100.0)), LocalDateTime.now())
            LOG.info("Publishing new price [price={}]", price)
            pricePublisher.publish(it, price)
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PricePublishingJobs::class.java)
        private val assetIds = listOf(
                UUID.fromString("79fdc629-9d8d-46aa-9ddb-7a06350bc287"), // BTC
                UUID.fromString("caf419e9-4dfb-4816-b34c-a9a15f0fbd17"), // ETH
                UUID.fromString("379f204d-a81e-45e9-9e07-d7cf5c96e38f") // GRT
        )
    }

}
