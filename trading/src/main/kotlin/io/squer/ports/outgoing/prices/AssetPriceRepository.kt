package io.squer.ports.outgoing.prices

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import java.math.BigDecimal
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.time.LocalDateTime
import java.util.*

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface AssetPriceRepository : CoroutineCrudRepository<AssetPriceEntity, UUID> {
    suspend fun findByAssetIdOrderByCreatedAtDesc(assetId: UUID): List<AssetPriceEntity>
}

@MappedEntity
data class AssetPriceEntity(
    @field:Id
    var id: UUID,
    val price: BigDecimal,
    val assetId: UUID,
    val createdAt: LocalDateTime
)