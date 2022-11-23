package com.stevdzasan.messagebarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stevdzasan.messagebar.ContentWithMessageBar
import com.stevdzasan.messagebar.MessageBarPosition
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.messagebarcompose.ui.theme.MessageBarComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageBarComposeTheme {
                val state = rememberMessageBarState()
                ContentWithMessageBar(
                    messageBarState = state,
                    position = MessageBarPosition.BOTTOM
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            state.addError(exception = Exception("Internet Unavailable."))
                        }) {
                            Text(text = "Error Bottom")
                        }
                        Button(onClick = {
                            state.addSuccess(message = "Successfully Updated.")
                        }) {
                            Text(text = "Success Bottom")
                        }
                    }
                }
            }
        }
    }
}