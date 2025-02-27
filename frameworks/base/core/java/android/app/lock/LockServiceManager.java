package android.app.lock;

import android.annotation.SystemService;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

/** @hide */
@SystemService(Context.LOCK_SERVICE)
public class LockServiceManager {
    private static final String TAG = "AnkitLockServiceManager";
    private final Context mContext;
    private final ILockService mService;

    /** @hide */
    public LockServiceManager(Context context, ILockService service) {
        mContext = context;
        mService = service;
    }

    /** @hide */
    public void setLockState(boolean locked) {
        try {
            Log.i(TAG,"from lock service Manager!");
            mService.setLockState(locked);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to set Ankit lock state", e);
        }
    }

    /** @hide */
    public boolean isLocked() {
        try {
            Log.i(TAG,"from lock service Manager!");
            return mService.isLocked();
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to check Ankit lock state", e);
            return false;
        }
    }

    /** @hide */
    public void showLockMessage(String message) {
        try {
            Log.i(TAG,"from lock service Manager!");
            mService.showLockMessage(message);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to show Ankit lock message", e);
        }
    }
}
