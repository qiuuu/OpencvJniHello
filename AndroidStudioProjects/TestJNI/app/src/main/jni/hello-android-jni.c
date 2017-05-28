#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_pangge_testjni_MainActivity_getMsgFromJni(JNIEnv *env, jobject instance) {

    // TODO


    return (*env)->NewStringUTF(env, "hello from Jni C");
}