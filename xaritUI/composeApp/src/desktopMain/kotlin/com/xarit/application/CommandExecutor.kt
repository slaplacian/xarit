package com.xarit.application

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class CommandExecutor {
    // Execute a command and return its output
    suspend fun executeCommand(command: String): CommandResult = withContext(Dispatchers.IO) {
        try {
            val process = ProcessBuilder()
                .command(command.split(" "))
                .redirectErrorStream(true)
                .start()

            val reader = BufferedReader(InputStreamReader(process.inputStream, StandardCharsets.UTF_8))
            val output = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                output.append(line).append("\n")
            }

            val exitCode = process.waitFor()
            CommandResult(exitCode == 0, output.toString(), if (exitCode != 0) "Exit code: $exitCode" else "")
        } catch (e: Exception) {
            CommandResult(false, "", e.message ?: "Unknown error")
        }
    }
}

data class CommandResult(
    val success: Boolean,
    val output: String,
    val errorMessage: String = ""
)