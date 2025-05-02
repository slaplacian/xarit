package com.xarit.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import javax.swing.JFileChooser

@Composable
fun DirectoryOutlinedTextField(
    destinationPath: String,
    onDestinationPathChange: (String) -> Unit,
    enabled : Boolean
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = destinationPath,
            onValueChange = onDestinationPathChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            enabled = enabled
        )

        Spacer(modifier = Modifier.width(8.dp))

        XaritButton (
            onClick = {
                val directory = selectDirectory()
                if (directory != null) {
                    onDestinationPathChange(directory)
                }
            },
            text = "Browse...",
            enabled = enabled
        )
    }
}

fun selectDirectory(): String? {
    val fileChooser = JFileChooser().apply {
        fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
        dialogTitle = "Select Destination Folder"
        approveButtonText = "Select"
    }

    return when (fileChooser.showOpenDialog(null)) {
        JFileChooser.APPROVE_OPTION -> fileChooser.selectedFile.absolutePath
        else -> null
    }
}