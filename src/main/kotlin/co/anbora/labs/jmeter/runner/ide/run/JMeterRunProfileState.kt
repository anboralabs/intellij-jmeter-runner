package co.anbora.labs.jmeter.runner.ide.run

import com.intellij.execution.configurations.CommandLineState
import com.intellij.execution.process.ProcessHandler
import com.intellij.execution.runners.ExecutionEnvironment


class JMeterRunProfileState(
    environment: ExecutionEnvironment
): CommandLineState(environment) {
    override fun startProcess(): ProcessHandler {
        TODO("Not yet implemented")
    }
}