package com.soprasteria.johannes.winter.framework;

public class ExceptionUtil {

    public static RuntimeException soften(Exception ex) throws RuntimeException {
        return _soften(ex);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Exception> T _soften(Exception ex) throws T {
        throw (T)ex;
    }

}
