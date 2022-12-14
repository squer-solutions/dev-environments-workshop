package io.squer.config

import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton
import java.time.Clock

@Factory
class BeanConfig {
    @Singleton
    fun clock(): Clock {
        return Clock.systemUTC();
    }
}