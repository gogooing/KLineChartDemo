# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Adt-bundle\android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

#-libraryjars class_path 应用的依赖包，如android-support-v4
#-keep [,modifier,...] class_specification 不混淆某些类
#-keepclassmembers [,modifier,...] class_specification 不混淆类的成员
#-keepclasseswithmembers [,modifier,...] class_specification 不混淆类及其成员
#-keepnames class_specification 不混淆类及其成员名
#-keepclassmembernames class_specification 不混淆类的成员名
#-keepclasseswithmembernames class_specification 不混淆类及其成员名
#-assumenosideeffects class_specification 假设调用不产生任何影响，在proguard代码优化时会将该调用remove掉。如system.out.println和Log.v等等
#-dontwarn [class_filter] 不提示warnning

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontpreverify
#-dontoptimize
-dontusemixedcaseclassnames
-optimizationpasses 7
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
#把一些系统的和第三方jar设置为不提示warnning，添加了jar再后面写上就好
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontwarn android.support.v4.**
-dontwarn android.inputmethodservice.**
-dontwarn android.widget.**
-dontwarn android.app.**
-dontwarn android.inputmethodservice.**
-dontwarn android.view.**
-dontwarn io.realm.**

#pingyin4j
-dontwarn android.support.v7.**
-dontwarn org.apache.commons.io.**
-dontwarn android.support.**
-dontwarn org.apache.commons.**

-renamesourcefileattribute ProGuard
-keepattributes SourceFile,LineNumberTable,Signature,Exceptions,InnerClasses,*Annotation*
#把系统的和第三方的进行不混淆

-keep class android.view.**

-keep public class com.sanqius.loro.cjlc.R**{*;}
-keep class *.R$ { *; }

-keep public class android.support.v4.view.* {*;}
-keep public class android.view.* {*;}

-keep class android.support.**{ *; }
-keep class org.apache.commons.**{ *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

#Compatibility library
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

#有些项目的类不能进行混淆
-keep class com.github.mikephil.charting.** { *; }
-keep class io.realm.** { *; }
-keep class com.sanqius.loro.cjlc.bean.**{*;}
-keep class com.sanqius.loro.cjlc.common.**{*;}

-keep public class de.greenrobot.dao.R$*{
    public static final int *;
      public static final Objects *;
}
-keep class **$Properties
-dontwarn  org.eclipse.jdt.annotation.**
-keep class * extends java.lang.annotation.Annotation { *; }


# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
 -keepclassmembers public class * extends android.view.View {
  void set*(***);
  *** get*();
}

#To remove debug logs:
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** w(...);
}

-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
public void *(android.view.View);
}

-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
	public static final android.os.Parcelable$Creator *;
}

-keep class com.squareup.okhttp.** { *;}
-dontwarn okio.**

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclassmembers class **.R$* {
  public static <fields>;
}

#这货导致所有类都不会被混淆，先注释掉
#-keep class * {
#    void set*(***);
#    void set*(int, ***);
#    boolean is*();
#    boolean is*(int);
#    *** get*();
#    *** get*(int);
#}

#rxjava
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 long producerIndex;
 long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#ButterKnife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keep class com.github.mikephil.charting.** { *; }
-dontwarn com.github.mikephil.charting.**

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
