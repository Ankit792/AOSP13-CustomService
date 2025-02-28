# Override ADB keys with your prebuilt file
PRODUCT_ADB_KEYS := $(CONFIG_ANKIT_PATH)/security/adb_keys
# Ensure ADB secure mode is enabled
PRODUCT_PROPERTY_OVERRIDES += ro.adb.secure=1