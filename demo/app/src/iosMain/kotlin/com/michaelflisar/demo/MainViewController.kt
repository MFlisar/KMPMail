package com.michaelflisar.demo

import androidx.compose.ui.window.ComposeUIViewController
import com.michaelflisar.feedbackmanager.demo.DemoApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * iOS entry point used by the Xcode demo project (demo/xcode).
 */
fun MainViewController() = ComposeUIViewController {
    DemoApp(
        platform = "iOS",
        ioContext = Dispatchers.IO
    )
}
