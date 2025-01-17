package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.ide.settings.JMeterProjectSettingsConfigurable
import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchainService
import co.anbora.labs.jmeter.runner.ide.license.CheckLicense
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.KillableProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.components.service
import kotlin.io.path.pathString


class JMeterRunProfileState(
    environment: ExecutionEnvironment,
    private val runConfiguration: JMeterRunConfiguration
): CommandLineState(environment) {

    private val toolchain = service<JMeterToolchainService>()

    override fun startProcess(): ProcessHandler {

        if (!toolchain.toolchain().isValid()) {
            JMeterProjectSettingsConfigurable.show(runConfiguration.project)
        }

        if (!toolchain.toolchain().isValid()) {
            throw RuntimeException("JMeter not found! Please Setup.")
        }

        val licensed = CheckLicense.isLicensed() ?: true

        if (!licensed) {
            CheckLicense.requestLicense("Please, Support my work!")
        }

        val runner = JMeterRunnerFlavor.getApplicableFlavors().firstOrNull()
            ?: throw RuntimeException("Invalid OS command to launch JMeter, please create an issue ticket.")

        val exe = toolchain.toolchain().stdBinDir()?.resolve(runner.executable())?.pathString

        val properties = runConfiguration.properties.map {
            "-J ${it.key}=${it.value}"
        }

        val addProp = if (runConfiguration.propertyFile.isEmpty()) {
            emptyList()
        } else {
            listOf("--addprop ${runConfiguration.propertyFile}")
        }

        if (runConfiguration.testFile.isEmpty()) {
            throw RuntimeException("Invalid JMeter test plan.")
        }

        val commandLine = GeneralCommandLine(exe, "--testfile", runConfiguration.testFile,
            *addProp.toTypedArray(), *properties.toTypedArray())

        return KillableProcessHandler(commandLine)
    }
}