package com.stevdzasan.messagebar

import androidx.compose.runtime.*

class MessageBarState {
    var success = mutableStateOf<String?>(null)
        private set
    var error = mutableStateOf<Exception?>(null)
        private set

    fun addSuccess(message: String) {
        error.value = null
        success.value = message
    }

    fun addError(exception: Exception) {
        success.value = null
        error.value = exception
    }
}