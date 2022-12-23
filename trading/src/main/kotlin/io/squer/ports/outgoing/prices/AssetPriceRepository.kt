package io.squer.ports.outgoing.prices

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface AssetPriceRepository : CoroutineCrudRepository<AssetPriceEntity, UUID> {
    suspend fun findByAssetIdOrderByCreatedAtDesc(assetId: UUID): List<AssetPriceEntity>
}

@MappedEntity
data class AssetPriceEntity(
    @field:Id
    val id: UUID,
    val price: BigDecimal,
    val assetId: UUID,
    val createdAt: LocalDateTime
)