package io.squer.integration

import io.squer.ports.outgoing.assets.AssetEntity
import io.squer.ports.outgoing.assets.AssetRepository
import jakarta.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class AssetRepositoryTest: BaseIntegrationTest() {

    @Inject
    lateinit var sut: AssetRepository

    @Test
    fun shouldStoreAsset() {
        // arrange
        val id = UUID.randomUUID()
        val symbol = "symbol"
        val expectedAssetEntity = AssetEntity(id, symbol)
        runBlocking { sut.save(AssetEntity(id, symbol)) }

        // act
        val actualAssetEntity = runBlocking { sut.findById(id) }

        // assert
        assertEquals(expectedAssetEntity.id, actualAssetEntity?.id)
    }

}