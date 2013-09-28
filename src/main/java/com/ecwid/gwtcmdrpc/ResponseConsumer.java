package com.ecwid.gwtcmdrpc;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public interface ResponseConsumer<T> {
	void accept(T t);
}
