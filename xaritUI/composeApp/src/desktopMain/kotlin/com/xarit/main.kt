package com.xarit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.xarit.di.commonModule
import com.xarit.presentation.screens.comandresult.ComandResultScreen
import com.xarit.presentation.screens.main.MainScreen
import com.xarit.presentation.ui.theme.MyAppTheme
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.KoinApplication
import xaritui.composeapp.generated.resources.Res
import xaritui.composeapp.generated.resources.compose_multiplatform

fun main() = application {
    val windowState = rememberWindowState(
        position = WindowPosition.PlatformDefault,
    )

    var isWaitingResult by remember { mutableStateOf(false) }
    var awaitedResult by remember { mutableStateOf("") }

    Window(
        onCloseRequest = ::exitApplication,
        title = "XaritUI",
        icon = painterResource(Res.drawable.compose_multiplatform),
        state = windowState,
        resizable = false,
    ) {
        MyAppTheme {
            KoinApplication(
                application = {
                    modules(commonModule)
                }
            ) {
                MainScreen(
                    isWaitingResult = isWaitingResult,
                    setIsWaitingResult = {
                        isWaitingResult = it
                    },
                    setAwaitedResult = {
                        awaitedResult = it ?: "No result"
                    }
                )
            }
        }
    }

    if(isWaitingResult){
        Window(
            onCloseRequest = {
                isWaitingResult = false
            },
            title = "XaritUI Result",
            icon = painterResource(Res.drawable.compose_multiplatform),
        ) {
            MyAppTheme {
//                KoinApplication(
//                    application = {
//                        modules(commonModule)
//                    }
//                ) {
                    ComandResultScreen(
                        result = awaitedResult
                    )
//                }
            }
        }
    }
}
