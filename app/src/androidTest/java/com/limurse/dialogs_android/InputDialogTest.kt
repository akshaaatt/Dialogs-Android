package com.limurse.dialogs_android

import android.util.Patterns
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.limurse.dialogs.MaterialDialogScope
import com.limurse.dialogs.input
import com.limurse.dialogs.rememberMaterialDialogState
import com.limurse.dialogs.title
import com.limurse.dialogs_android.utils.DialogWithContent
import com.limurse.dialogs_android.utils.defaultButtons
import com.limurse.dialogs_android.utils.extensions.onDialogInput
import com.limurse.dialogs_android.utils.extensions.onDialogInputError
import com.limurse.dialogs_android.utils.extensions.onPositiveButton
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InputDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val testInput = "random text input from the user"
    private val testHint = "random hint"
    private val testLabel = "random text input label"
    private val testInputError = "random error"

    @Test
    fun checkTextShownOnInput() {
        setupBasicInputDialog()
        composeTestRule.onDialogInput().performTextInput(testInput)
        composeTestRule.onNodeWithText(testInput).assertExists()
    }

    @Test
    fun checkPrefillTextIsShown() {
        setupBasicInputDialog { input(label = testLabel, prefill = testInput) }
        composeTestRule.onNodeWithText(testInput).assertExists()
    }

    @Test
    fun checkHintTextIsShown() {
        setupBasicInputDialog { input(label = testLabel, placeholder = testHint) }
        composeTestRule.onNodeWithText(testHint, true).assertExists()
    }

    @Test
    fun checkCorrectLabelTextIsShown() {
        setupBasicInputDialog { input(label = testLabel) }
        composeTestRule.onNodeWithText(testLabel, true).assertExists()
    }

    // @Test
    // fun checkVisualTransformationIsApplied() {
    //     setupBasicInputDialog {
    //         input(
    //             label = testLabel,
    //             visualTransformation = PasswordVisualTransformation()
    //         )
    //     }
    //
    //     composeTestRule.onDialogInput().performTextInput(testInput)
    //     composeTestRule.onNodeWithText(testInput).assertDoesNotExist()
    // }

    @Test
    fun checkErrorTextIsShown() {
        setupBasicInputDialog {
            input(
                label = testLabel,
                errorMessage = testInputError,
                isTextValid = { false }
            )
        }
        composeTestRule.onNodeWithText(testLabel, true).assertExists()
    }

    @Test
    fun checkWaitForPositiveButtonEnabled() {
        val input = mutableStateOf<String?>(null)
        setupBasicInputDialog {
            input(
                label = testLabel,
                waitForPositiveButton = true,
                onInput = { input.value = it }
            )
        }

        composeTestRule.onDialogInput().performTextInput(testInput)
        assertEquals(null, input.value)
        composeTestRule.onPositiveButton().performClick()
        assertEquals(testInput, input.value)
    }

    @Test
    fun checkWaitForPositiveButtonDisabled() {
        val input = mutableStateOf<String?>(null)
        setupBasicInputDialog {
            input(
                label = testLabel,
                waitForPositiveButton = false,
                onInput = { input.value = it }
            )
        }

        composeTestRule.onDialogInput().performTextInput(testInput)
        assertEquals(testInput, input.value)
        input.value = null
        composeTestRule.onPositiveButton().performClick()
        assertEquals(null, input.value)
    }

    @Test
    fun checkErrorShownWithInputValidation() {
        setupBasicInputDialog {
            input(
                label = testLabel,
                waitForPositiveButton = false,
                isTextValid = { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
            )
        }

        listOf("hello", "hello@gmail", "hello.com", "hello@.com").forEach {
            composeTestRule.onDialogInput().performTextInput(it)
            composeTestRule.onDialogInputError().assertExists()
            composeTestRule.onDialogInput().performTextClearance()
        }

        listOf("hello@gmail.com", "test@test.co.uk", "test@hello.is").forEach {
            composeTestRule.onDialogInput().performTextInput(it)
            composeTestRule.onDialogInputError().assertDoesNotExist()
            composeTestRule.onDialogInput().performTextClearance()
        }
    }

    @Test
    fun checkErrorDisappearsOnValidInput() {
        setupBasicInputDialog {
            input(
                label = testLabel,
                waitForPositiveButton = false,
                isTextValid = { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
            )
        }

        composeTestRule.onDialogInput().performTextInput("hello@hello")
        composeTestRule.onDialogInputError().assertExists()
        composeTestRule.onDialogInput().performTextInput(".com")
        composeTestRule.onDialogInputError().assertDoesNotExist()
    }

    @Test
    fun checkPositiveButtonDisabledOnInvalidInput() {
        setupBasicInputDialog {
            input(
                label = testLabel,
                waitForPositiveButton = false,
                isTextValid = { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
            )
        }

        composeTestRule.onDialogInput().performTextInput("hello@hello")
        composeTestRule.onPositiveButton().assertIsNotEnabled()
        composeTestRule.onDialogInput().performTextInput(".com")
        composeTestRule.onPositiveButton().assertIsEnabled()
    }

    private fun setupBasicInputDialog(
        testInputDialog: @Composable MaterialDialogScope.() -> Unit = { input(label = "Test") }
    ) {
        composeTestRule.setContent {
            val dialogState = rememberMaterialDialogState(true)

            DialogWithContent(dialogState = dialogState, buttons = { defaultButtons() }) {
                title(res = R.string.input_dialog_title)
                testInputDialog()
            }
        }
    }
}
