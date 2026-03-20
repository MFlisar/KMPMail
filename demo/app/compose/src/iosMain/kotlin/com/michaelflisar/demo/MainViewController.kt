package com.michaelflisar.demo

import androidx.compose.ui.window.ComposeUIViewController
import com.michaelflisar.kmpmail.demo.DemoApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    DemoApp(
        appName = BuildKonfig.appName,
        platform = "iOS",
        ioContext = Dispatchers.IO
    )
}