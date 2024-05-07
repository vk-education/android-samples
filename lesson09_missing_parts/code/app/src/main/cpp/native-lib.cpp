#include <jni.h>
#include <string>

extern "C" JNIEXPORT jobject JNICALL
Java_com_vk_missingparts_NativeDemoActivity_calculateNextPoint(
        JNIEnv *env,
        jobject main,
        jobject current,
        jboolean show) {

    jclass pointClass = env->FindClass("com/vk/missingparts/Point");
    jmethodID xProp = env->GetMethodID(pointClass, "getX", "()I");
    jmethodID yProp = env->GetMethodID(pointClass, "getY", "()I");

    int x = env->CallIntMethod(current, xProp) + 42;
    int y = env->CallIntMethod(current, yProp) + 42;

    jmethodID constr = env->GetMethodID(pointClass, "<init>", "(II)V");
    jobject newPoint = env->NewObject(pointClass, constr, x, y);

    jclass thisClass = env->GetObjectClass(main);
    jmethodID showMethod = env->GetMethodID(thisClass, "showPoint",
                                            "(Lcom/vk/missingparts/Point;)V");
    if (showMethod != nullptr && show) {
        env->CallVoidMethod(main, showMethod, newPoint);
    }

    return newPoint;
}
