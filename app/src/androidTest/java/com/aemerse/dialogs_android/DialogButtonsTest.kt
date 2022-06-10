package com.aemerse.dialogs_android

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.aemerse.dialogs.MaterialDialogButtons
import com.aemerse.dialogs_android.utils.DialogWithContent
import com.aemerse.dialogs_android.utils.extensions.assertDialogDoesNotExist
import com.aemerse.dialogs_android.utils.extensions.assertDialogExists
import com.aemerse.dialogs_android.utils.extensions.onNegativeButton
import com.aemerse.dialogs_android.utils.extensions.onPositiveButton
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DialogButtonsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    private fun MaterialDialogButtons.defaultButtons() {
        negativeButton("Disagree")
        positiveButton("Agree")
    }

    @Test
    fun dialogDismissedOnPositiveButton() {
        composeTestRule.setContent { DialogWithContent(buttons = { defaultButtons() }) }
        composeTestRule.onPositiveButton().performClick()
        composeTestRule.assertDialogDoesNotExist()
    }

    @Test
    fun dialogDismissedOnNegativeButton() {
        composeTestRule.setContent { DialogWithContent(buttons = { defaultButtons() }) }
        composeTestRule.onNegativeButton().performClick()
        composeTestRule.assertDialogDoesNotExist()
    }

    @Test
    fun dialogNotDismissedOnPositiveButton() {
        composeTestRule.setContent { DialogWithContent(false, buttons = { defaultButtons() }) }
        composeTestRule.onPositiveButton().performClick()
        composeTestRule.assertDialogExists()
    }

    @Test
    fun dialogNotDismissedOnNegativeButton() {
        composeTestRule.setContent { DialogWithContent(false, buttons = { defaultButtons() }) }
        composeTestRule.onNegativeButton().performClick()
        composeTestRule.assertDialogExists()
    }
}
