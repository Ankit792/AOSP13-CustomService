import android.app.math.MathServiceManager;
import android.app.math.IMathServiceManager;

import android.app.lock.LockServiceManager;
import android.app.lock.ILockService;

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

            registerService(Context.LOCK_SERVICE, LockServiceManager.class,
            new CachedServiceFetcher<LockServiceManager>() {
                @Override
                public LockServiceManager createService(ContextImpl ctx) {
                    IBinder b = ServiceManager.getService(Context.LOCK_SERVICE);
                    ILockService service = ILockService.Stub.asInterface(b);
                    return new LockServiceManager(ctx.getOuterContext(), service);
                }
            }
        );
}