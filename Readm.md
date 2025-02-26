For Services running in Permissive mode will not case any problem but in enforceing mode
SELinux policy might block your service from starting.

In Permissive mode it will look like this
dart_mx8mm:/ $ service list | grep math
120     math: [android.app.math.IMathServiceManager]
130|dart_mx8mm:/ $ getenforce
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