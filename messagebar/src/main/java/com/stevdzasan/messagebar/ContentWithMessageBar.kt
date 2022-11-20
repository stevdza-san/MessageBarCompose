package com.stevdzasan.messagebar

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.concurrent.schedule

@Composable
fun rememberMessageBarState(): MessageBarState {
    return remember { MessageBarState() }
}

@Composable
fun ContentWithMessageBar(
    modifier: Modifier = Modifier,
    messageBarState: MessageBarState,
    visibilityDuration: Long = 3000L,
    showToastOnCopy: Boolean = false,
    successIcon: ImageVector = Icons.Default.Check,
    errorIcon: ImageVector = Icons.Default.Warning,
    contentBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    successContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    successContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    errorContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    errorContentColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    verticalPadding: Dp = 12.dp,
    horizontalPadding: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = contentBackgroundColor)
    ) {
        content()
        MessageBarComponent(
            messageBarState = messageBarState,
            visibilityDuration = visibilityDuration,
            successIcon = successIcon,
            errorIcon = errorIcon,
            successContainerColor = successContainerColor,
            successContentColor = successContentColor,
            errorContainerColor = errorContainerColor,
            errorContentColor = errorContentColor,
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding,
            showToastOnCopy = showToastOnCopy
        )
    }
}

@Composable
private fun MessageBarComponent(
    messageBarState: MessageBarState,
    visibilityDuration: Long,
    successIcon: ImageVector = Icons.Default.Check,
    errorIcon: ImageVector = Icons.Default.Warning,
    successContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    successContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    errorContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    errorContentColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    showToastOnCopy: Boolean = false
) {
    var showMessageBar by remember { mutableStateOf(false) }
    val error by rememberUpdatedState(newValue = messageBarState.error?.message)
    val message by rememberUpdatedState(newValue = messageBarState.success)

    DisposableEffect(key1 = messageBarState.updated) {
        showMessageBar = true
        val timer = Timer("Animation Timer", true)
        timer.schedule(visibilityDuration) {
            showMessageBar = false
        }
        onDispose {
            timer.cancel()
            timer.purge()
        }
    }

    AnimatedVisibility(
        visible = messageBarState.error != null && showMessageBar
                || messageBarState.success != null && showMessageBar,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top
        )
    ) {
        MessageBar(
            message = message,
            error = error,
            successIcon = successIcon,
            errorIcon = errorIcon,
            successContainerColor = successContainerColor,
            successContentColor = successContentColor,
            errorContainerColor = errorContainerColor,
            errorContentColor = errorContentColor,
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding,
            showToastOnCopy = showToastOnCopy
        )
    }
}

@Composable
private fun MessageBar(
    message: String? = null,
    error: String? = null,
    successIcon: ImageVector = Icons.Default.Check,
    errorIcon: ImageVector = Icons.Default.Warning,
    successContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    successContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    errorContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
    errorContentColor: Color = MaterialTheme.colorScheme.onErrorContainer,
    verticalPadding: Dp,
    horizontalPadding: Dp,
    showToastOnCopy: Boolean = false,
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (error != null) errorContainerColor
                else successContainerColor
            )
            .padding(vertical = if (error != null) 0.dp else verticalPadding)
            .padding(
                start = horizontalPadding,
                end = if (error != null) 0.dp else horizontalPadding
            )
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(4f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector =
                if (error != null) errorIcon
                else successIcon,
                contentDescription = "Message Bar Icon",
                tint = if (error != null) errorContentColor
                else successContentColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text =
                error ?: (message ?: "Unknown"),
                color = if (error != null) errorContentColor
                else successContentColor,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
        if (error != null) {
            TextButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    clipboardManager.setText(AnnotatedString(text = "$error"))
                    if (showToastOnCopy) {
                        Toast.makeText(context, "Copied!", Toast.LENGTH_SHORT).show()
                    }
                },
                contentPadding = PaddingValues(vertical = 0.dp)
            ) {
                Text(
                    text = "Copy",
                    color = errorContentColor,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}

@Composable
@Preview
fun MessageBarPreview() {
    MessageBar(
        message = "Successfully Updated.",
        verticalPadding = 12.dp,
        horizontalPadding = 12.dp
    )
}

@Composable
@Preview
fun MessageBarErrorPreview() {
    MessageBar(
        error = "Internet Unavailable",
        verticalPadding = 12.dp,
        horizontalPadding = 12.dp
    )
}