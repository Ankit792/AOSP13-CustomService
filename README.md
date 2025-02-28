# Authentication-Based Blocking

Allow only pre-authorized devices (those with keys in /data/misc/adb/adb_keys) to connect via ADB. Unknown devices are auto-denied without user interaction. Tested in permissive mode.

And complete block of adb in comments of device.mk.