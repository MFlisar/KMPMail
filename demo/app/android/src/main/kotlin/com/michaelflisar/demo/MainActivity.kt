package com.michaelflisar.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.michaelflisar.kmpmail.demo.BuildKonfig
import com.michaelflisar.kmpmail.demo.DemoApp
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoApp(
                appName = BuildKonfig.appName,
                platform = "Android",
                ioContext = Dispatchers.IO
            )
        }
    }
}
