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
# Keep Retrofit & OkHttp classes
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }
-keepclassmembers class okhttp3.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**

# Keep Gson annotations
-keep class com.google.gson.annotations.SerializedName { *; }
-keepattributes Annotation

# Keep Model classes for serialization/deserialization
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}


# Keep Hilt classes
-keep class * implements dagger.hilt.InstallIn { *; }
-keep @dagger.hilt.android.HiltAndroidApp class <1> { *; }
-keep class dagger.hilt.internal.** { *; }
-keepattributes RuntimeVisibleAnnotations
-dontwarn dagger.**


# --- Keep Android resource classes ---
-keep class **.R
-keep class **.R$* { *; }

# --- Keep ViewBinding and DataBinding ---
-keep class **.databinding.*Binding { *; }
-keep class **.databinding.* { *; }
-keep class **.BR { *; }
# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile