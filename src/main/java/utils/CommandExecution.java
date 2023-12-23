package utils;

import java.io.IOException;

public class CommandExecution {
    public void executeCommand(String[] command, String message) {
        try {
            // Create a ProcessBuilder with the command
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            // Start the process
            Process killProcess = processBuilder.start();
            // Wait for the process to complete (optional)
            int exitCode = killProcess.waitFor();
            System.out.println("Process exited with code: " + exitCode);
            System.out.println(message);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions as needed
        }
    }
}
