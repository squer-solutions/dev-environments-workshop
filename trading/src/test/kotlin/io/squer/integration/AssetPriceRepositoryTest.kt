package io.squer.integration

import io.squer.ports.outgoing.prices.AssetPriceEntity
import io.squer.ports.outgoing.prices.AssetPriceRepository
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime

class AssetPriceRepositoryTest: BaseIntegrationTest() {

    @Inject
    lateinit var sut: AssetPriceRepository

    @Test
    fun shouldStoreAssetPrice() {
        // arrange
        val price = BigDecimal.valueOf(100.00)
        val assetId = "asset-id"
        val createdAt = LocalDateTime.of(2023, 2, 23, 0, 0, 0)
        val expectedAssetPriceEntity = AssetPriceEntity(price, assetId, createdAt)
        runBlocking { sut.persistPrice(assetId, price, createdAt) }

        // act
        val actualAssetPriceEntity = runBlocking { sut.getLatestPrice(assetId) }

        // assert
        assertEquals(expectedAssetPriceEntity, actualAssetPriceEntity)
    }

}