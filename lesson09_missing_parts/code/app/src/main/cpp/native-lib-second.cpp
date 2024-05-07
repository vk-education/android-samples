#include <jni.h>
#include <string>

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_vk_missingparts_NativeDemoActivity_echoString(JNIEnv *env, jobject thisObj, jstring input) {
    const char *nativeInput = env->GetStringUTFChars(input, NULL);
    /* process */
    env->ReleaseStringUTFChars(input, nativeInput);
//
//    char returnString[20];
    return env->NewStringUTF(nativeInput);
}
// https://medium.com/tompee/android-ndk-jni-primer-and-cheat-sheet-18dd006ec07f
JNIEXPORT void JNICALL
Java_com_vk_missingparts_NativeDemoActivity_callback(JNIEnv *env, jobject thisObj) {
    /* Get class reference */
    jclass thisClass = env->GetObjectClass(thisObj);

    /* Get method ID and static method ID */
    /* Java method is: private double sum(int n1, int n2) */
    jmethodID methodId = env->GetMethodID(thisClass, "sum", "(II)D");

    /* Java method is: private static void log(string text) */
    jmethodID staticMethodId = env->GetStaticMethodID(thisClass, "log", "(Ljava/lang/String;)V");

    /* Invoke instance method */
    env->CallDoubleMethod(thisObj, methodId, 2, 5);

    /* Invoke static method */
    jstring text = env->NewStringUTF("Hello");
    env->CallVoidMethod(thisObj, staticMethodId, text);
}

JNIEXPORT void JNICALL
Java_com_vk_missingparts_NativeDemoActivity_newObject(JNIEnv *env, jobject thisObj) {
    /* Get the target class */
    jclass targetClass = env->FindClass("java/lang/Double");

    /* Find the constructor that you want */
    jmethodID methodId = env->GetMethodID(targetClass, "<init>", "(I)V");

    /* Create new object */
    jobject obj = env->NewObject(targetClass, methodId, 2);
}
}