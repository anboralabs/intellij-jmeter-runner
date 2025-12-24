package co.anbora.labs.jmeter.runner.ide.startup

import co.anbora.labs.jmeter.ide.toolchain.JMeterKnownToolchainsState
import co.anbora.labs.jmeter.runner.ide.run.JMeterRunnerFlavor
import co.anbora.labs.jmeter.runner.ide.service.JMeterInstallationListenerService
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class CheckExecutionPermissions: ProjectActivity {

    override suspend fun execute(project: Project) {
        val runService = project.service<JMeterInstallationListenerService>()
        JMeterKnownToolchainsState.getInstance().subscribe(runService.subscriber)

        JMeterRunnerFlavor.getApplicableFlavors().forEach {
            it.addExecutionPermissions()
        }
    }
}