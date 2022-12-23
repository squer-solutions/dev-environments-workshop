package io.squer.ports.outgoing.trades

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface TradeRepository : CoroutineCrudRepository<TradeEntity, UUID>

@MappedEntity
class TradeEntity(
    @field:Id
    val id: UUID,
    val amount: BigDecimal,
    val atPrice: BigDecimal,
    val createdAt: LocalDateTime,
    val assetId: UUID,
)
