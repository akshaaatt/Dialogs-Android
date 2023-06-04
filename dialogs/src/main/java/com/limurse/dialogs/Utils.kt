package com.limurse.dialogs

import android.os.Build.VERSION.SDK_INT
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

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

@Composable
fun GifImage(
    imageID: Int,
    placeholderID: Int
){
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(data = imageID)
            .error(placeholderID)
            .build(),

        ImageLoader.Builder(context)
            .components {
                when {
                    SDK_INT >= 28 -> {
                        add(ImageDecoderDecoder.Factory())
                    }
                    else -> {
                        add(GifDecoder.Factory())
                    }
                }
            }
            .build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    )
}