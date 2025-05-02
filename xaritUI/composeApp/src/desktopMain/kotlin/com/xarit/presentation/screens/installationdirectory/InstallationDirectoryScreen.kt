package com.xarit.presentation.screens.installationdirectory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import javax.swing.JFileChooser

@Composable
fun InstallationDirectoryScreen() {
    var destinationPath by remember { mutableStateOf("C:\\YourApp") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with title
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8E8E8))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Select Destination Location",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Where should YourApp be installed?",
                        fontSize = 14.sp
                    )
                }

                // You would need an actual folder icon resource
                // For the artifact, we'll use a placeholder
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.LightGray)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("📁")
                }
            }

            // Main content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                // Folder icon and description
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFFE082))
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📁")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Setup will install YourApp into the following folder:")
                }

                // Directory selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = destinationPath,
                        onValueChange = { destinationPath = it },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            val directory = selectDirectory()
                            if (directory != null) {
                                destinationPath = directory
                            }
                        }
                    ) {
                        Text("Browse...")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "At least 10 MB of free disk space is required.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }

            // Bottom buttons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8E8E8))
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Button(
                        onClick = { /* Back logic */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0E0E0))
                    ) {
                        Text("< Back")
                    }

                    Button(
                        onClick = { /* Next logic with selected path */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2196F3))
                    ) {
                        Text("Next >", color = Color.White)
                    }

                    Button(
                        onClick = { /* Cancel logic */ },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0E0E0))
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}


// Function to show native directory chooser dialog
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