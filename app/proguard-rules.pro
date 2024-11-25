# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.** { *; }
-keep interface retrofit2.** { *; }

-keep class com.example.shopease.data.model.** { *; }
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**


-keep class * implements dagger.hilt.InstallIn
-if class * extends android.app.Application
-keep @dagger.hilt.android.HiltAndroidApp class <1> { *; }
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile