package co.anbora.labs.jmeter.runner.ide.run.flavor

import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchain
import co.anbora.labs.jmeter.ide.toolchain.JMeterToolchainService
import co.anbora.labs.jmeter.runner.ide.run.JMeterRunnerFlavor
import com.intellij.openapi.components.service
import com.intellij.openapi.util.SystemInfo
import java.nio.file.Files
import java.nio.file.attribute.PosixFilePermission

class UnixJMeterRunner: JMeterRunnerFlavor() {
    override fun executable(): String = "jmeter"

    override fun isApplicable(): Boolean = SystemInfo.isUnix

    override fun addExecutionPermissions(): Boolean {
        val toolchain = service<JMeterToolchainService>()

        return addExecutionPermissions(toolchain.toolchain())
    }

    override fun addExecutionPermissions(toolchain: JMeterToolchain): Boolean {
        val exe = toolchain.stdBinDir()?.resolve(executable()) ?: return false

        try {
            if (!Files.isExecutable(exe)) {
                // Unix/Mac
                val permissions = Files.getPosixFilePermissions(exe)
                permissions.add(PosixFilePermission.OWNER_EXECUTE)
                Files.setPosixFilePermissions(exe, permissions)
            }
        } catch (e: Exception) {
            return false
        }
        return true
    }
}