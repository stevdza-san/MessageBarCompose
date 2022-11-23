package com.stevdzasan.messagebar

import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stevdzasan.messagebar.TestTags.COPY_BUTTON
import com.stevdzasan.messagebar.TestTags.MESSAGE_BAR
import com.stevdzasan.messagebar.TestTags.MESSAGE_BAR_TEXT
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MessageBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var state: MessageBarState
    private lateinit var clipboardManager: ClipboardManager

    @Test
    fun addNoState_Assert_MessageBarDoesNotExist() {
        composeTestRule.setContent {
            state = rememberMessageBarState()
            ContentWithMessageBar(messageBarState = state) {}
        }
        composeTestRule.onNodeWithTag(MESSAGE_BAR).assertDoesNotExist()
    }

    @Test
    fun addSuccessState_Assert_CorrectSuccessMessageIsDisplayed_And_CopyButtonDoesNotExist() {
        composeTestRule.setContent {
            state = rememberMessageBarState()
            ContentWithMessageBar(messageBarState = state) {}
        }
        state.addSuccess(message = "Success Message!")
        composeTestRule.onNodeWithTag(MESSAGE_BAR_TEXT)
            .assertTextEquals("Success Message!")
        composeTestRule.onNodeWithTag(COPY_BUTTON)
            .assertDoesNotExist()
    }

    @Test
    fun addErrorState_Assert_CorrectErrorMessageIsDisplayed_And_CopyButtonExists() {
        composeTestRule.setContent {
            state = rememberMessageBarState()
            ContentWithMessageBar(messageBarState = state) {}
        }
        state.addError(exception = Exception("Fatal Error!"))
        composeTestRule.onNodeWithTag(MESSAGE_BAR_TEXT)
            .assertTextEquals("Fatal Error!")
        composeTestRule.onNodeWithTag(COPY_BUTTON)
            .assertExists()
    }

    @Test
    fun addErrorState_ClickCopyButton_Assert_CorrectMessageCopiedToClipboard() {
        composeTestRule.setContent {
            clipboardManager = LocalClipboardManager.current
            state = rememberMessageBarState()
            ContentWithMessageBar(messageBarState = state) {}
        }
        state.addError(exception = Exception("Fatal Error!"))
        composeTestRule.onNodeWithTag(COPY_BUTTON).performClick()
        assert(clipboardManager.getText()?.text == "Fatal Error!")
    }

}