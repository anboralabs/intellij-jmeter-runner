package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchainService
import com.intellij.execution.Executor
import com.intellij.execution.configurations.LocatableConfigurationBase
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.configurations.RuntimeConfigurationException
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.components.service
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.JDOMExternalizer
import org.apache.commons.lang.StringUtils
import org.jdom.Element
import java.io.File

class JMeterRunConfiguration(
    project: Project,
    configurationFactory: JMeterConfigurationFactory
): LocatableConfigurationBase<Any>(project, configurationFactory) {

    private val toolchain = service<JMeterToolchainService>()

    var testFile: String = ""
    var propertyFile: String = ""
    var properties = LinkedHashMap<String, String>()
    var jvmParameters: String = ""
    var customParameters: String = ""
    var workingDirectory: String = ""

    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState {
        return JMeterRunProfileState(environment, this)
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        return JMeterRunConfigurationEditor(project)
    }

    override fun checkSettingsBeforeRun() {
        if (!File(testFile).exists()) {
            throw RuntimeConfigurationException("Test file not found")
        }

        if (!StringUtils.isBlank(propertyFile) && !File(propertyFile).exists()) {
            throw RuntimeConfigurationException("Properties file not found")
        }

        if (!toolchain.toolchain().isValid()) {
            throw RuntimeConfigurationException("JMeter not found")
        }
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)

        testFile = JDOMExternalizer.readString(element, "testFile").orEmpty()
        propertyFile = JDOMExternalizer.readString(element, "propertyFile").orEmpty()
        jvmParameters = JDOMExternalizer.readString(element, "jvmParameters").orEmpty()
        customParameters = JDOMExternalizer.readString(element, "customParameters").orEmpty()
        workingDirectory = JDOMExternalizer.readString(element, "workingDirectory").orEmpty()

        val properties = this.properties
        JDOMExternalizer.readMap(element, properties, "properties", "property")
        this.properties = properties
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)

        JDOMExternalizer.write(element, "testFile", testFile)
        JDOMExternalizer.write(element, "propertyFile", propertyFile)
        JDOMExternalizer.write(element, "jvmParameters", jvmParameters)
        JDOMExternalizer.write(element, "customParameters", customParameters)
        JDOMExternalizer.write(element, "workingDirectory", workingDirectory)
        JDOMExternalizer.writeMap(element, properties, "properties", "property")
    }
}