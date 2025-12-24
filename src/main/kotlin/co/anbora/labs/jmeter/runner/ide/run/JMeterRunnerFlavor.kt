package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchain
import com.intellij.openapi.extensions.ExtensionPointName


abstract class JMeterRunnerFlavor {

    abstract fun executable(): String

    abstract fun isApplicable(): Boolean

    abstract fun addExecutionPermissions(): Boolean

    abstract fun addExecutionPermissions(toolchain: JMeterToolchain): Boolean

    companion object {
        private val EP_NAME: ExtensionPointName<JMeterRunnerFlavor> =
            ExtensionPointName.create("co.anbora.labs.jmeter.runner.jmeter-runner-intellij.runner")

        fun getApplicableFlavors(): List<JMeterRunnerFlavor> =
            EP_NAME.extensionList.filter { it.isApplicable() }
    }
}