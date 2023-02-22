package io.squer.ports.outgoing.prices

import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.CachePut
import io.micronaut.cache.annotation.Cacheable
import jakarta.inject.Singleton
import java.math.BigDecimal
import java.time.LocalDateTime

@Singleton
@CacheConfig("prices")
class AssetPriceRepository {
    @Cacheable
    suspend fun getLatestPrice(assetId: String): AssetPriceEntity? {
        return null // on cache miss
    }

    @CachePut(parameters = ["assetId", "createdAt"])
    suspend fun persistPrice(
        assetId: String,
        price: BigDecimal,
        createdAt: LocalDateTime
    ): AssetPriceEntity {
        return AssetPriceEntity(
            price = price,
            assetId = assetId,
            createdAt = createdAt
        )
    }
}

data class AssetPriceEntity(
    val price: BigDecimal,
    val assetId: String,
    val createdAt: LocalDateTime
)
