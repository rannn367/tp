package seedu.address;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
/**
 * Test class for MainApp using process-based testing.
 */
public class MainAppTest {

    private static final int PROCESS_TIMEOUT = 10;

    /**
     * Constructs the command for launching the JavaFX application in a separate process.
     */
    private List<String> createApplicationLaunchCommand(String appName) {
        final String workerJavaCmd = System.getProperty("worker.java.cmd", "java");
        final String workerPatchModuleFile = System.getProperty("worker.patchmodule.file");
        final String workerClassPath = System.getProperty("worker.classpath.file");
        final String classpath = System.getProperty("java.class.path");

        List<String> cmd = new ArrayList<>();
        cmd.add(workerJavaCmd);

        if (workerPatchModuleFile != null) {
            cmd.add("@" + workerPatchModuleFile);
        } else {
            System.out.println("Warning: no worker.patchmodule passed to unit test");
        }

        cmd.add("-cp");
        if (workerClassPath != null) {
            cmd.add(workerClassPath + ":" + classpath);
        } else {
            cmd.add(classpath);
        }

        cmd.add(appName);
        return cmd;
    }

    /**
     * Tests the default application behavior using a separate process.
     */
    @Test
    public void testApps() {
        String[] toTest = {
            "seedu.address.TestAppLauncher",
            "seedu.address.DefaultAppLauncher"
        };

        for (String appName: toTest) {
            List<String> command = createApplicationLaunchCommand(appName);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.inheritIO();

            try {
                Process process = processBuilder.start();
                boolean finished = process.waitFor(PROCESS_TIMEOUT, TimeUnit.SECONDS);
                if (!finished) {
                    process.destroy();
                    fail("Test application failed to start!");
                }

                int exitCode = process.exitValue();
                if (exitCode != 0) {
                    fail("Test application exited with error code: " + exitCode);
                }
            } catch (IOException | InterruptedException e) {
                fail("Failed to launch test application: " + e.getMessage());
            }
        }
    }
}
