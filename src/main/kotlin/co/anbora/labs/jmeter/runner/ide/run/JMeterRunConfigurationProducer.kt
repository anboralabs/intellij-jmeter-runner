package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.fileTypes.JmxFileType
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.actions.LazyRunConfigurationProducer
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.openapi.util.Ref
import com.intellij.openapi.util.io.FileUtil
import com.intellij.psi.PsiElement

class JMeterRunConfigurationProducer: LazyRunConfigurationProducer<JMeterRunConfiguration>() {
    override fun setupConfigurationFromContext(
        configuration: JMeterRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement>
    ): Boolean {
        val location = context.location ?: return false

        val testFile = location.virtualFile

        if (testFile == null || JmxFileType != testFile.fileType) {
            return false
        }

        configuration.testFile  = testFile.path
        configuration.setName(testFile.name)

        val propertyFile = testFile.parent.findChild("jmeter.properties")
        if (propertyFile != null) {
            configuration.propertyFile = propertyFile.path
        }

        var customParameters = ""

        val systemPropertyFile = testFile.parent.findChild("system.properties")
        if (systemPropertyFile != null) {
            customParameters += "--systemPropertyFile " + systemPropertyFile.path + " "
        }

        val userPropertyFile = testFile.parent.findChild("user.properties")
        if (userPropertyFile != null) {
            customParameters += "--addprop " + userPropertyFile.path + " "
        }

        val testPropertyFile = testFile.parent.findChild(testFile.nameWithoutExtension + ".properties")
        if (testPropertyFile != null) {
            customParameters += "--addprop " + testPropertyFile.path + " "
        }

        configuration.customParameters = customParameters.trim { it <= ' ' }
        return true
    }

    override fun isConfigurationFromContext(
        configuration: JMeterRunConfiguration,
        context: ConfigurationContext
    ): Boolean {
        val location = context.location ?: return false

        val file = location.virtualFile ?: return false

        return FileUtil.toSystemIndependentName(file.path) == FileUtil.toSystemIndependentName(configuration.testFile)
    }

    override fun getConfigurationFactory(): ConfigurationFactory {
        return JMeterConfigurationFactory(JMeterConfigurationType.INSTANCE)
    }
}