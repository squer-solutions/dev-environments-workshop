package io.squer.ports.outgoing.assets

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface AssetRepository : CoroutineCrudRepository<AssetEntity, UUID>

@MappedEntity
class AssetEntity(
    @field:Id
    var id: UUID,
    val symbol: String,
)
