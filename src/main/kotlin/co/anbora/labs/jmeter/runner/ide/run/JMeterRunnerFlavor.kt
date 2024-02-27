package co.anbora.labs.jmeter.runner.ide.run

import com.intellij.openapi.extensions.ExtensionPointName


abstract class JMeterRunnerFlavor {

    abstract fun executable(): String

    abstract fun isApplicable(): Boolean

    companion object {
        private val EP_NAME: ExtensionPointName<JMeterRunnerFlavor> =
            ExtensionPointName.create("co.anbora.labs.jmeter.runner.jmeter-runner-intellij.runner")

        fun getApplicableFlavors(): List<JMeterRunnerFlavor> =
            EP_NAME.extensionList.filter { it.isApplicable() }
    }
}