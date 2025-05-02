package com.xarit.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xarit.application.CommandExecutor
import com.xarit.application.CommandResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()
    private val commandExecutor: CommandExecutor = CommandExecutor()

    fun executeCommand(command: String, setAwaitedResult: (String?) -> Unit) {
        viewModelScope.launch {
            _state.value = _state.value.copy(result = commandExecutor.executeCommand(command))
            setAwaitedResult(_state.value.result?.output)
        }
    }

    fun onIsDownloadCheckedChange(isChecked: Boolean) {
        _state.value = _state.value.copy(isDownloadChecked = isChecked)
    }

    fun onIsUploadCheckedChange(isChecked: Boolean) {
        _state.value = _state.value.copy(isUploadChecked = isChecked)
    }

    fun onIsRecursiveCheckedChange(isChecked: Boolean) {
        _state.value = _state.value.copy(isRecursiveChecked = isChecked)
    }

    fun onIsGenerateQRCodeCheckedChange(isChecked: Boolean) {
        _state.value = _state.value.copy(isGenerateQRCodeChecked = isChecked)
    }

    fun onPortChange(port: String) {
        _state.value = _state.value.copy(port = port)
    }

    fun onDestinationPathChange(destinationPath: String) {
        _state.value = _state.value.copy(destinationPath = destinationPath)
    }

    fun onTimeChange(time: String) {
        _state.value = _state.value.copy(time = time)
    }

    data class MainScreenState(
        val destinationPath: String = "/home",
        val isDownloadChecked: Boolean = true,
        val isUploadChecked: Boolean = true,
        val isRecursiveChecked: Boolean = false,
        val isGenerateQRCodeChecked: Boolean = false,
        val port: String = "8080",
        val time: String = "300",
        val result: CommandResult? = null
    )
}