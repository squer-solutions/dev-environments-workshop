package io.squer.integration

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(BeforeAllExtension::class)
@MicronautTest(transactional = false)
abstract class BaseIntegrationTest