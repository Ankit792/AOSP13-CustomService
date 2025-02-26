public abstract class Context {

    @StringDef(suffix = { "_SERVICE" }, value = {
        MATH_SERVICE,
    })

    /**
    * @hide
    */
    public static final String MATH_SERVICE = "math";
}