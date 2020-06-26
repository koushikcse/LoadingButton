# LoadingButton
Show or hide loading on button

[![](https://jitpack.io/v/koushikcse/LoadingButton.svg)](https://jitpack.io/#koushikcse/LoadingButton)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-LoadingButton-green.svg?style=flat )]( https://android-arsenal.com/details/1/7683 )

![LoadingButton demo](https://github.com/koushikcse/LoadingButton/blob/master/sampledemo.gif)

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
    implementation 'com.github.koushikcse:LoadingButton:1.6'
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
            android:layout_height="51dp"
            android:layout_marginTop="20dp"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="Shadow World!"
            android:textColor="@color/white"
            app:lb_buttonColor="@color/colorPrimary"
            app:lb_isShadowEnable="true"
            app:lb_isCircular="true"
            app:lb_loaderColor="@color/colorAccent"
            app:lb_shadowColor="@color/colorPrimaryDark"
            app:lb_shadowHeight="5dp" />
       ...
</layout>
```


### Methods to use in View Class 
   
   You can easily use to show loading on button click by calling `showLoading();` and hide loading by calling 
   `hideLoading();` whenever you want to stop loading.
   
   **Show Loading**
   
   `LoadingButton loadingButton = (LoadingButton) findViewById(R.id.loadingButton);
    loadingButton.showLoading();
   `
   
   **Hide Loading**
    
   `LoadingButton loadingButton = (LoadingButton) findViewById(R.id.loadingButton);
    loadingButton.hideLoading();
    `

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
