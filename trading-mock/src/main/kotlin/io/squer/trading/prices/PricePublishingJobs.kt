package io.squer.trading.prices

import io.micronaut.scheduling.annotation.Scheduled
import io.squer.trading.prices.mock.PricePublisher
import io.squer.trading.prices.models.Price
import jakarta.inject.Singleton
import kotlin.random.Random
import org.slf4j.LoggerFactory
import java.util.*

@Singleton
class PricePublishingJobs(private val pricePublisher: PricePublisher) {

    @Scheduled(fixedDelay = "10s")
    fun publishEveryThenSeconds() {
        val price = Price("baseId", "quoteId", Random.nextDouble(0.0, 100.0))
        LOG.info("Publishing new price [price={}]", price)
        pricePublisher.publish(UUID.randomUUID().toString(), price)
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(PricePublishingJobs::class.java)
    }

}