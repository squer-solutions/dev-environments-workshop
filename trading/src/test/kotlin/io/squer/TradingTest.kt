package io.squer

import io.micronaut.runtime.EmbeddedApplication
import io.squer.integration.BaseIntegrationTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TradingTest: BaseIntegrationTest() {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun testItWorks() {
        Assertions.assertTrue(application.isRunning)
    }
}
