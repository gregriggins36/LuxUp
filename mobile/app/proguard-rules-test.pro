-dontobfuscate
-dontoptimize

-keepattributes EnclosingMethod

# Instrument Test specific dependencies
-dontwarn com.squareup.javawriter.**
-dontwarn org.hamcrest.**
-dontwarn org.junit.**
-dontwarn android.test.**
-dontwarn org.mockito.**
-dontwarn org.robolectric.**
-dontwarn org.assertj.**

-dontwarn org.slf4j.**
-dontwarn java.beans.**

-dontwarn com.yahoo.squidb.**

-dontwarn net.bytebuddy.**
-dontwarn org.bouncycastle.**

-dontwarn org.xmlpull.v1.**

-include proguard-rules.pro
