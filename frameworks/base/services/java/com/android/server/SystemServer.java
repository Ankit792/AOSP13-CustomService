import com.android.server.math.MathService;
import com.android.server.lock.LockService;

public final class SystemServer implements Dumpable {
    private void startCoreServices(@NonNull TimingsTraceAndSlog t) {
        try {

            // Context context = getContext();
            mSystemContext.startService(new Intent(mSystemContext, com.android.server.connectivity.WifiCheckService.class));
        

            t.traceBegin("MathService");
            try {
                Slog.i(TAG, "Starting Ankit MathService");
                ServiceManager.addService("math", new MathService(context));
                Slog.i(TAG, "Ankit MathService Started");
            } catch (Throwable e) {
                Slog.e(TAG, "Failure Ankit starting MathService Service", e);
                reportWtf("starting MathService", e);
            }
            t.traceEnd();

            t.traceBegin("LockService");
            try {
                Slog.i(TAG, "Starting Ankit lock service");
                // LockService lockService = new LockService(context);
                ServiceManager.addService("lock_service", new LockService(context));
                Slog.i(TAG, "Ankit LockService Started");
            } catch (Throwable e) {
                Slog.e(TAG, "Failure Ankit starting lock Service", e);
                reportWtf("starting LockService", e);
            }
            t.traceEnd();
        }
    }
}

