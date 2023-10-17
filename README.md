<h1 align="center">Message Bar Compose</h1></br>

<p align="center">
  <a href="https://jitpack.io/#stevdza-san/MessageBarCompose/1.0.8"><img alt="License" src="https://badgen.net/badge/Jitpack/1.0.8/orange?icon=github"/></a>
  <a href="https://github.com/stevdza-san"><img alt="Profile" src="https://badgen.net/badge/Github/stevdza_san/green?icon=github"/></a>
</p><br>

<p align="center">
Message Bar is a simple animated UI component that you can wrap around your composable content.
This component is used to display Error/Success messages to your users,
and it is built to supports Material 3 design system.
</p><br>

<p align="center">
<img src="https://github.com/stevdza-san/MessageBarCompose/blob/master/previews/MessageBar.gif?raw=true" width="268"/>
<img src="https://github.com/stevdza-san/MessageBarCompose/blob/master/previews/MessageBar2.gif?raw=true" width="268"/>
<img src="https://github.com/stevdza-san/MessageBarCompose/blob/master/previews/MessageBar3.gif?raw=true" width="268"/>
</p>

## Download
<a href="https://jitpack.io/#stevdza-san/MessageBarCompose/1.0.8"><img alt="License" src="https://badgen.net/badge/Jitpack/1.0.8/orange?icon=github"/></a>

### Gradle

Add the dependency below to your module's `build.gradle` file:
```gradle
dependencies {
    implementation 'com.github.stevdza-san:MessageBarCompose:1.0.8'
}
```
Add a repository in your `settings.gradle` file:
```
dependencyResolutionManagement {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
## Usage

There are two required parameters: `messageBarState` and `content`.

```kotlin
val state = rememberMessageBarState()
ContentWithMessageBar(messageBarState = state) {
     // Your Composable Screen               
}
```

You can control what kind of a Message Bar you want to display, by using the `MessageBarState`.
There are two functions that you can use to achieve that: `addError()` and `addSuccess()`.
They are self explanatory, the first one accepts an Exception type, while the other a simple String.
depending on what kind of a message bar you want to see.

### Message Bar - Success
<img src="https://raw.githubusercontent.com/stevdza-san/MessageBarCompose/9644914665875997fd03aa18ef4cc1d9250c3e74/Success.svg"/>

```kotlin
val state = rememberMessageBarState()
ContentWithMessageBar(messageBarState = state) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            state.addSuccess(message = "Successfully Updated.")
        }) {
            Text(text = "Click me")
        }
    }
}
```


### Message Bar - Error
<img src="https://raw.githubusercontent.com/stevdza-san/MessageBarCompose/9644914665875997fd03aa18ef4cc1d9250c3e74/Error.svg"/>

```kotlin
val state = rememberMessageBarState()
ContentWithMessageBar(messageBarState = state) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            state.addError(exception = Exception("Internet Unavailable."))
        }) {
            Text(text = "Click me")
        }
    }
}
```

## Like what you see? :yellow_heart:
⭐ Give a star to this repository. <br />
☕ Buy me a coffee: https://ko-fi.com/stevdza_san

# License
```xml
Designed and developed by 2022 stevdza-san (Stefan Jovanović)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
