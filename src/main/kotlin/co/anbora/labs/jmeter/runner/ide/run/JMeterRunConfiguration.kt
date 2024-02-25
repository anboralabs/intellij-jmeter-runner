package co.anbora.labs.jmeter.runner.ide.run

import com.intellij.execution.Executor
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.ide.actions.runAnything.execution.RunAnythingRunProfileState
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class JMeterRunConfiguration(
    project: Project,
    configurationFactory: JMeterConfigurationFactory
): LocatableConfigurationBase<Any>(project, configurationFactory) {

    var testFile: String = ""
    var propertyFile: String = ""
    var properties = LinkedHashMap<String, String>()
    var jvmParameters: String = ""
    var customParameters: String = ""
    var workingDirectory: String = ""

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return RunAnythingRunProfileState(environment, "jmeter")
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return JMeterRunConfigurationEditor(project)
    }
}