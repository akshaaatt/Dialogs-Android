package com.aemerse.dialogs_android

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.aemerse.dialogs.*
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                backgroundColor = colorResource(id = R.color.black),
                topBar = { TopAppBar(activity = this, title = "Dialogs") },
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .padding(it)
                ) {
                    BasicDialogDemo()
                }
            }
        }
    }
}

@Composable
fun BasicDialogDemo() {
    DialogAndShowButton(buttonText = "Basic Dialog") {
        title(res = R.string.location_dialog_title)
        message(res = R.string.location_dialog_message)
    }

    DialogAndShowButton(
        buttonText = "Basic Dialog With Buttons",
        buttons = {
            negativeButton("Disagree")
            positiveButton("Agree")
        }
    ) {
        title(res = R.string.location_dialog_title)
        message(res = R.string.location_dialog_message)
    }

    DialogAndShowButton(
        buttonText = "Basic Dialog With Buttons and Lottie",
        buttons = {
            negativeButton("Disagree")
            positiveButton("Agree")
            button("Cancel")
        }
    ) {
        Row(Modifier
            .height(200.dp)
            .fillMaxWidth()) {
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.university))
            LottieAnimation(composition)
        }
    }

    DialogAndShowButton(
        buttonText = "Basic Dialog With Buttons and Gif",
        buttons = {
            negativeButton("Disagree")
            positiveButton("Agree")
            button("Cancel")
        }
    ) {
        Row(Modifier.height(200.dp).fillMaxWidth()) {
            GifImage(imageID = R.raw.thankyou, placeholderID = R.mipmap.ic_launcher)
        }
    }

    DialogAndShowButton(
        buttonText = "Basic Dialog With Stacked Buttons",
        buttons = {
            negativeButton("No Thanks")
            positiveButton("Turn On Speed Boost")
        }
    ) {
        title(res = R.string.location_dialog_title)
        message(res = R.string.location_dialog_message)
    }

    DialogAndShowButton(
        buttonText = "Basic Input Dialog",
        buttons = {
            negativeButton("Cancel")
            positiveButton("Ok")
        }
    ) {
        title(res = R.string.input_dialog_title)
        input(label = "Name", placeholder = "Jon Smith") {
            Log.d("SELECTION:", it)
        }
    }

    DialogAndShowButton(
        buttonText = "Outlined Input Dialog",
        buttons = {
            negativeButton("Cancel")
            positiveButton("Ok")
        }
    ) {
        title(res = R.string.input_dialog_title)
        input(label = "Name", placeholder = "Jon Smith", textFieldStyle = TextFieldStyle.Outlined) {
            Log.d("SELECTION:", it)
        }
    }

    DialogAndShowButton(
        buttonText = "Basic Input Dialog With Immediate Focus",
        buttons = {
            negativeButton("Cancel")
            positiveButton("Ok")
        }
    ) {
        val focusRequester = remember { FocusRequester() }
        title(res = R.string.input_dialog_title)
        input(
            label = "Name",
            placeholder = "Jon Smith",
            focusRequester = focusRequester,
            focusOnShow = true
        ) {
            Log.d("SELECTION:", it)
        }
    }

    DialogAndShowButton(
        buttonText = "Input Dialog with submit on IME Action",
        buttons = {
            negativeButton("Cancel")
            positiveButton("Ok")
        }
    ) {
        title(res = R.string.input_dialog_title)
        input(
            label = "Name", placeholder = "Jon Smith",
            keyboardActions = KeyboardActions(
                onDone = { submit() }
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        ) {
            Log.d("SELECTION:", it)
        }
    }

    DialogAndShowButton(
        buttonText = "Input Dialog with input validation",
        buttons = {
            negativeButton("Cancel")
            positiveButton("Ok")
        }
    ) {
        title("Please enter your email")
        input(
            label = "Email",
            placeholder = "hello@example.com",
            errorMessage = "Invalid email",
            isTextValid = {
                Patterns.EMAIL_ADDRESS.matcher(it).matches() && it.isNotEmpty()
            }
        ) {
            Log.d("SELECTION:", it)
        }
    }
}