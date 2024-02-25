package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchainService
import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.configurations.GeneralCommandLine
import com.intellij.execution.process.KillableProcessHandler
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.ide.actions.runAnything.execution.RunAnythingRunProfileState
import com.intellij.openapi.components.service


class JMeterRunProfileState(
    environment: ExecutionEnvironment,
    private val runConfiguration: JMeterRunConfiguration
): CommandLineState(environment) {

    private val toolchain = service<JMeterToolchainService>()

    override fun startProcess(): ProcessHandler {

        val exe = toolchain.toolchain().stdBinDir()?.findChild("jmeter")?.path

        val properties = runConfiguration.properties.map {
            "-J ${it.key}=${it.value}"
        }

        val addProp = if (runConfiguration.propertyFile.isEmpty()) {
            emptyList()
        } else {
            listOf("--addprop ${runConfiguration.propertyFile}")
        }

        val commandLine = GeneralCommandLine(exe, "--testfile", runConfiguration.testFile,
            *addProp.toTypedArray(), *properties.toTypedArray())

        return KillableProcessHandler(commandLine)
    }
}