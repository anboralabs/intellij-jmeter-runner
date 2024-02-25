package co.anbora.labs.jmeter.runner.ide.run

import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.openapi.project.Project

class JMeterConfigurationFactory(
    type: ConfigurationType
): ConfigurationFactory(type) {

    override fun getId(): String = this::class.java.simpleName

    override fun createTemplateConfiguration(project: Project): RunConfiguration {
        return JMeterRunConfiguration(project, this)
    }
}