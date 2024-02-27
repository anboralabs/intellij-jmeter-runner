package co.anbora.labs.jmeter.runner.ide.run.flavor

import co.anbora.labs.jmeter.runner.ide.run.JMeterRunnerFlavor
import com.intellij.openapi.util.SystemInfo

class WinJMeterRunner: JMeterRunnerFlavor() {
    override fun executable(): String = "jmeter.bat"

    override fun isApplicable(): Boolean = SystemInfo.isWindows
}