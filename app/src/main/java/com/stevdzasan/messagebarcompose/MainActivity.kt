package com.stevdzasan.messagebarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.messagebarcompose.ui.theme.MessageBarComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageBarComposeTheme {
                val state = rememberMessageBarState()
                ContentWithMessageBar(messageBarState = state) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surface),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            state.addError(exception = Exception("Internet Unavailable."))
                        }) {
                            Text(text = "Error #1")
                        }
                        Button(onClick = {
                            state.addError(
                                exception = Exception("This is supposed to be a very long error message.")
                            )
                        }) {
                            Text(text = "Error #2")
                        }
                        Button(onClick = {
                            state.addSuccess(message = "Successful.")
                        }) {
                            Text(text = "Success #1")
                        }
                        Button(onClick = {
                            state.addSuccess(message = "Successfully Updated.")
                        }) {
                            Text(text = "Success #2")
                        }
                    }
                }
            }
        }
    }
}