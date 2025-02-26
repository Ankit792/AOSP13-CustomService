import android.app.math.MathServiceManager;
import android.app.math.IMathServiceManager;

public final class SystemServiceRegistry {

        registerService(Context.MATH_SERVICE, MathServiceManager.class,
        new CachedServiceFetcher < MathServiceManager > () {
            @Override
            public MathServiceManager createService(ContextImpl context) throws ServiceNotFoundException {
                IBinder binder;
                if (true) { 
                    binder = ServiceManager.getServiceOrThrow(Context.MATH_SERVICE);
                } else {
                    binder = ServiceManager.getService(Context.MATH_SERVICE);
                }
                return new MathServiceManager(context, IMathServiceManager.Stub.asInterface(binder));
            }
        });
}