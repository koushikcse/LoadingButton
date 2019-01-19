# LoadingButton
Show or hide loading on button

[![](https://jitpack.io/v/koushikcse/LoadingButton.svg)](https://jitpack.io/#koushikcse/LoadingButton)

![LoadingButton demo](https://github.com/koushikcse/LoadingButton/blob/master/sample-demo.gif)

## Add to Gradle

Add this to your project level `build.gradle` file

```gradle
repositories {
    maven { url "https://jitpack.io" }
}
```

And then add this to your module level `build.gradle` file

```gradle
dependencies {
    implementation "com.github.koushikcse:LoadingButon:${latest-version}"
}
```
## How it works

Sometimes we need to show loading on button as per requirement we don't want to block full screen view with default loading or dialogs. So you can use this library to show loading on button hide loading after that when you want. 

## How to setup

Its very easy to use **LoadingButton** as its a custom button and contain all features of android default button with some extra attributes.

### Add it to a layout

```xml
<layout
     ...
     <com.kusu.loadingbutton.LoadingButton
            android:id="@+id/loadingButton"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:text="Hello World!"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:lb_isShadowEnable="false"/>
       ...
</layout>
```


# LICENSE

MIT License

Copyright (c) 2019 Koushik Mondal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
