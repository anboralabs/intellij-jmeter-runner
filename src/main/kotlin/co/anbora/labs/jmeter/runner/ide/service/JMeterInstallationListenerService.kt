package co.anbora.labs.jmeter.runner.ide.service

import co.anbora.labs.jmeter.ide.toolchain.flow.JMeterToolchainSubscriber
import co.anbora.labs.jmeter.runner.ide.run.JMeterRunnerFlavor
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlin.collections.forEach

@Service(Service.Level.PROJECT)
class JMeterInstallationListenerService(val project: Project): Disposable {

    val subscriber = JMeterToolchainSubscriber {
        val runner = JMeterRunnerFlavor.getApplicableFlavors().firstOrNull()

        it.forEach { toolchain ->
            runner?.addExecutionPermissions(toolchain)
        }
    }

    override fun dispose() {
        subscriber.unsubscribe()
    }
}