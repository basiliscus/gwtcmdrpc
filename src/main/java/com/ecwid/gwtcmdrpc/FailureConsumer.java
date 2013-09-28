package com.ecwid.gwtcmdrpc;

/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public interface FailureConsumer {
	void accept(Throwable caught);
}
