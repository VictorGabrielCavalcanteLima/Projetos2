#include <jni.h>
#include <string>
#include <fcntl.h>
#include <unistd.h>
#include <linux/uinput.h>

extern "C" JNIEXPORT jint JNICALL
Java_com_meu_gamepad_GamepadOverlayService_nativeInitGamepad(JNIEnv *env, jobject thiz) {
    int fd = open("/dev/uinput", O_WRONLY | O_NONBLOCK);
    if (fd < 0) return -1;

    ioctl(fd, UI_SET_EVBIT, EV_KEY);
    ioctl(fd, UI_SET_KEYBIT, BTN_A);
    ioctl(fd, UI_SET_KEYBIT, BTN_B);
    
    struct uinput_setup usetup{};
    usetup.id.bustype = BUS_USB;
    usetup.id.vendor = 0x045e;
    usetup.id.product = 0x028e;
    strcpy(usetup.name, "Gamepad Virtual Android");

    ioctl(fd, UI_DEV_SETUP, &usetup);
    ioctl(fd, UI_DEV_CREATE);

    return fd;
}
