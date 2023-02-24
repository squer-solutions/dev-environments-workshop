package io.squer.integration

import io.squer.devenv.DevEnv
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtensionContext

class BeforeAllExtension: BeforeAllCallback{

    override fun beforeAll(context: ExtensionContext?) {
        if(!started) {
            started = true
            devEnv.setupDevEnv(true)
        }
    }

    companion object {
        private val devEnv = DevEnv()
        private var started = false
    }
}