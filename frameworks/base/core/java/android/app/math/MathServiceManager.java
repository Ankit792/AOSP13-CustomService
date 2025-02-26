package android.app.math;

import android.os.RemoteException;
import android.annotation.SystemService;
import android.content.Context;
import android.util.Log;
import android.annotation.Nullable;

/** @hide */
@SystemService(Context.MATH_SERVICE)
public final class MathServiceManager {

    private static final String TAG = "AnkitMathServiceManager";
    private final IMathServiceManager mService;
    private Context mContext;

    /** @hide */
    public MathServiceManager(Context context, IMathServiceManager service) {
        mContext = context;
        mService = service;
    }

    /** @hide */
    public void initServiceName() {
        try {
            Log.i(TAG,"from math service Manager!");
            mService.initServiceName();
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to get math service Manager");
        }
    }

    /** @hide */
    public int add(int a, int b) {
        try {
            Log.i(TAG,"Implemented addition from math service Manager!");
            return mService.add(a, b);
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to get add math service Manager");
            return 0;

        }
    }

    /** @hide */
    public int sub(int a, int b) {
        try {
            Log.i(TAG,"Implemented sub from math service Manager!");
            return mService.sub(a, b);
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to get sub math service Manager");
            return 0;

        }
    }

    /** @hide */
    public int multiply(int a, int b) {
        try {
            Log.i(TAG,"Implemented mul from math service Manager!");
            return mService.multiply(a, b);
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to get multiply math service Manager");
            return 0;

        }
    }

    /** @hide */
    @Nullable
    public String getServiceName() {
        try {
            return mService.getServiceName();
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to get getServiceName Manager");
            return "ERROR";
        }
    }
}