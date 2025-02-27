public abstract class Context {

    @StringDef(suffix = { "_SERVICE" }, value = {
        MATH_SERVICE,
    })

    /**
    * @hide
    */
    public static final String MATH_SERVICE = "math";

    /**
    * @hide
    */
    public static final String LOCK_SERVICE = "lock_service";
}