package com.ecwid.gwtcmdrpc;


/**
 * @author Vasily Karyaev <v.karyaev@gmail.com>
 */
public interface RequestExecutor<R extends Request<T>, T extends Response> {
    public T execute(R request) throws ServiceException;
}
