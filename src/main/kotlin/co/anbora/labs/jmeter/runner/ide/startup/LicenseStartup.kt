package co.anbora.labs.jmeter.runner.ide.startup

import co.anbora.labs.jmeter.runner.ide.license.CheckLicense
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.util.concurrency.AppExecutorUtil
import java.util.concurrent.TimeUnit

class LicenseStartup: ProjectActivity {
    override suspend fun execute(project: Project) {
        AppExecutorUtil.getAppScheduledExecutorService().schedule({
            val licensed = CheckLicense.isLicensed() ?: false

            if (!licensed && !project.isDisposed) {
                CheckLicense.requestLicense("Support JMeter Runner")
            }
        }, 5, TimeUnit.MINUTES)
    }
}