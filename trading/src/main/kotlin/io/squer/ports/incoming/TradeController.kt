package io.squer.view

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.squer.ports.outgoing.assets.AssetRepository
import io.squer.ports.outgoing.prices.AssetPriceRepository
import io.squer.ports.outgoing.trades.TradeEntity
import io.squer.ports.outgoing.trades.TradeRepository
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Controller
class TradeController(
    private val clock: Clock,
    private val assetRepository: AssetRepository,
    private val assetPriceRepository: AssetPriceRepository,
    private val tradeRepository: TradeRepository
) {
    @Post("/trade")
    suspend fun trade(request: TradeReqeuestDTO): TradeResponseDTO {
        val asset = assetRepository.findById(request.assetId)
            ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Asset not found.")
        val latestPrice = assetPriceRepository.findByAssetIdOrderByCreatedAtDesc(asset.id).getOrNull(0)?.price
            ?: throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No prices available")

        return tradeRepository.save(
            TradeEntity(
                id = UUID.randomUUID(),
                atPrice = latestPrice,
                amount = request.amountAsset,
                assetId = asset.id,
                createdAt = LocalDateTime.now(clock),
            )
        ).toTradeResponseDTO()
    }
}

data class TradeReqeuestDTO(
    val amountAsset: BigDecimal,
    val assetId: UUID,
)

data class TradeResponseDTO(
    val id: UUID,
    val amountAsset: BigDecimal,
    val assetId: UUID,
    val amountFiat: BigDecimal,
    val executedAt: Long,
)

fun TradeEntity.toTradeResponseDTO(): TradeResponseDTO {
    return TradeResponseDTO(
        id = this.id,
        amountAsset = this.amount,
        assetId = this.assetId,
        executedAt = this.createdAt.toEpochSecond(ZoneOffset.UTC),
        amountFiat = this.amount * this.atPrice
    )
}
