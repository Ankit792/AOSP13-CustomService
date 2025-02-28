# Override ADB keys with your prebuilt file
PRODUCT_ADB_KEYS := $(CONFIG_ANKIT_PATH)/security/adb_keys
# Ensure ADB secure mode is enabled
PRODUCT_PROPERTY_OVERRIDES += ro.adb.secure=1


# In complete block of ADB
# PRODUCT_PROPERTY_OVERRIDES += \
#     ro.adb.secure=0 \          # Disable ADB authentication (optional)
#     ro.debuggable=0 \          # Disable debuggable flag
#     persist.sys.usb.config=mtp # Set USB mode to MTP (no ADB)