package co.anbora.labs.jmeter.runner.ide.checker

import co.anbora.labs.jmeter.ide.checker.CheckerFlavor
import co.anbora.labs.jmeter.runner.ide.license.CheckLicense

class JMeterRunnerChecker: CheckerFlavor() {
    override fun check(): Boolean = CheckLicense.isLicensed() ?: false
}
