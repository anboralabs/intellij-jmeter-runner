package co.anbora.labs.jmeter.runner.ide.run

import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project
import javax.swing.JComponent


class JMeterRunConfigurationEditor(
    private val project: Project
): SettingsEditor<JMeterRunConfiguration>() {

    private lateinit var form: JMeterRunConfigurationForm

    override fun resetEditorFrom(s: JMeterRunConfiguration) {
        form.testFile = s.testFile
        form.propertyFile = s.propertyFile
        form.setProperties(s.properties)
        form.jvmParameters = s.jvmParameters
        form.customParameters = s.customParameters
        form.workingDirectory = s.workingDirectory
    }

    override fun applyEditorTo(s: JMeterRunConfiguration) {
        s.testFile = form.testFile
        s.propertyFile = form.propertyFile
        s.properties = form.properties
        s.jvmParameters = form.jvmParameters
        s.customParameters = form.customParameters
        s.workingDirectory = form.workingDirectory
    }

    override fun createEditor(): JComponent {
        form = JMeterRunConfigurationForm(project)
        return form.rootPanel
    }
}