package io.squer.config

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Requires
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import io.squer.ports.outgoing.assets.AssetEntity
import io.squer.ports.outgoing.assets.AssetRepository
import io.squer.ports.outgoing.prices.AssetPriceEntity
import io.squer.ports.outgoing.prices.AssetPriceRepository
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.util.UUID

@Context
@Singleton
@Requires(notEnv = ["production", "staging"])
class TestDataInjector(
    private val assetRepository: AssetRepository,
    private val assetPriceRepository: AssetPriceRepository,
    private val clock: Clock
) {

    @EventListener
    fun onStartup(event: ServerStartupEvent) {
        runBlocking {
            listOf(
                assetRepository.save(
                    AssetEntity(
                        id = UUID.fromString("79fdc629-9d8d-46aa-9ddb-7a06350bc287"),
                        symbol = "BTC",
                    )
                ),
                assetRepository.save(
                    AssetEntity(
                        id = UUID.fromString("caf419e9-4dfb-4816-b34c-a9a15f0fbd17"),
                        symbol = "ETH",
                    )
                ),
                assetRepository.save(
                    AssetEntity(
                        id = UUID.fromString("379f204d-a81e-45e9-9e07-d7cf5c96e38f"),
                        symbol = "GRT",
                    )
                )
            ).map {
                assetPriceRepository.save(
                    AssetPriceEntity(
                        id = UUID.randomUUID(),
                        price = BigDecimal("12"),
                        assetId = it.id,
                        createdAt = LocalDateTime.now(clock)
                    )
                )
            }
        }
    }
}
