package com.michaelflisar.kmpmail.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.michaelflisar.democomposables.layout.DemoCollapsibleRegion
import com.michaelflisar.democomposables.layout.DemoColumn
import com.michaelflisar.democomposables.layout.DemoRegion
import com.michaelflisar.democomposables.layout.rememberDemoExpandedRegions
import com.michaelflisar.kmpmail.Mail
import com.michaelflisar.kmpmail.MailAttachmentFile
import com.michaelflisar.kmpmail.startEmailChooser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.files.SystemTemporaryDirectory
import kotlinx.io.writeString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoApp(
    appName: String,
    platform: String,
    ioContext: CoroutineDispatcher,
) {
    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(appName) },
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

    var receiver by remember { mutableStateOf("") }
    val attachmentContent = "This is a test file."

    val noMailClientDialog = remember { mutableStateOf(false) }
    val localUriHandler = LocalUriHandler.current
    val clipboard = LocalClipboard.current
    val clipboardManager = LocalClipboardManager.current


    DemoColumn(
        modifier = modifier.padding(all = 16.dp)
    ) {
        DemoRegion("Platform: $platform")
        DemoCollapsibleRegion(
            title = "Demos", regionId = 1, state = regionState
        ) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = receiver,
                onValueChange = { receiver = it },
                label = { Text("Email") }
            )
            Button(
                onClick = {
                    // create a test file in temp directory
                    // no need to make sure its shareable on android, the library does handle that for you...
                    val tempFolderPath = SystemTemporaryDirectory
                    val tempFile = Path(tempFolderPath, "test.txt")

                    if (SystemFileSystem.exists(tempFile)) {
                        SystemFileSystem.delete(tempFile)
                    }
                    SystemFileSystem.sink(tempFile).use { sink ->
                        sink.buffered().use { writer ->
                            writer.writeString(attachmentContent)
                        }
                    }
                    // begin-snippet: mail
                    val mail = Mail(
                        receivers = listOf(receiver),
                        subject = "Feedback from $platform Demo App",
                        body = "Please write your feedback here...\n\n",
                        bodyIsHtml = false,
                        attachments = listOf(MailAttachmentFile(tempFile))
                    )
                    val success = mail.startEmailChooser("Select email app")
                    // end-snippet: mail
                    if (!success) {
                        scope.launch { snackbarHostState.showSnackbar("No email client found on device!") }
                        noMailClientDialog.value = true
                    }
                }
            ) {
                Text("Send simple feedback email")
            }
        }
    }

    if (noMailClientDialog.value) {
        Dialog(
            onDismissRequest = {
                noMailClientDialog.value = false
            }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                tonalElevation = 6.dp,
                modifier = Modifier.padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Kein Mail-Client gefunden!",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Es ist kein Mail-Client installiert oder eingerichtet. Du kannst die unten stehenden Informationen kopieren und manuell per Mail versenden.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        "Informationen:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    OutlinedTextField(
                        value = attachmentContent,
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        enabled = false,
                        label = { Text("Anhang") },
                        minLines = 3,
                        maxLines = 3
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    clipboardManager.setText(
                                        annotatedString = buildAnnotatedString {
                                            appendLine("Test File:")
                                            append(attachmentContent)
                                            appendLine("")
                                            appendLine("User Feedback:")
                                        }
                                    )
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Copy to Clipboard")
                        }
                        Button(
                            onClick = {
                                try {
                                    localUriHandler.openUri("mailto:$receiver")
                                } catch (e: Exception) {
                                    scope.launch { snackbarHostState.showSnackbar("Kein Mailto-Handler gefunden!") }
                                    println(e.message)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Mail öffnen")
                        }
                    }
                }
            }
        }
    }
}