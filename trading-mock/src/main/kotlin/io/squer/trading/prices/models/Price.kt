package io.squer.trading.prices.models

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class Price(val assetId: UUID, val price: BigDecimal, val createdAt: LocalDateTime)
