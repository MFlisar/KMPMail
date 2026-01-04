package com.michaelflisar.feedbackmanager.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaelflisar.democomposables.layout.DemoCollapsibleRegion
import com.michaelflisar.democomposables.layout.DemoColumn
import com.michaelflisar.democomposables.layout.DemoRegion
import com.michaelflisar.democomposables.layout.rememberDemoExpandedRegions
import com.michaelflisar.kmpmail.Feedback
import com.michaelflisar.kmpmail.FeedbackFile
import com.michaelflisar.kmpmail.executeTest
import com.michaelflisar.kmpmail.startEmailChooser
import com.michaelflisar.kmpmail.shared.resources.Res
import com.michaelflisar.kmpmail.shared.resources.app_name
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.io.writeString
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoApp(
    platform: String,
    ioContext: CoroutineDispatcher,
) {
    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            topBar = {
                val appName = stringResource(Res.string.app_name)
                TopAppBar(
                    title = { Text("$appName Demo") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            DemoContent(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                platform = platform,
                ioContext = ioContext,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Composable
private fun DemoContent(
    modifier: Modifier,
    platform: String,
    ioContext: CoroutineDispatcher,
    snackbarHostState: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()
    val regionState = rememberDemoExpandedRegions(ids = listOf(1, 2))

    DemoColumn(
        modifier = modifier.padding(all = 16.dp)
    ) {
        DemoRegion("Platform: $platform")
        DemoCollapsibleRegion(
            title = "Demos", regionId = 1, state = regionState
        ) {
            Text("Test: " + executeTest())
            val mail = remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = mail.value,
                onValueChange = { mail.value = it },
                label = { Text("Email") }
            )
            Button(
                onClick = {
                    // create a test file in temp directory
                    // no need to make sure its shareable on android, the library does handle that for you...
                    val tempFolderPath = SystemTemporaryDirectory
                    val tempFile = Path(tempFolderPath, "feedbackmanager_demo.txt")
                    if (SystemFileSystem.exists(tempFile)) {
                        SystemFileSystem.delete(tempFile)
                    }
                    SystemFileSystem.sink(tempFile).use { sink ->
                        sink.buffered().use { writer ->
                            writer.writeString("This is a test file for FeedbackManager.")
                        }
                    }
                    // begin-snippet: feedback
                    val mail = mail.value
                    val feedback = Feedback(
                        receivers = listOf("mflisar.development@gmail.com"),
                        subject = "Feedback from $platform Demo App",
                        text = "Please write your feedback here...\n\n",
                        textIsHtml = false,
                        attachments = listOf(FeedbackFile(tempFile))
                    )
                    feedback.startEmailChooser("Select email app")
                    // end-snippet: feedback
                }
            ) {
                Text("Send simple feedback email")
            }
        }
    }
}