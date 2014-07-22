package com.ecwid.gwtcmdrpc;


import java.lang.annotation.*;

/**
* @author Vasily Karyaev <v.karyaev@gmail.com>
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExecutedBy {
    public Class<? extends RequestExecutor<?, ?>> value();
}
