package com.aemerse.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
internal fun getString(@StringRes res: Int? = null, default: String? = null): String {
    return if (res != null) {
        LocalContext.current.getString(res)
    } else default
        ?: throw IllegalArgumentException("Function must receive one non null string parameter")
}

internal fun List<Pair<MaterialDialogButtonTypes, Placeable>>.buttons(type: MaterialDialogButtonTypes) =
    this.filter { it.first == type }.map { it.second }

@Composable
internal fun isSmallDevice(): Boolean {
    return LocalConfiguration.current.screenWidthDp <= 360
}

@Composable
internal fun isLargeDevice(): Boolean {
    return LocalConfiguration.current.screenWidthDp <= 600
}

@Composable
fun DialogAndShowButton(
    buttonText: String,
    buttons: @Composable MaterialDialogButtons.() -> Unit = {},
    content: @Composable MaterialDialogScope.() -> Unit
) {
    val dialogState = rememberMaterialDialogState()

    MaterialDialog(dialogState = dialogState, buttons = buttons) {
        content()
    }

    TextButton(
        onClick = { dialogState.show() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colors.primaryVariant),
    ) {
        Text(
            buttonText,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            color = MaterialTheme.colors.onPrimary
        )
    }
}