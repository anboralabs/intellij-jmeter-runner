package co.anbora.labs.jmeter.runner.ide.run

import co.anbora.labs.jmeter.ide.icons.JmeterIcons
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationType
import com.intellij.execution.configurations.ConfigurationTypeUtil
import javax.swing.Icon

class JMeterConfigurationType: ConfigurationType {

    private val factory = JMeterConfigurationFactory(this)

    override fun getDisplayName(): String = "JMeter"

    override fun getConfigurationTypeDescription(): String = "Run JMeter test plans"

    override fun getIcon(): Icon = JmeterIcons.FILE

    override fun getId(): String = ID

    override fun getConfigurationFactories(): Array<ConfigurationFactory> = arrayOf(factory)

    companion object {
        val INSTANCE: ConfigurationType
            get() = ConfigurationTypeUtil.findConfigurationType(JMeterConfigurationType::class.java)

        const val ID = "co.anbora.labs.jmeter.runner.jmeter-runner-intellij.JMeterConfigurationType"
    }
}