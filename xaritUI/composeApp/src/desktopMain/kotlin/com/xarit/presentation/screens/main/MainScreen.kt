package com.xarit.presentation.screens.main

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.xarit.presentation.components.DirectoryOutlinedTextField
import com.xarit.presentation.components.XaritButton
import org.koin.compose.viewmodel.koinViewModel
import kotlin.reflect.KFunction2

@Composable
fun MainScreen(
    viewModel: MainViewModel = koinViewModel(),
    isWaitingResult: Boolean,
    setIsWaitingResult: (Boolean) -> Unit,
    setAwaitedResult: (String?) -> Unit
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainContent(
        state = state,
        onDestinationPathChange = viewModel::onDestinationPathChange,
        onDownloadCheckedChange = viewModel::onIsDownloadCheckedChange,
        onUploadCheckedChange = viewModel::onIsUploadCheckedChange,
        onRecursiveCheckedChange = viewModel::onIsRecursiveCheckedChange,
        onGenerateQRCodeCheckedChange = viewModel::onIsGenerateQRCodeCheckedChange,
        onTimeChange = viewModel::onTimeChange,
        onPortChange = viewModel::onPortChange,
        executeCommand = viewModel::executeCommand,
        setIsWaitingResult = setIsWaitingResult,
        setAwaitedResult = setAwaitedResult,
        isWaitingResult = isWaitingResult
    )
}

@Composable
fun MainContent(
    state: MainViewModel.MainScreenState,
    onDestinationPathChange: (String) -> Unit,
    onDownloadCheckedChange: (Boolean) -> Unit,
    onUploadCheckedChange: (Boolean) -> Unit,
    onRecursiveCheckedChange: (Boolean) -> Unit,
    onGenerateQRCodeCheckedChange: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit,
    onPortChange: (String) -> Unit,
    executeCommand: KFunction2<String, (String?) -> Unit, Unit>,
    setIsWaitingResult: (Boolean) -> Unit,
    setAwaitedResult: (String?) -> Unit,
    isWaitingResult: Boolean
){
    Box (
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(32.dp),
        ) {
            Text(
                text = "Welcome to Xarit, your minimalist HTTP file sharing tool",
                modifier = Modifier.padding(bottom = 32.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleLarge
            )
            DirectoryOutlinedTextField(
                state.destinationPath,
                onDestinationPathChange = onDestinationPathChange,
                enabled = !isWaitingResult
            )
            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Text(
                text = "Advanced",
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground
            )

            Card(
                modifier = Modifier.padding(top = 16.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Black
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isDownloadChecked,
                                onCheckedChange = onDownloadCheckedChange,
                                enabled = !isWaitingResult
                            )

                            Text("Download")
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isUploadChecked,
                                onCheckedChange = onUploadCheckedChange,
                                enabled = !isWaitingResult
                            )

                            Text("Upload")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isRecursiveChecked,
                                onCheckedChange = onRecursiveCheckedChange,
                                enabled = !isWaitingResult
                            )
                            Text("Allow Recursive")
                        }
                    }

                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = state.isGenerateQRCodeChecked,
                                onCheckedChange = onGenerateQRCodeCheckedChange,
                                enabled = !isWaitingResult
                            )

                            Text("Generate QR Code")
                        }
                        OutlinedTextField(
                            value = state.time,
                            onValueChange = onTimeChange,
                            label = {
                                Text("Server time Live")
                            },
                            enabled = !isWaitingResult
                        )

                        OutlinedTextField(
                            value = state.port,
                            onValueChange = onPortChange,
                            label = {
                                Text("Port")
                            },
                            enabled = !isWaitingResult
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(32.dp)
            ) {
                Spacer(
                    modifier = Modifier.weight(1f)
                )

                XaritButton(
                    onClick = {},
                    text = "Help"
                )

                Spacer(
                    modifier = Modifier.width(16.dp)
                )

                if(isWaitingResult){
                    XaritButton(
                        onClick = {
                            setIsWaitingResult(false)
                        },
                        text = "Stop Server",
                    )
                } else {
                    XaritButton(
                        onClick = {
                            setIsWaitingResult(true)
                            executeCommand("ipconfig", setAwaitedResult)
                        },
                        text = "Serve",
                        enabled = state.isUploadChecked || state.isDownloadChecked
                    )
                }
            }
        }
    }
}
