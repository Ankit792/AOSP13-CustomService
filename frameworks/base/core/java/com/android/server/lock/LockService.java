package com.android.server.lock;

import android.app.lock.ILockService;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import android.content.Intent;
import android.os.UserHandle;
import android.app.StatusBarManager;

import android.app.admin.DevicePolicyManager;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.pm.PackageManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.os.SystemProperties;

public class LockService extends ILockService.Stub {
    private static final String TAG = "AnkitLockService";
    private static final String ACTION_LOCK_STATE_CHANGED = "com.customservice.timer.ACTION_LOCK_STATE_CHANGED";
    private static final String EXTRA_LOCKED = "locked";
    private static final String PERMISSION_RECEIVE_LOCK_STATE = "com.customservice.timer.permission.RECEIVE_LOCK_STATE";


    private final Context mContext;
    private boolean mLocked = false;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private DevicePolicyManager mDpm;
    private ComponentName mAdminComponent;
    
    public LockService(Context context) {
        mContext = context;
    }

    @Override
    public void setLockState(boolean locked) {
        mLocked = locked;
        Log.d(TAG, "Lock state changed to: " + locked);
        Log.i(TAG,"Control status bar");
        
        // Get StatusBarManager once
        StatusBarManager statusBar = (StatusBarManager) mContext.getSystemService(Context.STATUS_BAR_SERVICE);
        if (statusBar == null) {
            Log.e(TAG, "StatusBarManager not available");
            return;
        }

        if (locked) {
            Log.i(TAG, "Lock state Disable status bar expansion");
            statusBar.disable(StatusBarManager.DISABLE_EXPAND 
                | StatusBarManager.DISABLE_NOTIFICATION_ICONS
                | StatusBarManager.DISABLE_CLOCK
                | StatusBarManager.DISABLE_SYSTEM_INFO);
        } else {
            Log.i(TAG, "Re-enable status bar");
            statusBar.disable(StatusBarManager.DISABLE_NONE);
        }

        // Handle LockTask
        if (locked) {
            Log.i(TAG, "Lock Task if startLockTask statement check ");
            startLockTask();
        } else {
            Log.i(TAG, "Lock Task if stopLockTask statement check ");
            stopLockTask();
        }

        // broadcast intent 
        Intent intent = new Intent(ACTION_LOCK_STATE_CHANGED);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(EXTRA_LOCKED, locked);
        // mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM, 
        //     "com.customservice.timer.permission.RECEIVE_LOCK_STATE");      
        // mContext.sendBroadcastAsUser(intent, 
        // UserHandle.SYSTEM, 
        // "com.customservice.timer.permission.RECEIVE_LOCK_STATE");
        mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM, PERMISSION_RECEIVE_LOCK_STATE); // Use the permission string directly
        }

    @Override
    public boolean isLocked() {
        return mLocked;
    }

    private void startLockTask() {
        try {
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (am.getLockTaskModeState() == ActivityManager.LOCK_TASK_MODE_LOCKED) {
                Log.i(TAG, "getLockTaskModeState");
                return;
            }

             // Add device owner check first
            if (!mDpm.isDeviceOwnerApp(mAdminComponent.getPackageName())) {
                Log.e(TAG, "Not device owner - cannot start LockTask");
                return;
            }

            // Verify admin activation
            if (!mDpm.isAdminActive(mAdminComponent)) {
                Log.e(TAG, "Admin component not active");
                return;
            }

            mContext.enforceCallingOrSelfPermission(
                "android.permission.MANAGE_DEVICE_ADMINS", 
                "LockTask permission required"
            );

            mDpm = (DevicePolicyManager) mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
            mAdminComponent = new ComponentName("com.customservice.timer", 
                "com.customservice.timer.LockAdminReceiver");

            if (!mDpm.isAdminActive(mAdminComponent)) {
                Log.e(TAG, "Admin component not active");
                return;
            }

            // Set LockTask packages
            mDpm.setLockTaskPackages(mAdminComponent, new String[]{"com.customservice.timer"});

            // Start LockTask
            ActivityTaskManager.getService().startSystemLockTaskMode(
                UserHandle.myUserId()
            );

            // Hide navigation bar
            SystemProperties.set("qemu.hw.mainkeys", "1");
            } catch (Exception e) {
                Log.e(TAG, "LockTask failed", e);
        }
    }

    private void stopLockTask() {
        try {
            ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (am.getLockTaskModeState() != ActivityManager.LOCK_TASK_MODE_NONE) {
                final IActivityTaskManager atm = ActivityTaskManager.getService();
                atm.stopSystemLockTaskMode();
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to stop LockTask", e);
        }
    }

    @Override
    public void showLockMessage(String message) {
        mHandler.post(() -> {
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        });
    }
}
