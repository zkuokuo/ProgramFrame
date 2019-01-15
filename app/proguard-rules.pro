-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                                                  # 混淆时是否做预校验
-dontwarn android.support.v4.**
-dontwarn com.google.**
-dontwarn com.nibiru.**
-dontwarn org.bouncycastle.**
-dontwarn com.hotcast.net.**
-dontwarn javax.**
-dontwarn java.awt.**
-dontwarn demo.**
-dontwarn org.apache.**
-dontwarn android.**


-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

-keep public class * extends android.app.Activity                               # 保持哪些类不被混淆
-keep public class * extends android.app.Application                            # 保持哪些类不被混淆
-keep public class * extends android.app.Service                                # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider                    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference                      # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  public static final android.os.Parcelable$Creator *;
}

-keepattributes SourceFile,LineNumberTable

-keep class com.google.** {*;}
-keep class com.nibiru.lib.** {*;}
-keep class android.app.** {*;}
-keep class org.bouncycastle.** {*;}
-keep class com.nostra13.** {*;}
-keep class javax.** {*;}
-keep class x.core.ui.**{*;}
-keep class java.awt.**{*;}
-keep class demo.**{*;}
-keep class wseemann.media.**{*;}
-keep class org.videolan.**{*;}
-keep class com.nibiru.service.**{*;}
-keep class org.apache.**{*;}
-keep class android.**{*;}

-dontwarn x.core.**
-keep public class * extends x.core.ui.XBaseScene
-keep public class * extends x.core.GlobalApplication

-keep class com.dlodlo.**{*;}
-keep class com.google.** {*;}
-keep class com.qualcomm.svrapi.**{*;}
-keep class com.sixdof.**{*;}
-keep class ruiyue.**{*;}
-keep class x.core.ui.view.**{*;}
-keep class com.nibiru.lib.** {*;}
-keep class com.nibiru.vrconfig.** {*;}
-keep class com.nibiru.interaction.** {*;}


#13 picasso -keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}

#14 okhttp3 -dontwarn com.squareup.okhttp3.**
-dontwarn com.squareup.okhttp3.**
-dontwarn org.conscrypt.**
-keep class com.squareup.okhttp3.** { *;}
-keep class org.conscrypt.** { *;}
-dontwarn okio.**

#15 retrofit2 -dontwarn retrofit.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#Rxjava Rxandroid -dontwarn sun.misc.**
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

# Gson
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
#自己定义的实体类
-keep class com.nibiru.programframe.data.model.**{*;}
-keep class com.nibiru.framelib.bean.**{*;}
