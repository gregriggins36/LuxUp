-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-dontnote com.google.android.gms.internal.**

#Classes needed for Navigation Component
-keep class com.weddingwire.patronus.** extends android.support.v4.app.Fragment{}

#Kotlin
-keep class kotlin.jvm.functions.** { *; }
-dontnote kotlin.internal.PlatformImplementationsKt
-dontnote kotlin.jvm.internal.Reflection

#Okio
-dontwarn okio.**
-dontnote okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

#Retrofit 2
-dontwarn retrofit2.**
-dontnote retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# OkHTTP 3
# This is to allow OkHTTP 3 to properly pull out the variable when trying to determine
# the TrustManager of the factory. See here for why it works:
# https://github.com/square/okhttp/blob/bd22589ee3645da310db2e1032b0ce3784c3476b/okhttp/src/main/java/okhttp3/internal/Platform.java#L414
-keepclassmembernames class **.TLSSocketFactory { *; }
-dontnote okhttp3.**

#Gson
-keepattributes Signature
-keep class com.google.gson.Gson
-dontnote com.google.gson.internal.UnsafeAllocator

#Android Support Library
-dontwarn android.support.**
-dontnote android.support.**

#Android Design Support Library
-keep public class * extends android.support.design.widget.CoordinatorLayout$Behavior {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keep class android.support.v7.widget.SearchView { *; }

#Glide
-keep class android.media.MediaMetadataRetriever
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-dontwarn android.media.MediaMetadataRetriever
-dontnote com.bumptech.glide.Glide

#Dagger
-keep class javax.inject.Provider
-dontwarn javax.inject.Provider

# okhttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**

# okio
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.*
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn okio.**

# Rx
-dontnote io.reactivex.**
