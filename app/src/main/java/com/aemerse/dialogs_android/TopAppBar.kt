package com.aemerse.dialogs_android.components

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.aemerse.dialogs_android.R

@Composable
fun TopAppBar(activity: Activity, title: String = "MusicBrainz") {
    androidx.compose.material.TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon =  {
            IconButton(onClick = {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://musicbrainz.org")))
            }) {
                Icon(painterResource(id = R.mipmap.ic_launcher),
                    "Dialogs",
                    tint = Color.Unspecified)
            }
        },
        backgroundColor = colorResource(id = R.color.black),
        contentColor = colorResource(id = R.color.white),
        elevation = 2.dp,
        actions = {
            IconButton(onClick = {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/akshaaatt/Dialogs-Android.git")))
            }) {
                Icon(painterResource(id = R.drawable.ic_github),
                    "GitHub",
                    tint = Color.Unspecified)
            }
        }
    )
}
