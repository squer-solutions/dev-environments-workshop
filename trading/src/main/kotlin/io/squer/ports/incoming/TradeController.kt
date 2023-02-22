package io.squer.ports.incoming

import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.exceptions.HttpStatusException
import io.squer.ports.outgoing.assets.AssetRepository
import io.squer.ports.outgoing.prices.AssetPriceRepository
import io.squer.ports.outgoing.trades.TradeEntity
import io.squer.ports.outgoing.trades.TradeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.math.BigDecimal
import java.time.Clock
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@Controller
class TradeController(
    private val clock: Clock,
    private val assetRepository: AssetRepository,
    private val assetPriceRepository: AssetPriceRepository,
    private val tradeRepository: TradeRepository
) {
    @Post("/trade")
    suspend fun trade(request: TradeRequestDTO): TradeResponseDTO {
        /* By default, Coroutines execute immediately. Using async { } allows me to schedule both Coroutines
        to run concurrently. */
        return coroutineScope {
            val confirmedAssetId = async {
                assetRepository.findById(UUID.fromString(request.assetId))?.id
                    ?: throw HttpStatusException(HttpStatus.NOT_FOUND, "Asset not found.")
            }
            val latestPrice = async {
                assetPriceRepository.getLatestPrice(request.assetId)?.price ?: throw HttpStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No prices available")
            }

            return@coroutineScope tradeRepository.save(
                TradeEntity(
                    id = UUID.randomUUID(),
                    assetId = confirmedAssetId.await(),
                    atPrice = latestPrice.await(),
                    amount = request.amountAsset,
                    createdAt = LocalDateTime.now(clock),
                )
            ).toTradeResponseDTO()
        }
    }
}

data class TradeRequestDTO(
    val amountAsset: BigDecimal,
    val assetId: String,
)

data class TradeResponseDTO(
    val id: UUID,
    val amountAsset: BigDecimal,
    val assetId: String,
    val amountFiat: BigDecimal,
    val executedAt: Long,
)

fun TradeEntity.toTradeResponseDTO(): TradeResponseDTO {
    return TradeResponseDTO(
        id = this.id,
        amountAsset = this.amount,
        assetId = this.assetId.toString(),
        executedAt = this.createdAt.toEpochSecond(ZoneOffset.UTC),
        amountFiat = this.amount * this.atPrice
    )
}
