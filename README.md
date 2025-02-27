# SELinux and Service Modes in AOSP

## Overview

Services running in **Permissive** mode will not cause any problems. However, in **Enforcing** mode, SELinux policies might block your service from starting.

## Simple Math Service

#### Behavior in Permissive Mode

When the system is in **Permissive** mode, the service runs without restrictions, and you can observe the following behavior:

```sh
dart_mx8mm:/ $ service list | grep math
120     math: [android.app.math.IMathServiceManager]

dart_mx8mm:/ $ getenforce
Permissive

dart_mx8mm:/ $ logcat | grep MathService
02-26 10:17:31.761   615   615 D SystemServerTiming: MathService
02-26 10:17:31.761   615   615 I SystemServer: Starting Ankit MathService
02-26 10:17:31.763   615   615 I SystemServer: Ankit MathService Started
02-26 10:17:31.763   615   615 V SystemServerTiming: MathService took to complete: 2ms

dart_mx8mm:/ $ service call math 1 i32 5 i32 3
Result: Parcel(00000000 00000008   '........')

dart_mx8mm:/ $ logcat | grep avc
02-26 10:17:31.763   269   269 E SELinux : avc:  denied  { add } for pid=615 uid=1000 name=math scontext=u:r:system_server:s0 tcontext=u:object_r:default_android_service:s0 tclass=service_manager permissive=1
```
#### Behavior in Enforcing Mode
When the system is in **Enforcing** mode, the service runs with restrictions, and there will not be any avc denials after implementing seppolicy like in private directory math_service.te and service_contexts



### WiFi check Service

WifiCheckService is an Android background service that periodically checks the Wi-Fi connectivity status in the background. If the device is disconnected from Wi-Fi, it logs a message and displays a Toast notification to alert the user. It runs a repeating task (Runnable) using a Handler. The service stops when it is explicitly destroyed.