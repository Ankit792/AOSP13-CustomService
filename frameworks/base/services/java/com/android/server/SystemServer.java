import com.android.server.math.MathService;

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
        }
    }
}

