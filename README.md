<h1 align="center">Dialogs-Android</h1>

<p align="center">
    <img src="https://jitpack.io/v/akshaaatt/Dialogs-Android.svg?style=flat-square&logo=github&logoColor=white"
         alt="GitHub issues">
    <a href="https://jitpack.io/#akshaaatt/Dialogs-Android">
    <a href="https://play.google.com/store/apps/details?id=com.redalck.dtu_rm">
       <img src="https://PlayBadges.pavi2410.me/badge/downloads?id=com.redalck.dtu_rm">
    </a>
    <a href="https://github.com/akshaaatt/Dialogs-Android/commits/master">
    <img src="https://img.shields.io/github/last-commit/akshaaatt/Dialogs-Android.svg?style=flat-square&logo=github&logoColor=white"
         alt="GitHub last commit">
    <a href="https://github.com/akshaaatt/Dialogs-Android/issues">
    <img src="https://img.shields.io/github/issues-raw/akshaaatt/Dialogs-Android.svg?style=flat-square&logo=github&logoColor=white"
         alt="GitHub issues">
    <a href="https://github.com/akshaaatt/Dialogs-Android/pulls">
    <img src="https://img.shields.io/github/issues-pr-raw/akshaaatt/Dialogs-Android.svg?style=flat-square&logo=github&logoColor=white"
         alt="GitHub pull requests">
</p>
      
<p align="center">
  <a href="#features">Features</a> •
  <a href="#development">Development</a> •
  <a href="#usage">Usage</a> •
  <a href="#license">License</a> •
  <a href="#contribution">Contribution</a>
</p>

<h1 align="center">
  <br>
  <a href="https://github.com/akshaaatt/Dialogs-Android/archive/master.zip"><img src="https://i.postimg.cc/mZ1ZnNty/17973884.jpg" alt="Dialogs-Android"></a>
</h1>
	    
---
	    
[![ezgif-com-gif-maker.gif](https://i.postimg.cc/Qd4wvkhq/ezgif-com-gif-maker.gif)](https://postimg.cc/ts6Bx611)

## Features

* Written in Kotlin
* No boilerplate code
* Easy initialization
* Supports Lottie, Gifs, Images and more
* Written for Jetpack Compose

## Gradle Dependency

* Add the JitPack repository to your project's build.gradle file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
* Add the dependency in your app's build.gradle file

```
dependencies {
    implementation 'com.github.akshaaatt:Dialogs-Android:2.0.0'
}
```
        
## Development
	    
* Prerequisite: Latest version of the Android Studio and SDKs on your pc.
* Clone this repository.
* Use the `gradlew build` command to build the project directly or use the IDE to run the project to your phone or the emulator.
	    
## Usage
	    
```kotlin
val dialogState = rememberMaterialDialogState()
MaterialDialog(dialogState = dialogState) {
title(text = "Use Location Services?")
message(res = R.string.location_dialog_message)
}

/* This should be called in an onClick or an Effect */ 
dialog.show()
```	    
    
The dialog is shown when the dialog.show() is called. The hard coded strings for all the components in the example above can be replaced with a string resource id.

Buttons can be added to the dialog by adding the buttons parameter:

```kotlin
MaterialDialog(
dialogState = dialogState,
buttons = {
    positiveButton("Ok")
    negativeButton("Cancel")
}
) {
...
}
```
	    
If the text of the buttons is too long to fit in one row the buttons will be automatically stacked on top on one another.

Input Dialogs

```kotlin
MaterialDialog(dialogState = dialogState, buttons = { ... }) {
...
input(label = "Name", placeholder = "Jon Smith") { inputString ->
    /* Do something */
}
...
}
```
	    
In the code snippet above creates the dialog seen in the image (without the title). Once an input is entered and the user presses the positive button, the dialog will callback with the string the user inputted. The input function also has other parameters which can be found in the comment above the input function implementation. You can also change the style of the dialog by making use of the textFieldStyle parameter. For example, the following code can be used for an outlined text field:

```kotlin
input(label = "Name", placeholder = "Jon Smith", textFieldStyle = TextFieldStyle.Outlined) { inputString ->
/* Do something */
}
```
	    
List Dialogs

Below is an example of a plain list dialog:

```kotlin
MaterialDialog(dialogState = dialogState) {
...
listItems(listOf("Item 1", "Item 2", "Item 3"), onClick { index, item ->
    //Do Something
})
...
}
```
	    
If you would like to use a custom list item layout you can use the following code:

```kotlin
data class TextIcon(text: String, icon: ImageAsset)
val items = listOf(
TextIcon("Item 1", imageFromResource(R.drawable.item_one))
TextIcon("Item 2", imageFromResource(R.drawable.item_two))
TextIcon("Item 3", imageFromResource(R.drawable.item_three))
)
...

MaterialDialog(dialogState = dialogState) {
...
listItems(items, onClick { index, item ->
    //Do Something
}) {
    Row {
	Box(Modifier.preferedSize(40.dp)) {
	    Image(it.icon)
	}
	Text(it.text, modifier = Modifier.padding(start = 12.dp))
    }
}
...
}
```
	    
Single Choice List

```kotlin
MaterialDialog(dialogState = dialogState, buttons = { ... }) {
listItemsSingleChoice(
    list = listOf("Item 1", "Item 2", "Item 3"),
    disabledIndices = listOf(1),
    initialSelection = 2
) {
    //Do Something
}
}
```
	    
As seen in the code snippet above you can pass in a list of indices of items which should be disabled (ie. cannot be selected) and the item index which should selected to start with. By default, if no initial selection is given it will default to the first item.

Multiple Choice List

```kotlin	    
MaterialDialog(dialogState = dialogState, buttons = { ... }) {
listItemsSingleChoice(
    list = listOf("Item 1", "Item 2", "Item 3"),
    disabledIndices = listOf(1),
    initialSelection = listOf(1)
) {
    //Do Something
}
}
```
	    
As seen in the code snippet above you can select which items will be disabled (ie. cannot be selected) and also a list of item indicies which should be initially selected.

Custom View

```kotlin
MaterialDialog(dialogState = dialogState, buttons = { ... }) {
customView {
    //Define your view here
}
}
```
	    
The custom view provides a box with the appropriate material design padding's assigned to it so that your content. If you don't want the padding you can just put your composable anywhere in the dialog.build scope and it will be displayed as normal.

Theming

The background colour of the dialog can be changed by using the backgroundColor parameter of the build function:

```kotlin
MaterialDialog(dialogState = dialogState, buttons = { ... }, backgroundColor = Color.Red) {
title(text = "Use Location Services?")
message(res = R.string.location_dialog_message)
}
```
	    
The shape of the dialog is defaulted to the MaterialTheme.shapes.medium property but can also be set using the shape parameter in MaterialDialog .
      
## License

This Project is licensed under the [GPL version 3 or later](https://www.gnu.org/licenses/gpl-3.0.html).

## Contribution

You are most welcome to contribute to this project!
